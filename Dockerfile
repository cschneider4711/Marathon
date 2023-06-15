######
## Stage 1: Clone the Git repository
######
FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/cschneider4711/Marathon.git




######
## Stage 2: Build Angular client parts
######
FROM node:14 AS angular-build
WORKDIR /usr/src/app
COPY --from=clone /app/Marathon/client/angular/package*.json ./
RUN npm install
COPY --from=clone /app/Marathon/client/angular ./
RUN npm run build




######
## Stage 3: Build Java application with Maven
######
FROM maven:3.5-jdk-8-alpine as java-build

WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY --from=clone /app/Marathon/pom.xml /app/pom.xml
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY --from=clone /app/Marathon /app
RUN mvn install




######
## Stage 4: Package everyting into Tomcat and create start script and expose port and define command to start
######
FROM tomcat:7.0.92-jre8-alpine
RUN apk --update --no-cache add curl

# used in other scripts to filter 
LABEL training="marathon"

# used to parameterize detail logging
ENV MARATHON_LOG_PREFIX OFF

WORKDIR /app

# Create a group and user "marathon"
RUN addgroup --system marathon && adduser --system --ingroup marathon --home /home/marathon marathon

COPY --from=java-build /app/target/marathon.war /usr/local/tomcat/webapps
COPY --from=java-build /app/integration/db/hsqldb /app/hsqldb
COPY --from=java-build /app/integration/db/hsqldb/lib/hsqldb.jar /usr/local/tomcat/lib/hsqldb.jar
COPY --from=java-build /app/src/main/java/demo/antivirus/scanFile.sh /home/marathon/marathonScripts/scanFile.sh
COPY --from=java-build /app/src/main/java/demo/util/default.png /home/marathon/marathonImages/default.png

COPY --from=angular-build /usr/src/app/dist/marathon /usr/local/tomcat/webapps/ROOT/poc

RUN chmod 755 /home/marathon/marathonScripts/scanFile.sh

RUN echo "cd /app/hsqldb/bin; ./startMarathonDB.sh &" >> /app/marathon.sh
#RUN echo "sleep 3; cd /usr/local/tomcat/bin; ./startup.sh; tail -f /usr/local/tomcat/logs/catalina.out" >> /app/marathon.sh
RUN echo "sleep 3; cd /usr/local/tomcat/bin; ./catalina.sh jpda start; tail -f /usr/local/tomcat/logs/catalina.out" >> /app/marathon.sh
RUN chmod 755 /app/marathon.sh

# Tell docker that all future commands should run as the "marathon" user
RUN chown -R marathon:marathon /app /usr/local/tomcat /home/marathon
USER marathon

ENV CATALINA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7199 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

# Tomcat
EXPOSE 8080
# Debug
EXPOSE 8000
# JMX
EXPOSE 7199

HEALTHCHECK CMD curl --silent --fail http://localhost:8080/marathon/monitoring | grep true || exit 1
CMD /app/marathon.sh

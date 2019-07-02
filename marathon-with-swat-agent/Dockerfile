######
## Stage 1: Clone the Git repository
######
FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/cschneider4711/SWAT.git


######
## Stage 2: Build application with Maven
######
FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY --from=clone /app/SWAT/serial-whitelist-application-trainer /app
RUN mvn clean package -DskipTests
RUN mv /app/target/serial-whitelist-application-trainer-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/swat.jar


######
## Stage 3: Extend the existing Marathon image to incude startup 
## with SWAT javaagent
######
FROM cschneider4711/marathon:latest

# copy the freshly built SWAT agent into this image
USER root
COPY --from=build /app/swat.jar /app/swat.jar
RUN chown marathon:marathon /app/swat.jar

# set environment variable for Tomcat to prefix JVM start with javaagent SWAT
USER marathon
ENV JAVA_OPTS=-javaagent:/app/swat.jar

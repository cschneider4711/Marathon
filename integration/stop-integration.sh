# stop the local database and tomcat
cd db/hsqldb/bin/
kill `ps aux | grep '[h]sqldb' | awk '{print $2}'`
cd ../../../
cd web/tomcat-port-9090/bin/
unset CATALINA_HOME
./shutdown.sh
cd ../../../

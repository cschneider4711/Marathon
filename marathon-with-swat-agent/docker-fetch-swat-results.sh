echo "############################################################"
echo "XStream based deserialization:"
docker exec -it marathon-swat cat /usr/local/tomcat/bin/whitelist-xstream-with-stacktraces.swat
echo "############################################################"
echo "ObjectInputStream based deserialization:"
docker exec -it marathon-swat cat /usr/local/tomcat/bin/whitelist-ois-with-stacktraces.swat
echo "############################################################"

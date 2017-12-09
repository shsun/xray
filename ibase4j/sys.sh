#!/bin/sh


clear;

mvn clean;

mvn clean install -f pom-sys-service-server.xml 

sleep 1;

mvn clean install -f pom-sys-web-server.xml 


echo 'copy war to tomcat'
sleep 2;
#cp -rfv iBase4J-SYS-Service/target/*.war $TOMCAT_HOME/webapps/
cp -rfv iBase4J-SYS-Web/target/*.war $TOMCAT_HOME/webapps/
echo 'copy war done'
sleep 2;

TOMCAT_HOME=~/Downloads/apache-tomcat-7.0.82
bash $TOMCAT_HOME/bin/shutdown.sh;sleep 3;bash $TOMCAT_HOME/bin/startup.sh;
tail -f $TOMCAT_HOME/logs/catalina.out;


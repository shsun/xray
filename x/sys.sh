#!/bin/sh

#TOMCAT_HOME_2=~/Downloads/apache-tomcat-7.0.82
TOMCAT_HOME_2=~/Downloads/apache-tomcat-7
#TOMCAT_HOME_2=~/Downloads/apache-tomcat-8
#TOMCAT_HOME_2=~/Downloads/apache-tomcat-9

clear;
mvn clean;

sudo chmod -R 777 */target;

mvn clean install -f pom-sys-service-server.xml 
sleep 1;
mvn clean install -f pom-sys-web-server.xml 


echo 'copy war to tomcat'
sleep 2;
cp -rfv iBase4J-SYS-Service/target/*.war $TOMCAT_HOME_2/webapps/
cp -rfv iBase4J-SYS-Web/target/*.war $TOMCAT_HOME_2/webapps/
echo 'copy war done'
sleep 2;


bash $TOMCAT_HOME_2/bin/shutdown.sh;sleep 3;bash $TOMCAT_HOME_2/bin/startup.sh;
tail -f $TOMCAT_HOME_2/logs/catalina.out;


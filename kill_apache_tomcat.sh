#!/bin/sh

tomcat_pid(){
  echo `ps aux | grep apache-tomcat-7.0.82 | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
}

pid=$(tomcat_pid)
if [ -n "$pid" ]
        then
                echo "Tomcat is already running (pid: $pid)"
                kill -9 $pid;
                sleep 10;
        else
                echo "starting.............................................."
fi

clear;
#bash /Users/shsun/Downloads/apache-tomcat-7.0.82/bin/catalina.sh start;
#tail -f /Users/shsun/Downloads/apache-tomcat-7.0.82/logs/catalina.out;


#!/bin/sh


#stop nginx
#ps -ef|grep nginx
#kill -QUIT 2072
#kill -9 nginx

# restart nginx
#$NGINX_HOME/nginx -s reload
#/usr/local/bin/nginx -s reload


#zk
#1. 启动ZK服务:       sh bin/zkServer.sh start
#2. 查看ZK服务状态: sh bin/zkServer.sh status
#3. 停止ZK服务:       sh bin/zkServer.sh stop
#4. 重启ZK服务:       sh bin/zkServer.sh restart




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


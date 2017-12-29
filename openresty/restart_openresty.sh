#!/bin/sh

app_pid(){
  echo `ps aux | grep nginx: | grep -v grep | awk '{ print $2 }'`
}

pid=$(app_pid)
if [ -n "$pid" ]
        then
                echo "nginx is already running (pid: $pid)"
				kill -9 $pid;
				sleep 1;
        else
				echo "starting.............................................."
fi


clear;

#bash /Users/shsun/Downloads/apache-tomcat-7.0.82/bin/catalina.sh start;
#tail -f /Users/shsun/Downloads/apache-tomcat-7.0.82/logs/catalina.out;

#/usr/local/Cellar/openresty/1.13.6.1/nginx/
#NGX_HOME=${OPENRESTY_PREFIX}/nginx;
NGX_HOME=/usr/local/openresty/nginx;


sudo nginx -s quit;
sudo nginx -s stop;
sleep 1;

echo '';
sudo cp -rfv conf/* ${NGX_HOME}/conf/;
echo '';
sudo cp -rfv ./lua/* ${NGX_HOME}/
echo '';


#sleep 1;

sudo nginx -c ${NGX_HOME}/conf/nginx.conf
echo '';

tail -f /opt/logs/openresty_error.log ;

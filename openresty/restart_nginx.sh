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


sudo nginx -s quit;
sudo nginx -s stop;
sleep 1;

sudo cp -rfv conf/nginx.conf /usr/local/openresty/nginx/conf/nginx.conf;
#sleep 1;

sudo nginx -c /usr/local/openresty/nginx/conf/nginx.conf

tail -f /usr/local/openresty/nginx/logs/error.log ;


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

NGX_HOME=${OPENRESTY_PREFIX}/nginx;
#NGX_HOME=/usr/local/openresty/nginx;


nginx -s quit;
nginx -s stop;
sleep 1;

sudo test -d /opt/openresty/ || mkdir -p /opt/openresty;

echo '';
sudo cp -rfv conf/* /opt/openresty/;
echo '';
sudo cp -rfv ./lua/* /opt/openresty/
echo '';


#sleep 1;

nginx -c /opt/openresty/nginx.conf
echo '';

tail -f /opt/openresty/error.log ;

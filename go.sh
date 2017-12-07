#!/bin/sh

echo ""
echo "starting..................... nginx"
sudo redis-cli shutdown
sudo /usr/local/bin/nginx -s reload;
sleep 3;


echo ""
echo "starting..................... zookeeper"
sudo bash /usr/local/share/zookeeper-3.4.6/bin/zkServer.sh stop;
sleep 3;
sudo bash /usr/local/share/zookeeper-3.4.6/bin/zkServer.sh start;
sudo bash /usr/local/share/zookeeper-3.4.6/bin/zkServer.sh status;
sleep 3;


echo ""
echo "starting..................... redis"
sudo redis-cli shutdown;
sleep 3;
sudo /usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf;
sleep 3;


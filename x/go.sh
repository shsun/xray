#!/bin/sh


echo ""
echo ""
echo ""
echo "starting..................... nginx"
sudo redis-cli shutdown
sudo /usr/local/bin/nginx -s reload;
sleep 3;


echo ""
echo ""
echo ""
echo "starting..................... zookeeper"
sudo bash $ZOOKEEPER_HOME/bin/zkServer.sh stop;
sleep 3;
sudo bash $ZOOKEEPER_HOME/bin/zkServer.sh start;
sudo bash $ZOOKEEPER_HOME/bin/zkServer.sh status;
sleep 3;


echo ""
echo ""
echo ""
echo "starting..................... redis"
sudo redis-cli shutdown;
sleep 3;
sudo /usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf;
sleep 3;



















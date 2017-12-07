


---------------------------------------------------- nginx:
/usr/local/etc/nginx/nginx.conf （配置文件路径）
/usr/local/var/www （服务器默认路径）
/usr/local/Cellar/nginx/1.8.0 （安装路径）

/usr/local/bin/nginx -s reload

访问localhost:80，成功说明安装好了




----------------------------------------------------- zookeeper:
/usr/local/share/zookeeper-3.4.6

bin目录，使用zkServer.sh start启动服务
使用jps命令查看，存在QuorumPeerMain进程，表示Zookeeper已经启动

bin目录下，使用zkServer.sh stop停止服务
用jps命令查看，QuorumPeerMain进程已不存在，表示Zookeeper已经关闭




------------------------------------------------------ redis:

https://www.cnblogs.com/chinesern/p/5580665.html

Install :
1. 官网http://redis.io/ 下载最新的稳定版本,这里是3.2.0
2. sudo mv 到 /usr/local/
3. sudo tar -zxf redis-3.2.0.tar 解压文件
4. 进入解压后的目录 cd redis-3.2.0
5. sudo make test 测试编译
6. sudo make install


二. 服务配置
切换到root用户
1. /usr/local 建立相关目录
    sudo mkdir  /usr/local/redis/bin;
    sudo mkdir  /usr/local/redis/etc;
    sudo mkdir  /usr/local/redis/db;
2. 拷贝/usr/local 下的 bin 目录到 /usr/local/redis/bin
    sudo cp -rfv /usr/local/bin /usr/local/redis/bin
3. 拷贝/usr/local/redis/redis-3.2.0/src 下的 mkreleasehdr.sh  到  /usr/local/redis/bin下
    sudo cp -rfv /usr/local/redis3.2.0/src/mkreleasehdr.sh /usr/local/redis/mkreleasehdr.sh;
4. 拷贝 redis.conf 到 /usr/local/redis/etc下
    sudo cp -rfv /usr/local/redis-3.2.0/redis.conf /usr/local/redis/etc
5. 修改 redis.conf
6. 修改权限为当前使用者 xxx
    chown -R xxxx /usr/local/redis
7.启动服务
    cd /usr/local/redis
    ./bin/redis-server ./etc/redis.conf
8.访问服务
    cd /usr/local/redis
    ./bin/redis-cli



/usr/local/share/redis
redis-server &
/etc/init.d/redis_6380 stop
/etc/init.d/redis_6380 start&

/usr/local/redis/bin/redis-server 



#!/bin/sh


echo '';
echo "install these software would take much time"
echo '--------------->> update';
echo '';
sudo apt-get update;


echo '';
echo '';
echo '--------------->> install software 4 openresty/nginx';
echo '';
echo -n "libpcre3 libpcre3-dev zlib1g-dev openssl bless and libssl-dev will be installed"
echo -n "Do you want to continue? [Y/n]"
read input
if [ $input == "Y" ]; then
	# for openresty/nginx
	echo 'install 3rd-payty software for openrety/nginx ............';
	sudo apt-get install libpcre3 libpcre3-dev;
	sudo apt-get install zlib1g-dev;
	sudo apt-get install openssl libssl-dev, bless;
	echo 'done';
else
   	echo "$input"
fi


echo '';
echo '';
echo '--------------->> openresty';
echo '';
echo -n "Do you want to continue? [Y/n]"
read input
if [ $input == "Y" ]; then	
	wget -qO - https://openresty.org/package/pubkey.gpg | sudo apt-key add -;
	sudo apt-get -y install software-properties-common;
	sudo add-apt-repository -y "deb http://openresty.org/package/ubuntu $(lsb_release -sc) main";
	sudo apt-get update;
	sudo apt-get install openresty;
	echo 'done';
else
   	echo "$input"
fi


echo '';
echo '';
echo '--------------->> install preload,vim,redis-server,jdk1.8,ruby,mysql,rabbitMQ';
echo '';
echo -n "Do you want to continue? [Y/n]"
read input
if [ $input == "Y" ]; then
	sudo apt-get install preload;
	sleep 2;
	sudo apt-get install vim;
	sleep 2;
	sudo apt-get update;sudo apt-get install redis-server;
	sleep 2;
	sudo add-apt-repository ppa:webupd8team/java;sudo apt-get update;sudo apt-get install oracle-java8-installer;sudo update-java-alternatives -s java-8-oracle;
	sleep 2;
	sudo apt-get update;sudo apt-get install ruby;
	sleep 2;	
	sudo apt-get install mysql-server;sudo apt isntall mysql-client;sudo apt install libmysqlclient-dev;
	sleep 2;
	sudo echo 'deb http://www.rabbitmq.com/debian/ testing main' | sudo tee /etc/apt/sources.list.d/rabbitmq.list;wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -;sudo apt-get update;sudo apt-get install rabbitmq-server;
	echo 'done';
else
   	echo "$input"
fi









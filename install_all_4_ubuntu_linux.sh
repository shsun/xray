#!/bin/sh


sudo apt-get update;


echo -n "libpcre3 libpcre3-dev zlib1g-dev openssl and libssl-dev will be installed"
echo -n "Do you want to continue? [Y/n]"
read input1

if [ $input == "Y" ] then
	# for openresty/nginx
	echo 'install 3rd-payty software for openrety/nginx ............';
	sudo apt-get install libpcre3 libpcre3-dev;
	sudo apt-get install zlib1g-dev;
	sudo apt-get install openssl libssl-dev;
	echo 'done';
else
   	echo "$input1"
fi







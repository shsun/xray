

========== install openresty on ubuntu
1. apt-get install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl make build-essential
2. tar -xzvf openresty-VERSION.tar.gz 
3. make & make install
	此时，我的系统中已经安装好了Openresty，因为在配置时使用了默认安装路径，所以我的程序被安装到了：
	/usr/local/openresty/中，该文件夹中包含了luajit、lualib、nginx三个目录。
	openresty的配置文件默认是在  /usr/local/openresty/nginx/conf





========== whereis openresty on ubuntu
/usr/bin/openresty /etc/openresty /usr/local/openresty



========== start openresty on OSX/Ubuntu-Linux
sudo /usr/local/openresty/nginx/sbin/nginx -c /usr/local/openresty/nginx/conf/nginx.conf;





http {
	lua_shared_dict dogs 10m; # shared data in lua, you can use this cache in lua via ngx.shared.dogs
   	server {
    	location /set {
        	content_by_lua '
            	local dogs = ngx.shared.dogs
                dogs:set("Jim", 8)
                ngx.say("STORED")
            ';
        }
        location /get {
        	content_by_lua '
            	local dogs = ngx.shared.dogs
            	ngx.say(dogs:get("Jim"))
            ';
       	}
    }
}





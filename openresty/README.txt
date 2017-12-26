

http://www.ttlsa.com/nginx/nginx-and-lua/



configure arguments: --prefix=/usr/local/openresty/nginx --with-debug --with-cc-opt='-DNGX_LUA_USE_ASSERT -DNGX_LUA_ABORT_AT_PANIC -O2' 

--add-module=../ngx_devel_kit-0.3.0 --add-module=../echo-nginx-module-0.61 --add-module=../xss-nginx-module-0.05 
--add-module=../ngx_coolkit-0.2rc3 --add-module=../set-misc-nginx-module-0.31 --add-module=../form-input-nginx-module-0.12 
--add-module=../encrypted-session-nginx-module-0.07 --add-module=../srcache-nginx-module-0.31 
--add-module=../ngx_lua-0.10.11 --add-module=../ngx_lua_upstream-0.07 
--add-module=../headers-more-nginx-module-0.33 --add-module=../array-var-nginx-module-0.05
 --add-module=../memc-nginx-module-0.18 --add-module=../redis2-nginx-module-0.14 --add-module=../redis-nginx-module-0.3.7 
 --add-module=../rds-json-nginx-module-0.15 --add-module=../rds-csv-nginx-module-0.08 
 --add-module=../ngx_stream_lua-0.0.3 --with-ld-opt=-Wl,-rpath,/usr/local/openresty/luajit/lib 
 --with-openssl=/usr/local/openssl 
 --with-stream --with-stream_ssl_module 
 --with-http_ssl_module



========== install openresty on ubuntu
1. install openssh to /usr/local/openssl/ via 
	apt-get install libreadline-dev libncurses5-dev libpcre3-dev libssl-dev perl make build-essential
2. download openresty and tar -xzvf openresty-VERSION.tar.gz 
3. 
	3.1 sudo ./configure --with-debug --with-openssl=/usr/local/openssl/ --with-ngx_http_log_module --with-ngx_syslog_module --with-ngx_errlog_module --with-ngx_epoll_module -j2; 
	3.2 make & make install
	此时，我的系统中已经安装好了Openresty，因为在配置时使用了默认安装路径，所以我的程序被安装到了：
	/usr/local/openresty/中，该文件夹中包含了luajit、lualib、nginx三个目录。
	openresty的配置文件默认是在  /usr/local/openresty/nginx/conf





========== whereis openresty on ubuntu
/usr/bin/openresty /etc/openresty /usr/local/openresty



========== start openresty on OSX/Ubuntu-Linux
sudo /usr/local/openresty/nginx/sbin/nginx -c /usr/local/openresty/nginx/conf/nginx.conf;




http {
	lua_shared_dict shared_data 10m; # shared data in lua, you can use this cache in lua via ngx.shared.dogs
   	server {
    	location /set {
        	content_by_lua '
            	local dogs = ngx.shared.dogs
                dogs:set("Jim", 8)
                ngx.say("STORED")
            ';
        }
        location /get {
			#content_by_lua_file the_lua_file_path;

        	content_by_lua '
            	local dogs = ngx.shared.dogs
            	ngx.say(dogs:get("Jim"))
            ';
       	}
    }
}

 




 

configure arguments: 
--prefix=/usr/local/openresty/nginx --with-debug 
--with-cc-opt='-DNGX_LUA_USE_ASSERT -DNGX_LUA_ABORT_AT_PANIC -O2' 
--add-module=../ngx_devel_kit-0.3.0 
--add-module=../echo-nginx-module-0.61 
--add-module=../xss-nginx-module-0.05 
--add-module=../ngx_coolkit-0.2rc3 
--add-module=../set-misc-nginx-module-0.31 
--add-module=../form-input-nginx-module-0.12 
--add-module=../encrypted-session-nginx-module-0.07 
--add-module=../srcache-nginx-module-0.31 
--add-module=../ngx_lua-0.10.11 
--add-module=../ngx_lua_upstream-0.07 
--add-module=../headers-more-nginx-module-0.33 
--add-module=../array-var-nginx-module-0.05 
--add-module=../memc-nginx-module-0.18 
--add-module=../redis2-nginx-module-0.14 
--add-module=../redis-nginx-module-0.3.7 
--add-module=../rds-json-nginx-module-0.15 --add-module=../rds-csv-nginx-module-0.08 --add-module=../ngx_stream_lua-0.0.3 --with-ld-opt=-Wl,-rpath,/usr/local/openresty/luajit/lib --with-openssl=/usr/local/openssl --with-stream --with-stream_ssl_module --with-http_ssl_module








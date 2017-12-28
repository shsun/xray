
http://tool.oschina.net/uploads/apidocs/nginx-zh/HttpLuaModule.htm#init_by_lua_file

http://www.ttlsa.com/nginx/nginx-and-lua/






post-read
	读取请求内容阶段，nginx读取并解析完请求头之后就立即开始运行	
	譬喻模块 ngx_realip 就在 post-read 阶段注册了处理惩罚措施，它的成果是迫使 Nginx 认为当前请求的来历地点是指定的某一个请求头的值。

server-rewrite
	server请求地址重写阶段
	当ngx_rewrite模块的set设置指令直接书写在server设置块中时，根基上都是运行在server-rewrite阶段

find-config
	配置查找阶段，用来完成当前请求与location配重块之间的配对工作；
	设置查找阶段，这个阶段并不支持Nginx模块注册处理惩罚措施，而是由Nginx焦点来完成当前请求与location设置块之间的配对事情。
	
rewrite
	location请求地址重写阶段，当ngx_rewrite指令用于location中，就是再这个阶段运行的；
	location请求地点重写阶段，当ngx_rewrite指令用于location中，就是再这个阶段运行的；别的ngx_set_misc(配置md5、encode_base64等)模块的指令，尚有ngx_lua模块的set_by_lua指令和rewrite_by_lua指令也在此阶段。
	
post-rewrite
	请求地址重写提交阶段，当nginx完成rewrite阶段所要求的内部跳转动作，如果rewrite阶段有这个要求的话
	请求地点重写提交阶段，当nginx完成rewrite阶段所要求的内部跳动弹作，假如rewrite阶段有这个要求的话
	
	
preaccess
	ngx_limit_req和ngx_limit_zone在这个阶段运行，ngx_limit_req可以控制请求的访问频率，ngx_limit_zone可以控制访问的并发度；
	会见权限查抄筹备阶段，ngx_limit_req和ngx_limit_zone在这个阶段运行，ngx_limit_req可以节制请求的会见频率，ngx_limit_zone可以节制会见的并发度
	
	
access
	权限检查阶段，ngx_access在这个阶段运行，配置指令多是执行访问控制相关的任务，如检查用户的访问权限，检查用户的来源IP是否合法
	会见权限查抄阶段，尺度模块ngx_access、第三方模块ngx_auth_request以及第三方模块ngx_lua的access_by_lua指令就运行在这个阶段。设置指令多是执行会见节制相关的任务，如查抄用户的会见权限，查抄用户的来历IP是否正当
	
post-access
	访问权限检查提交阶段
	会见权限查抄提交阶段；主要用于共同access阶段实现尺度ngx_http_core模块提供的设置指令satisfy的成果。satisfy
all(与干系),satisfy any(或干系)

try-files
	配置项try_files处理阶段
	专门用于实现尺度设置指令try_files的成果
	假如前 N-1个参数所对应的文件系统工具都不存在，try-files 阶段就会当即提倡“内部跳转”到最后一个参数(即第 N个参数)所指定的URI

content
	内容产生阶段，是所有请求处理阶段中最为重要的阶段，因为这个阶段的指令通常是用来生成HTTP响应内容并输出HTTP响应的使命

log
	日志模块处理阶段, 记录日志




Nginx下Lua处理惩罚阶段与利用范畴：


作者：Gaolex
链接：https://www.jianshu.com/p/abaff828100d
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
| command    				| syntax    |  context  |
| ----------------------   	| -----:     | :----: |
| init_by_lua        		| $1         |   5    |
| set_by_lua        			| $1         |   6    |
| rewrite_by_lua        		| $1         |   7    |
| access_by_lua        		| $1         |   7    |
| content_by_lua        		| $1         |   7    |
| header_filter_by_lua      | $1         |   7    |
| body_filter_by_lua        | $1         |   7    |
| log_by_lua        			| $1         |   7    |
| rewrite_by_lua        		| $1         |   7    |



init_by_lua            http											loading-config
set_by_lua             server, server if, location, location if		server-rewrite, rewrite
rewrite_by_lua         http, server, location, location if
access_by_lua          http, server, location, location if			access tail
content_by_lua         location, location if
header_filter_by_lua   http, server, location, location if
body_filter_by_lua     http, server, location, location if
log_by_lua             http, server, location, location if
timer

init_by_lua:
在nginx从头加载设置文件时，运行内里lua剧本，常用于全局变量的申请。
譬喻lua_shared_dict共享内存的申请，只有当nginx重起后，共享内存数据才清空，这常用于统计。

set_by_lua:
配置一个变量，常用与计较一个逻辑，然后返回功效
该阶段不能运行Output API、Control API、Subrequest API、Cosocket API

rewrite_by_lua:
在access阶段前运行，主要用于rewrite

access_by_lua:
主要用于会见节制，能收集到大部门变量，雷同status需要在log阶段才有。
这条指令运行于nginx access阶段的末端，因此老是在 allow 和 deny 这样的指令之后运行，固然它们同属 access
阶段。

content_by_lua:
阶段是所有请求处理惩罚阶段中最为重要的一个，运行在这个阶段的设置指令一般都负担着生成内容(content)并输出HTTP响应。

header_filter_by_lua:
一般只用于配置Cookie和Headers等
该阶段不能运行Output API、Control API、Subrequest API、Cosocket API









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
	3.1 
	debug:
		clear;sudo ./configure --with-openssl=/usr/local/openssl/ --add-module=3rd_party_modules/lua-resty-core --add-module=3rd_party_modules/lua-resty-module --with-debug -j2; 
	release:
		clear;sudo ./configure --with-openssl=/usr/local/openssl/ --add-module=3rd_party_modules/lua-resty-core --add-module=3rd_party_modules/lua-resty-module -j2; 
	
	
	
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








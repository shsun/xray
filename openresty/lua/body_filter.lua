if ngx.var.request_uri ~= ngx.var.constant_location then
    return constant_default_upstream;
end


--[[
    
It contains multiple addresses only if nginx connected to them (e.g. because first one was down and nginx had to connect to another one). In normal operation there usually only one address. Anyway you could consider last one as 'current'

ngx.var.upstream_addr container multiple addresses only if nginx connected to them (e.g. because first one was down and nginx had to connect to another one). 
In normal operation there usually only one address. Anyway you could consider last one as 'current'



example: 192.168.1.1:80, 192.168.1.2:80, unix:/tmp/sock. You can split the return value by comma:

local addrs = _.split(ngx.var.upstream_addr, ',')
if #addrs > 0 then
    ngx.log(ngx.ERR, addrs[#addrs])
end
The last one always is the current one



Here is a question about how to modify the targeted server's ip at runtime.


sudo code as bellow:


1. call http://xxxxxxxxxxx.com/go.sdo at client side

2. 
    if nil == shared_dic[client_ip] then
        shared_dic[client_ip]
    end
local previous_target_upstream_server = shared_dict[client_ip];
if previous_target_upstream_server == targeted_upstream_server;
    
    
    targeted_upstream_server
    
        
end







]]
local upstream_addr_str = nil;
if nil ~= ngx.var.upstream_addr then
    upstream_addr_str = " from "..ngx.var.upstream_addr;
else
    upstream_addr_str = "";
end

--ngx.log(ngx.INFO, 'body_filter @@@@@@@@@@@@@@@@--->>', "upstream_addr_str="..upstream_addr_str);
ngx.log(ngx.INFO, '');


--ngx.var.upstream_addr = string.gsub(ngx.var.upstream_addr,'8081','8082');

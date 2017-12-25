--[[
http://tool.oschina.net/uploads/apidocs/nginx-zh/HttpLuaModule.htm#set_by_lua
]]

-- http://blog.csdn.net/weiyuefei/article/details/38434797

local var = ngx.var  
--ngx.say("???????????<br/>") 

local ups = ngx.req.get_uri_args()["ups"]  
if ups == nil then  
 	ngx.say("usage: /lua_file?ups=x.x.x.x")  
    return  
end

local host = ngx.var.http_host;  
local ups_from = ngx.shared.shared_dict:get(host);
--ngx.log(ngx.WARN, host, " update upstream from ", ups_from, " to ", ups);  
ngx.say("%s-%s-%s-%s-%s-%s---", host, " update upstream from ", ups_from, " to ", ups, "<br/>");

ngx.shared.shared_dict:set(host, ups);  
ngx.say(host, " update upstream from ", ups_from, " to ", ups);


return ups;




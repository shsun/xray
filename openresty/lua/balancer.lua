local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")
local balancer = require("ngx.balancer");


-- shared dictionary
local shared_dict = ngx.shared.shared_dict;
-- client ip
local remote_addr = ngx.var.remote_addr;
--
local http_host = ngx.var.http_host;
--
local http_user_agent = ngx.var.http_user_agent;

local access;
local succ, err, forcible;
if shared_dict:get(remote_addr) == nil then
    access = {remote_addr=remote_addr, method='xu', times=1, upstream_addr=nil};
    succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));
end

access = cjson.decode( shared_dict:get(remote_addr) );
access['times'] = access['times'] + 1;
succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));


local upstream_addr = "nil";
if nil ~= access['upstream_addr'] then
    upstream_addr = access['upstream_addr'];
end
ngx.log(ngx.INFO, "times="..access['times']..", upstream_addr="..upstream_addr);

local backend = "";
local ok, err;
if access['times'] >= 4 then
    shared_dict:delete(remote_addr);
    backend = "8081";
    ok, err = balancer.set_current_peer("127.0.0.1", backend)
else
    backend = "8081";
    ok, err = balancer.set_current_peer("192.168.1.170", backend)
end
if not ok then
    ngx.log(ngx.ERR, "***********************failed to set the current peer: ", err)
    return ngx.exit(500)
end







ngx.log(ngx.DEBUG, "***********************current peer ", backend);






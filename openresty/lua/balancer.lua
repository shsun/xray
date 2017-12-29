local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")
local balancer = require("ngx.balancer");


-- shared dictionary
local cache_request_history = ngx.shared.cache_request_history;
-- client ip
local remote_addr = ngx.var.remote_addr;
--
local http_host = ngx.var.http_host;
--
local http_user_agent = ngx.var.http_user_agent;

local access;
local succ, err, forcible;
if cache_request_history:get(remote_addr) == nil then
    access = {remote_addr=remote_addr, method='xu', times=0, fullpath=nil};
    succ, err, forcible = cache_request_history:set(remote_addr, cjson.encode(access));
end

access = cjson.decode( cache_request_history:get(remote_addr) );
access['times'] = access['times'] + 1;
succ, err, forcible = cache_request_history:set(remote_addr, cjson.encode(access));

local fullpath = "nil";
if nil ~= access['fullpath'] then
    fullpath = access['fullpath'];
end

local port;
local upstream_addr = "192.168.1.170";
--local upstream_addr = "127.0.0.1";
if access['times'] >= 3 then
    cache_request_history:delete(remote_addr);
    port = "8081";
else
    port = "8082";
end
local ok, err = balancer.set_current_peer(upstream_addr, port);

if not ok then
    ngx.log(ngx.ERR, "***********************failed to set the current peer: ", err)
    return ngx.exit(500)
end

ngx.log(ngx.DEBUG, "***********************current peer ", 'upstream_addr='..upstream_addr, ' port='..port);




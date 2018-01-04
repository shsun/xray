local math = require('math');
local string = require('string');
local table = require('table');
local cjson = require('cjson');
local balancer = require('ngx.balancer');

if nil ~= string.find(ngx.var.request_uri, 'favicon.ico') then
    return;
end

local cache_request_history = ngx.shared.cache_request_history;
local remote_addr = ngx.var.remote_addr;
-- local http_host = ngx.var.http_host;
-- local http_user_agent = ngx.var.http_user_agent;

local is_download_action = false;

local port = {8081, 8082, 8083};
local upstream_addr = '192.168.1.170';

if nil == string.find(ngx.var.request_uri, 'docs/') then
    is_download_action = true;
end

local access;
local succ, err, forcible;
if cache_request_history:get(remote_addr) == nil then
    access = {remote_addr=remote_addr, method='xu', times=0, scheme=nil, server_addr=nil, request_uri=nil};
    succ, err, forcible = cache_request_history:set(remote_addr, cjson.encode(access));
end

access = cjson.decode( cache_request_history:get(remote_addr) );
-- force reset the current server_addr to previous server_addr if
local previous_remote_addr = access['remote_addr'];
local previous_server_addr = access['server_addr'];
local previous_request_uri = access['request_uri'];
if is_download_action and previous_remote_addr == ngx.var.remote_addr and previous_request_uri == ngx.var.request_uri then
    ngx.var.server_addr = previous_server_addr;
end
access['times'] = access['times'] + 1;
access['scheme'] = ngx.var.scheme;
access['server_addr'] = ngx.var.server_addr;
access['request_uri'] = ngx.var.request_uri;
succ, err, forcible = cache_request_history:set(remote_addr, cjson.encode(access));


--local upstream_addr = '127.0.0.1';
--[[
if access['times'] >= 3 then
    cache_request_history:delete(remote_addr);
    port = '8081';
else
    port = '8082';
end

local ok, err = balancer.set_current_peer(upstream_addr, port);
if not ok then
    ngx.log(ngx.ERR, '***********************failed to set the current peer: ', err)
    return ngx.exit(500)
end
]]



ngx.log(ngx.DEBUG, '***********************current peer ',
    ' remote_addr='..ngx.var.remote_addr,
    ' server_port='..ngx.var.server_port..
    ' scheme='..ngx.var.scheme..' server_addr='..ngx.var.server_addr..', request_uri='..ngx.var.request_uri..', uri='..ngx.var.uri);

for key, value in pairs(ngx.ctx) do
    ngx.log(ngx.DEBUG, key..'='..value);
end



ngx.log(ngx.INFO, '\n\n');

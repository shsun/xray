--[[

]]
local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

local function sayhi()
    ngx.log(ngx.INFO, "\n\n\n\nx.sayhi");
    -- shared dictionary
    local shared_dict = ngx.shared.shared_dict;
    -- client ip
    local remote_addr = ngx.var.remote_addr;
    --
    local http_host = ngx.var.http_host;
    --
    local http_user_agent = ngx.var.http_user_agent;

    --ngx.req.set_uri_args("r"..math.random(0, 999999999));

    --
    if shared_dict:get(remote_addr) == nil then
        local t = {remote_addr=remote_addr, method='xu', times=0};
        local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(t));
    end

    local access = cjson.decode( shared_dict:get(remote_addr) );
    if access == nil then
        ngx.log(ngx.INFO, "access is nil");
        return;
    else
        ngx.log(ngx.INFO, "remote_addr=", access["remote_addr"]);
    end
    
    access['times'] = access['times'] + 1;
    local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));
    ngx.log(ngx.INFO, '@@@@@@@@@@@@@@@@--->>', access['times'], ",  " , succ, err, forcible,"  target=", ngx.var.target);
    ngx.log(ngx.ERR, '@@@@@@@@@@@@@@@@--->>', ngx.var.upstream_addr);
    
    
    if access['times'] >= 4 then
        shared_dict:delete(remote_addr);
        ngx.var.target = '127.0.0.1:8082/examples'
        return
    else
        ngx.log(ngx.WARN, 'else times=', access['times'], "\n");
    end
    
    local key = ngx.var.http_user_agent;
    if not key then
        ngx.log(ngx.ERR, "no user-agent found");
        return ngx.exit(400);
    end
    --ngx.var.target = '127.0.0.1:8081/examples';
end


local x = {
    sayhi           = sayhi,
};
return x;



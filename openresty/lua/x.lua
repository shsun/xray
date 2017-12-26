--[[

]]
local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

local function sayhi()    
    local default_upstream = 'default_doc_upstream';    
    if ngx.var.request_uri ~= '/docs/' then
        return default_upstream;
    end
    
    -- shared dictionary
    local shared_dict = ngx.shared.shared_dict;
    -- client ip
    local remote_addr = ngx.var.remote_addr;
    --
    local http_host = ngx.var.http_host;
    --
    local http_user_agent = ngx.var.http_user_agent;

    --
    -- ngx.req.set_uri_args("r"..math.random(0, 999999999));
    
    
    --
    if shared_dict:get(remote_addr) == nil then
        local t = {remote_addr=remote_addr, method='xu', times=0};
        local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(t));
    end

    local access = cjson.decode( shared_dict:get(remote_addr) );
    if access == nil then
        return default_upstream;
    else
        ngx.log(ngx.ERR, "remote_addr=", access["remote_addr"]);
    end
    
    access['times'] = access['times'] + 1;
    local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));
    ngx.log(ngx.INFO, '@@@@@@@@@@@@@@@@--->>', access['times'], ",  " , succ, err, forcible, ' http_host='..ngx.var.http_host, ' request_uri='..ngx.var.request_uri);
    ngx.log(ngx.ERR, '@@@@@@@@@@@@@@@@--->>', "request_uri="..ngx.var.request_uri, ", scheme="..ngx.var.scheme, ", host="..ngx.var.host);
       
    if access['times'] >= 3 then
        shared_dict:delete(remote_addr);
        ngx.var.target = '127.0.0.1:8082/examples';
        default_upstream = ngx.var.target;
    else
        ngx.log(ngx.INFO, '####################----', access['times'], "\n");
    end
    return default_upstream;
end



local x = {
    sayhi           = sayhi,
};

return x;
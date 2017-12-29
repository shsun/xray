--[[

]]
local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

local function sayhi()
    local constant_default_upstream = 'default_doc_upstream';
    
    -- shared dictionary
    local shared_dict = ngx.shared.shared_dict;
    -- client ip
    local remote_addr = ngx.var.remote_addr;
    --
    local http_host = ngx.var.http_host;
    --
    local http_user_agent = ngx.var.http_user_agent;

    --ngx.req.set_uri_args("r="..math.random(0, 999999999));

    if shared_dict:get(remote_addr) == nil then
        local t = {remote_addr=remote_addr, method='xu', times=1, body_filter_times=0};
        local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(t));
    end

    local access = cjson.decode( shared_dict:get(remote_addr) );
    local upstream_addr = "nil";
    if nil ~= access['upstream_addr'] then
        upstream_addr = access['upstream_addr'];
    end
    ngx.log(ngx.INFO, "times="..access['times'].." body_filter_times="..access['body_filter_times']..", upstream_addr="..upstream_addr);

    if access['times'] >= 4 then
        shared_dict:delete(remote_addr);
        --ngx.var.target = '127.0.0.1:8082/examples';
        constant_default_upstream = ngx.var.target;
    end

    return constant_default_upstream;
end



local x = {
    sayhi           = sayhi,
};

return x;

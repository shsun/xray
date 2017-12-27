local cjson = require("cjson");

-- shared dictionary
local shared_dict = ngx.shared.shared_dict;
-- client ip
local remote_addr = ngx.var.remote_addr;

if shared_dict:get(remote_addr) ~= nil then
    local access = cjson.decode( shared_dict:get(remote_addr) );
    access['times'] = access['times'] - (access['body_filter_times'] - 1 );
    access['body_filter_times'] = 0;
    local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));
    access = cjson.decode( shared_dict:get(remote_addr) );
    ngx.log(ngx.INFO, "times="..access['times'].." body_filter_times="..access['body_filter_times']);
end

--
if nil ~= ngx.var.upstream_response_time and 'number' == type(ngx.var.upstream_response_time) then
    local resp_time_so_far = ngx.now() - tonumber(ngx.var.upstream_response_time);
    -- 1s
    if tonumber(ngx.var.upstream_response_time) >= 1 then
        ngx.log(ngx.WARN, "[SLOW] request_time="..ngx.var.request_time.." upstream_response_time="..ngx.var.upstream_response_time..upstream_addr_str);
    end
end

local cjson = require("cjson");

local upstream_addr = nil;
if nil ~= ngx.var.upstream_addr then
    upstream_addr = ngx.var.upstream_addr;
else
    upstream_addr = nil;
end

ngx.log(ngx.INFO, "upstream_addr="..upstream_addr);

--
if nil ~= ngx.var.upstream_response_time and 'number' == type(ngx.var.upstream_response_time) then
    local resp_time_so_far = ngx.now() - tonumber(ngx.var.upstream_response_time);
    -- 1s
    if tonumber(ngx.var.upstream_response_time) >= 1 then
        ngx.log(ngx.WARN, "[SLOW] request_time="..ngx.var.request_time.." upstream_response_time="..ngx.var.upstream_response_time..", upstream_addr="..upstream_addr);
    end
end

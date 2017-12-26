if ngx.var.request_uri ~= ngx.var.constant_location then
    return constant_default_upstream;
end


local upstream_addr_str = nil;
if nil ~= ngx.var.upstream_addr then
    upstream_addr_str = " from "..ngx.var.upstream_addr;
else
    upstream_addr_str = "";
end

ngx.log(ngx.INFO, 'body_filter @@@@@@@@@@@@@@@@--->>', "upstream_addr_str="..upstream_addr_str);



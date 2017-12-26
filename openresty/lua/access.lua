local upstream_addr_str = nil;
if nil ~= ngx.var.upstream_addr then
    upstream_addr_str = " from "..ngx.var.upstream_addr;
else
    upstream_addr_str = "";
end

ngx.log(ngx.ERR, 'access @@@@@@@@@@@@@@@@--->>'..upstream_addr_str);



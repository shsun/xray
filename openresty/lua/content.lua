ngx.log(ngx.INFO, 'content @@@@@@@@@@@@@@@@--->>');

local upstream_addr_str = nil;
if nil ~= ngx.var.upstream_addr then
    upstream_addr_str = " from "..ngx.var.upstream_addr;
else
    upstream_addr_str = "";
end

ngx.log(ngx.INFO, 'content @@@@@@@@@@@@@@@@--->>', "upstream_addr_str="..upstream_addr_str);

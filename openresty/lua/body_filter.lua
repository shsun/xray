local cjson = require("cjson");

-- shared dictionary
local shared_dict = ngx.shared.shared_dict;
-- client ip
local remote_addr = ngx.var.remote_addr;

ngx.log(ngx.INFO, "remote_addr="..remote_addr);



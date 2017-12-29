local cjson = require("cjson");

-- shared dictionary
local cache_request_history = ngx.shared.cache_request_history;
-- client ip
local remote_addr = ngx.var.remote_addr;

--ngx.log(ngx.INFO, "remote_addr="..remote_addr);

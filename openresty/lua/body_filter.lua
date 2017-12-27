local cjson = require("cjson");

-- shared dictionary
local shared_dict = ngx.shared.shared_dict;
-- client ip
local remote_addr = ngx.var.remote_addr;

ngx.log(ngx.INFO, "remote_addr="..remote_addr);


if shared_dict:get(remote_addr) ~= nil then
    local access = cjson.decode( shared_dict:get(remote_addr) );
    access['times'] = access['times'] + 1;
    access['body_filter_times'] = access['body_filter_times'] + 1;
    local succ, err, forcible = shared_dict:set(remote_addr, cjson.encode(access));
    
    ngx.log(ngx.INFO, "times="..access['times'].." body_filter_times="..access['body_filter_times']);
end




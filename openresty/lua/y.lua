local _M = { _VERSION = '1.0' }

function _M:sayhi()

    print("\n\n\n\ny.sayhi");

    local key = ngx.var.http_user_agent;
    if not key then
        ngx.log(ngx.ERR, "no user-agent found");
        return ngx.exit(400);
    end
    local host = "127.0.0.1:8081";

    ngx.log(ngx.INFO, "go to 163...?");

    --ngx.var.target = host;
end

return _M

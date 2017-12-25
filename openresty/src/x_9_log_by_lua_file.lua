
-- /usr/local/openresty/nginx/logs/error.log

if ngx.var.log_val ~= nil then
    function mylog(msg)
        file = io.open ("/usr/local/openresty/nginx/logs/error.log","a+")
        file:write (msg)
        file:flush();
        file:close();
    end

    ngx.var.log_val = ngx.var.log_val.."] httpstatus="..ngx.var.status.." request_time="..ngx.var.request_time.."\n"

    mylog(ngx.var.log_val)
end



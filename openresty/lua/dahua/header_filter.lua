--[[
要保证正确设置响应头，这样浏览器如果发送了指定请求头后，就可以 无限期缓存静态文件，是的用户只需下载一次即可。
这个重写规则使得任何静态文件，如果请求参数中包含时间戳值，那么就设置相应的Expires和Cache-Control响应头
]]
if ngx.var.query_string and ngx.re.match( ngx.var.query_string, "^([0-9]{10})$" ) then
    ngx.header["Expires"] = ngx.http_time( ngx.time() + 31536000 );
    ngx.header["Cache-Control"] = "max-age=31536000";
end

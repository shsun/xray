--[[
http://tool.oschina.net/uploads/apidocs/nginx-zh/HttpLuaModule.htm#content_by_lua


syntax: content_by_lua <lua-script-str>

context: location, location if

phase: content

Acts as a "content handler" and executes Lua code string specified in <lua-script-str> for every request. The Lua code may make API calls and is executed as a new spawned coroutine in an independent global environment (i.e. a sandbox).

Do not use this directive and other content handler directives in the same location. For example, this directive and the proxy_pass directive should not be used in the same location.

]]
-- require("Access");
local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

function TableToStr(t)
    if t == nil then return "" end
    local retstr= "{"

    local i = 1
    for key,value in pairs(t) do
        local signal = ","
        if i==1 then
            signal = ""
        end

        if key == i then
            retstr = retstr..signal..ToStringEx(value)
        else
            if type(key)=='number' or type(key) == 'string' then
                retstr = retstr..signal..'['..ToStringEx(key).."]="..ToStringEx(value)
            else
                if type(key)=='userdata' then
                    retstr = retstr..signal.."*s"..TableToStr(getmetatable(key)).."*e".."="..ToStringEx(value)
                else
                    retstr = retstr..signal..key.."="..ToStringEx(value)
                end
            end
        end

        i = i+1
    end

    retstr = retstr.."}"
    return retstr
end


function StrToTable(str)
    if str == nil or type(str) ~= "string" then
        return
    end

    return loadstring("return " .. str)()
end

function ToStringEx(value)
    if type(value)=='table' then
        return TableToStr(value)
    elseif type(value)=='string' then
        return "\'"..value.."\'"
    else
        return tostring(value)
    end
end


local host = ngx.var.http_host;

-- cache ip
local ups = ngx.req.get_uri_args()["ups"]

ngx.log(ngx.DEBUG, 'log.info............')
ngx.say('say............')
print([[i am print]])

if ngx.shared.shared_dict:get(ngx.var.remote_addr) == nil then
    local t = {remote_addr=ngx.var.remote_addr, method='xu', times=0};
    local succ, err, forcible = ngx.shared.shared_dict:set(ngx.var.remote_addr, TableToStr(t));
    ngx.say('put the access into shared_dict-1->', t['remote_addr'], t['method'], t['times']);
    ngx.say('put the access into shared_dict-2->', succ, err, forcible);
    ngx.say("\n");
end
--local json_obj = cjson.decode( ngx.shared.shared_dict:get(host) )
local access = StrToTable( ngx.shared.shared_dict:get(ngx.var.remote_addr) );
if access == nil then
    ngx.say("access is nil");
else
    for key, value in pairs(access) do
        ngx.say(key, ":", value);
    end
end
ngx.say("\n");

ngx.say('before times=', access['times'], "\n");
access['times'] = access['times'] + 1;
ngx.say('after times=', access['times'], "\n");
local succ, err, forcible = ngx.shared.shared_dict:set(ngx.var.remote_addr, TableToStr(access));
ngx.say('reset->', succ, err, forcible);


     ngx.exec("/bar", "a=goodbye");


if access['times'] >= 2 then
    ngx.say("go baidu.com", access['times']);
    
    local host = ngx.var.http_host
    local ups_from = ngx.shared.shared_dict:get(host);

    ngx.log(ngx.WARN, host, " update upstream from ", ups_from, " to ", ups)

    ngx.shared.shared_dict:set(host, ups);
    ngx.say(host, " update upstream from ", ups_from, " to ", ups)



local res = ngx.location.capture("/upstream",  
        {  
             method = ngx.HTTP_GET,  
             vars = {  
                 my_upstream = "remote_hello",  
                 my_uri = "/hello",  
             },  
        }  
    )
    
---     ngx.exec("/bar", "a=goodbye");







    ngx.shared.shared_dict:delete(ngx.var.remote_addr);
else
    ngx.say('else times=', access['times'], "\n");
end





--[[
local ups = ngx.req.get_uri_args()["ups"]       
if ups == nil then
	ngx.say("usage: curl /_switch_upstream?upstream=unix:/path-to-sock-file")
	return
end
]]


--[[
local host = ngx.var.http_host
local ups_src = ngx.shared.shared_dict:get(host)
ngx.log(ngx.WARN, host, " change upstream from ", ups_src, " to ", ups)
ngx.shared.shared_dict:set(host,  ups)
ngx.say(host, " change upstream from ", ups_src, " to ", ups)
]]

return


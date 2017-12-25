local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

require "x_base_class"

xutils = class(x_base_class)

function xutils:table2string(t)
    if t == nil then return "" end
    local retstr= "{"

    local i = 1
    for key,value in pairs(t) do
        local signal = ","
        if i==1 then
            signal = ""
        end

        if key == i then
            retstr = retstr..signal..xutils:ToStringEx(value)
        else
            if type(key)=='number' or type(key) == 'string' then
                retstr = retstr..signal..'['..xutils:ToStringEx(key).."]="..xutils:ToStringEx(value)
            else
                if type(key)=='userdata' then
                    retstr = retstr..signal.."*s"..xutils:table2string(getmetatable(key)).."*e".."="..xutils:ToStringEx(value)
                else
                    retstr = retstr..signal..key.."="..xutils.ToStringEx(value)
                end
            end
        end

        i = i+1
    end

    retstr = retstr.."}"
    return retstr
end


function xutils:string2table(str)
    if str == nil or type(str) ~= "string" then
        return
    end

    return load("return " .. str)()
end

function xutils:ToStringEx(value)
    if type(value)=='table' then
        return xutils:table2string(value)
    elseif type(value)=='string' then
        return "\'"..value.."\'"
    else
        return tostring(value)
    end
end

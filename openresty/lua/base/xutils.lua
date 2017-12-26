local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")

xutils = { balance=0,
    table2string = function (self, t)
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
                        retstr = retstr..signal.."*s"..table2string(getmetatable(key)).."*e".."="..ToStringEx(value)
                    else
                        retstr = retstr..signal..key.."="..ToStringEx(value)
                    end
                end
            end

            i = i+1
        end

        retstr = retstr.."}"
        return retstr
    end,
    string2table = function (self, str)
        if str == nil or type(str) ~= "string" then
            return
        end

        return load("return " .. str)()
    end,

    ToStringEx = function (self, value)
        if type(value)=='table' then
            return table2string(value)
        elseif type(value)=='string' then
            return "\'"..value.."\'"
        else
            return tostring(value)
        end
    end

}

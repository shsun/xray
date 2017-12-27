
--[[

1. fetch json-obj from shared_dic at set_by or rewrite 
2. save an json-obj in shared_dic if step-1 is nil and re-fetch it at set_by or rewrite.
3. return responsed proxy_pass via json-obj.times at set_by or rewrite
4. increase times&body_filter_times at body_filter
5. reset times&body_filter_times at body_filter at log
6. re-visite the url, go-to step-1-->step-3-->step-4-->step-5;

]]

local math = require('math')
local string = require("string")
local table = require("table")
local cjson = require("cjson")


local function to_json()
    self.times = self.times + 1;
    self.body_filter_times = self.body_filter_times + 1;
end

local function to_string()
    self.times = self.times + 1;
    self.body_filter_times = self.body_filter_times + 1;
end

local function increate_at_body_filter()
    self.times = self.times + 1;
    self.body_filter_times = self.body_filter_times + 1;
end

local function validate_at_log()
    self.times = self.times - (self.body_filter_times - 1);
    self.body_filter_times = 0;
end


local x = {
    remote_addr=nil,
    method=nil,
    times=0,
    body_filter_times=0,
    targeted_upstream_addr=nil,
    increate_at_body_filter=increate_at_body_filter,
    validate_at_log=validate_at_log
};

return x;

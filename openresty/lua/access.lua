

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

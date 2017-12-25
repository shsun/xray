
require("math");



local function main()
    print("in the function")
    --dosomething()
    local x = 10
    local y = 20
    return x + y
end

local a = main;

print(a());

local t = main();
print(t);

--
-- Created by IntelliJ IDEA.
-- User: shsun
-- Date: 1/4/18
-- Time: 13:42
-- To change this template use File | Settings | File Templates.
--

require('Shape');

Rectangle = Shape:new()
-- 派生类方法 new
function Rectangle:new (o,length,breadth)
    o = o or Shape:new(o)
    setmetatable(o, self)
    self.__index = self
    self.area = length * breadth
    return o
end

-- 派生类方法 printArea
function Rectangle:printArea ()
    print("矩形面积为 ",self.area)
end


--[[
-- 创建对象
myrectangle = Rectangle:new(nil, 10, 20);
myrectangle:printArea();
]]
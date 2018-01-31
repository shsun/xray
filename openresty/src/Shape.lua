--
-- Created by IntelliJ IDEA.
-- User: shsun
-- Date: 1/4/18
-- Time: 13:43
-- To change this template use File | Settings | File Templates.
--



Shape = {area = 0}
function Shape:new (o,side)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    side = side or 0
    self.area = side*side;
    return o
end
-- 基础类方法 printArea
function Shape:printArea ()
    print("面积为 ",self.area)
end

--[[
-- 创建对象
myshape = Shape:new(nil, 10);
myshape:printArea();
]]



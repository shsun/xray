--
-- Created by IntelliJ IDEA.
-- User: shsun
-- Date: 1/4/18
-- Time: 13:44
-- To change this template use File | Settings | File Templates.
--

require('Shape');

Square = Shape:new()
-- 派生类方法 new
function Square:new (o, side)
    o = o or Shape:new(o, side)
    setmetatable(o, self)
    self.__index = self
    return o
end

function Square:printArea ()
    print("正方形面积为 ", self.area)
end


--[[
-- 创建对象
mysquare = Square:new(nil, 10);
mysquare:printArea();
]]





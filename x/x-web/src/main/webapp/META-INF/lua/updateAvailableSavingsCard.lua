--
-- Created by IntelliJ IDEA.
-- User: shsun
-- Date: 1/18/18
-- Time: 09:41
-- To change this template use File | Settings | File Templates.
--


-- 操作可用储蓄卡,当客户购买则减,卖家增加可用库存则加，使用lua脚本redis操作的保证原子性
redis.call('INCRBY', KEYS[1], ARGV[1]);

local current = redis.call('GET', KEYS[1]);
local result = { success = true };
result['@class'] = 'org.meibaobao.ecos.basecomponent.common.Result';
local encodestr;

if tonumber(current) < 0
then
    redis.call('DECRBY', KEYS[1], ARGV[1]);
    current = redis.call('GET', KEYS[1]);
    result["success"] = false;
else
    result["success"] = true;
end
result["data"] = current;
encodestr = cjson.encode(result);
-- print(encodestr);
return encodestr;




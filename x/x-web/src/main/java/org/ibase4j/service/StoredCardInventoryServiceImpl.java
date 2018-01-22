package org.ibase4j.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class StoredCardInventoryServiceImpl {

    @Resource(name = "updateAvailableSavingsCard")
    private RedisScript<Map<String, Object>> updateAvailableSavingsCard;

    @Resource(name = "initAvailableSavingsCard")
    private RedisScript<Boolean> initAvailableSavingsCard;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    public static String availableStotredCardInventoryNamePre = "availableStotredCard_";

    public StoredCardInventoryServiceImpl(){
        super();
    }


    public Map<String, Object> availableStotredCardInventory(String storedCardSchemeNo, int num) {
        List list = Collections.singletonList(availableStotredCardInventoryNamePre + storedCardSchemeNo);
        Map<String, Object> result = redisTemplate.execute(updateAvailableSavingsCard, list, num);
        return result;
    }

    public Boolean initAvailableStotredCardInventory(String storedCardSchemeNo, int num) {
        List list = Collections.singletonList(availableStotredCardInventoryNamePre + storedCardSchemeNo);
        Boolean result = redisTemplate.execute(initAvailableSavingsCard, list, num);
        return result;
    }

    public void sayhi() {

        System.out.println("done initAvailableSavingsCard=" + initAvailableSavingsCard + ", updateAvailableSavingsCard=" + updateAvailableSavingsCard);

    }

}

package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
@Slf4j
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /***
     *
     * 初始化商品缓存中的内存
     *
     * @param skuId 商品id
     * @param amount 库存数量
     */
    private void initSkuInventory(String skuId, Long expire, Integer amount) {
        addStock(skuId, expire, amount);
    }


    /**
     * 加库存
     *
     * @param skuId  商品id
     * @param expire 过期时间（秒）
     * @param num    库存数量
     * @return
     */
    public long addStock(String skuId, Long expire, int num) {
        // 库存ID
        String redisKey = "redis_key:stock:" + skuId;
        Assert.notNull(expire, "初始化库存失败，库存过期时间不能为null");
        //同一时间操作同个商品只能有一个操作
        RLock redisLock = redissonClient.getLock("inventory:" + skuId);
        try {
            if (redisLock.tryLock(10, TimeUnit.SECONDS)) {
                boolean hasKey = redisTemplate.hasKey(redisKey);
                // 判断key是否存在，存在就直接更新
                if (hasKey) {
                    return redisTemplate.opsForValue().increment(redisKey, num);
                } else {
                    // 初始化库存
                    redisTemplate.opsForValue().set(redisKey, num, expire, TimeUnit.SECONDS);

                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            redisLock.unlock();
        }
        return num;
    }


}

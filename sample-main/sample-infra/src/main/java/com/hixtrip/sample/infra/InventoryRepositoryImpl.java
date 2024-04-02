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


    /**
     * 减库存
     *
     * @param skuId  商品id
     * @param expire 过期时间（秒）
     * @param num    库存数量
     * @return
     */
    public boolean decStock(String skuId, Long expire, int num) {
        // 库存ID
        String redisKey = "redis_key:stock:" + skuId;
        Assert.notNull(expire, "初始化库存失败，库存过期时间不能为null");
        //同一时间操作同个商品只能有一个操作
        RLock redisLock = redissonClient.getLock("inventory:" + skuId);
        try {
            if (redisLock.tryLock(10, TimeUnit.SECONDS)) {
                boolean hasKey = redisTemplate.hasKey(redisKey);
                // 判断key是否存在和缓存中的库存是否大于0
                if (hasKey && (Integer) redisTemplate.opsForValue().get(redisKey) > 0) {
                    redisTemplate.opsForValue().decrement(redisKey, num);
                    return true;
                }
            } else {
                log.info("服务繁忙清稍后在试!");
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            redisLock.unlock();
        }
        return false;
    }

}

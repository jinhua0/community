package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @ClassName: RedisTest
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));

        /*redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().get(redisKey, "username");

        redisTemplate.opsForList().leftPush("hi", 1);
        redisTemplate.opsForList().rightPop("hi");

        redisTemplate.opsForSet().add("hi", 11,12,13);
        redisTemplate.opsForSet().pop("hi");

        redisTemplate.opsForZSet().add(redisKey, "haha", 82);
        redisTemplate.opsForZSet().popMax("haha");*/

        /*redisTemplate.expire(redisKey, 10, TimeUnit.MILLISECONDS);*/

    }

    @Test
    public void testBound() {
        String redisKey = "test:count";

        BoundValueOperations ops = redisTemplate.boundValueOps(redisKey);
        ops.increment();
        ops.increment();
        ops.increment();
        System.out.println(ops.get());
    }

    // 编程式事务
    @Test
    public void testTx() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";
                // 开启事务
                operations.multi();

                redisTemplate.opsForSet().add(redisKey);

                // 提交事务
                operations.exec();
                return operations;
            }
        });

        System.out.println(obj);
    }
}

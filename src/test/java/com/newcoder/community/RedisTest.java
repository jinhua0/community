package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


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

    // HyperLogLog, 统计 20W  重负数据的独立总数
    @Test
    public void testHyperLog() {
        String redisKey = "test:hll:01";

        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }

        for (int i = 1; i <= 100000; i++) {
            int r = (int) (Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, r);
        }

        // 统计数据
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    // 合并数据， 先合并 再统计数据的独立总数
    @Test
    public void testHyperHeBing() {
        String redisKey1 = "test:hll:02";

        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey1, i);
        }

        String redisKey2 = "test:hll:03";
        for (int i = 5001; i <= 15000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        }

        String redisKey3 = "test:hll:04";
        for (int i = 10001; i <= 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        }

        String redisKeyUnion = "test:hll:union";
        redisTemplate.opsForHyperLogLog().union(redisKeyUnion, redisKey1, redisKey2, redisKey3);

        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKeyUnion));
    }

    // 统计一组数据的Boolean值
    @Test
    public void testBitMap() {

        String redisKey = "test:bm:01";

        // 记录
        redisTemplate.opsForValue().setBit(redisKey, 1, true);

        // 查询
        redisTemplate.opsForValue().getBit(redisKey, 0);
        redisTemplate.opsForValue().getBit(redisKey, 1);

        // 统计
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(obj);
    }

    // 统计三组数据的布尔值， 三组数据做OR运算

}

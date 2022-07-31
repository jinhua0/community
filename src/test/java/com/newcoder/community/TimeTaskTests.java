package com.newcoder.community;

import com.newcoder.community.service.DataService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @Description:
 * @ClassName: TimeTaskTests
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TimeTaskTests {
    private static final Logger logger = LoggerFactory.getLogger(TimeTaskTests.class);

    // JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    //Spring 普通的线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;

    // Spring执行定时任务的线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private DataService dataService;

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1、JDK普通线程池
    @Test
    public void testExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ExecutorService");
            }
        };

        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }
        sleep(1000);
    }

    // JDK定时任务线程池
    @Test
    public void testScheduledExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ExecutorService");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task, 10000, 1000, TimeUnit.MILLISECONDS);

        sleep(20000);
    }

    // Spring 普通线程池
    @Test
    public void testThreadPool() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ThreadPoolTaskExecutor");
            }
        };

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(task);
        }
        sleep(1000);
    }

    // Spring 执行定时任务的线程池
    @Test
    public void testThreadPoolTaskScheduler() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ThreadPoolTaskScheduler");
            }
        };

        Date date = new Date(System.currentTimeMillis() + 1000);
        threadPoolTaskScheduler.scheduleAtFixedRate(task, date, 1000);

        sleep(3000);
    }

    // 5、Spring普通线程池简化
    @Test
    public void testThreadPoolSimple() {
        for (int i = 0; i < 10; i++) {
            dataService.execute1();
        }
    }

    // 6、Spring定时任务的线程池简化
    @Test
    public void testThreadPoolTaskSchedulerSimple() {
        sleep(20000);
    }


}

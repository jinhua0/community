package com.newcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @ClassName: ThreadPoolConfig
 * @author: jinhua
 */
@Configuration
@EnableScheduling
@EnableAsync
/* 启用定时任务 */
public class ThreadPoolConfig {
}

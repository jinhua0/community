package com.newcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Description:
 * @ClassName: WkConfig
 * @author: jinhua
 */
@Configuration
public class WkConfig {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(WkConfig.class);

    // 储存路径
    @Value("${wk.image.storage}")
    private String wkImageStorage;

    // 在初始化构造函数之后创建图片目录
    @PostConstruct
    public void init() {
        // 创建Wk图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()) {
            file.mkdir();
            logger.info("创建WK图片目录" + wkImageStorage);
        }
    }
}

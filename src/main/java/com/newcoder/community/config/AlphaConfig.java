package com.newcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @Description:
 * @ClassName: AlphaConfig
 * @author: jinhua
 */
@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yy-mm-dd HH:mm:ss");
    }

}

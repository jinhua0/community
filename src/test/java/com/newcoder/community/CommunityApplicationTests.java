package com.newcoder.community;

import com.newcoder.community.config.AlphaConfig;
import com.newcoder.community.dao.Alphago;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testApplicationContext(){
        Alphago alphago = applicationContext.getBean(Alphago.class);
        System.out.println(alphago.select());


        System.out.println(simpleDateFormat.format(new Date()));
    }
}

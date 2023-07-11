package com.newcoder.community;

import com.newcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Description:
 * @ClassName: MailTest
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired //注入模板引擎的Bean
    private TemplateEngine templateEngine;


    @Test
    public void MailTest(){
        mailClient.sendMail("er2578912608@163.com", "Test", "Welcome");
    }

    @Test
    public void templateTest(){
        Context context = new Context();
        context.setVariable("username", "Monday");
        String content = templateEngine.process("/mail/demo",context);

        System.out.println(content);
        mailClient.sendMail("er2578912608@163.com", "Thmeleaf", content);
    }


}


package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @Description:
 * @ClassName: SensitiveTests
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "你去赌博了，你去嫖娼了，你去开票了";

        System.out.println(sensitiveFilter.filter(text));

        String text2 = "你去α赌α博α了，你去α嫖α娼α了，你去α开α票α了";
        System.out.println(sensitiveFilter.filter(text2));
    }
}

package com.newcoder.community;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.MessageMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.*;
import com.newcoder.community.service.DiscussPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: MapperTests
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testMessage() {
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message message : messages){
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : messages1) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }
    

    @Test
    public void testSelectById(){
        Page page = new Page();
        int row = discussPostService.findDicussPostRows(0);
        page.setRows(row);
        System.out.println(page.getRows());
        System.out.println(page.getTotal());
    }

    @Test
    public void TestSelectById(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);

        User user1 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user1);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test2");
        user.setPassword("123");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://images.nowcoder.com/head/200t.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
    }

    @Test
    public void testUpdate(){
        int row = userMapper.updateStatus(150, 1);
        System.out.println(row);

        int row2 = userMapper.updateHeader(150, "http://images.nowcoder.com/head/100t.png");
        System.out.println(row2);

        int row3 = userMapper.updatePassword(150, "123");
        System.out.println(row3);

    }

    @Test
    public void testSelectPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for (DiscussPost post : discussPosts){
            System.out.println(post);
        }

        int row = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(row);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();

        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 *10));

        int row = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(row);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        int row = loginTicketMapper.updateStatus("abc", 1);
        LoginTicket loginTicket2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket2);
    }

    @Test
    public void testInsertDiscuss() {
        DiscussPost post = new DiscussPost();
        post.setUserId(999);
        post.setTitle("title");
        post.setContent("content");
        post.setCreateTime(new Date());
        int i = discussPostService.addDiscussPost(post);
        System.out.println(i);

    }

}


package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: HomeController
 * @author: jinhua
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostService discussPostService;

    //首页功能
    @RequestMapping(path = "index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        // SPring MVC 框架自动实例化 Model 和 Page , 并将配置注入Model
        // 所以在thymeleaf中可以直接访问Page 对象中的数据
        int row = discussPostService.findDicussPostRows(0);
        page.setRows(row);
        page.setPath("/index");
        List<DiscussPost> list = discussPostService.findDicussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list != null){
            for (DiscussPost post : list){
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    @RequestMapping(path = "error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }
}

package com.newcoder.community.controller;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Description:
 * @ClassName: LoginController
 * @author: jinhua
 */
@Controller
public class LoginController implements CommunityConstant {
    @Autowired
    private UserService userService;

    //获取提交页面
    @RequestMapping(path = "register", method = RequestMethod.GET)
    public String getRegister() {
        return "/site/register";
    }

    //获取登录页面
    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String getLogin() {
        return "/site/login";
    }

    // 在注册页面，post进行提交数据时
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "已经向你的邮箱发送了激活邮件");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    //激活处理
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model,
                             @PathVariable("userId") int userId,
                             @PathVariable("code") String code) {
        int result = userService.activation(userId, code);

        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功，你可以登录帐号了");
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "该账号已经被激活了");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，您提供的激活码不正确");
            model.addAttribute("target", "/index");
        }

        return "/site/operate-result";
    }
}

package com.newcoder.community.controller;

import com.newcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @ClassName: AlphaController
 * @author: jinhua
 */
@Controller
@RequestMapping("alpha")
public class AlphaController {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @RequestMapping("hello")
    @ResponseBody
    public String sayHello() {
        return "Hello World, SpringBoot.";
    }

    @RequestMapping("http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //请求数据
        //状态行
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        //请求头
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        //请求体，传的参数
        System.out.println(request.getParameter("code"));

        //返回响应数据
        //响应数据的格式 与 编码
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get请求

    // /students?current=1&limit=20
    @RequestMapping(path = "students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
    ) {
        System.out.println(current);
        System.out.println(limit);
        return "many students";
    }

    // /student/123
    @RequestMapping(path = "student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@PathVariable("id") int id) {
        System.out.println(id);
        return "One student";
    }

    //post
    @RequestMapping(path = "student", method = RequestMethod.POST)
    @ResponseBody
    public String getStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "zhangsan");
        modelAndView.addObject("age", "18");
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }

    //响应html数据第二种方式
    @RequestMapping(path = "school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", "18");
        return "/demo/view";
    }

    //响应JSON格式数据
    @RequestMapping(path = "emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", "zhangsan");
        map.put("age", "18");
        map.put("salary", 8000.00);
        return map;
    }

    // 产生Cookie
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());

        // 设置cookie 有效的路径
        cookie.setPath("/community/alpha");

        // 设置cookie 生命周期
        cookie.setMaxAge(60 * 10);

        response.addCookie(cookie);
        return "set cookie";
    }

    // 验证cookie
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }

    // 设置SessionId
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id", 1);
        session.setAttribute("name", "test");
        return "set session";
    }

    // 得到Session信息
    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    // 发送Ajax请求
    @RequestMapping(path = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public String getAjax(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功");
    }

    // 事务管理, 注解实现,声明式事务
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Object save() {

        return "OK";
    }

    // 编程式事务管理
    public Object save2() {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                return "ok";
            }
        });
    }
}
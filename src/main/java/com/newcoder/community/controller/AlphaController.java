package com.newcoder.community.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
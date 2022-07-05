package com.newcoder.community.controller.advice;

import com.newcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 统一处理异常
 * @ClassName: ExceptionAdvice
 * @author: jinhua
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    // 记录日志
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    // 统一处理异常
    @ExceptionHandler(Exception.class)
    public void handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器异常" + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()){
            logger.error(element.toString());
        }

        String xRequestedWith = request.getHeader("x-requested-with");
        // 如果是异步请求
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}

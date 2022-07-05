package com.newcoder.community.controller;

import com.newcoder.community.entity.Comment;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.CommentService;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @Description: 处理评论
 * @ClassName: CommentController
 * @author: jinhua
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        User user = hostHolder.getUser();
        comment.setUserId(user.getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }
}

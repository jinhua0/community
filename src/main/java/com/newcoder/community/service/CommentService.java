package com.newcoder.community.service;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description:
 * @ClassName: CommentService
 * @author: jinhua
 */
@Service
public class CommentService implements CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    // 查找实体对应所有的评论
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    // 评论数目
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    // 添加评论

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 评论不为空， 然后对敏感词进行处理
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));

        // 添加评论
        int row = commentMapper.insertComment(comment);

        // 更新帖子评论数量
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }

        return row;
    }
}

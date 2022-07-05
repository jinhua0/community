package com.newcoder.community.dao;

import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 根据实体挑 评论
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    // 返回数据的条目数
    int selectCountByEntity(int entityType, int entityId);

    // 增加评论
    int insertComment(Comment comment);
}

package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);

    // @ 取别名
    // 如果只有一个参数，并在<if>里面，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    // 插入帖子数据
    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    // 更新评论数量
    int updateCommentCount(int id, int commentCount);

    // 修改类型
    int updateType(int id, int type);

    // 修改状态
    int updateStatus(int id, int status);

    int updateScore(int id, double score);
}

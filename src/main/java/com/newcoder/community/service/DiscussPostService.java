package com.newcoder.community.service;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @ClassName: DiscussPostService
 * @author: jinhua
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDicussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDicussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}

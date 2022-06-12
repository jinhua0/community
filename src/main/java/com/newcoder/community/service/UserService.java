package com.newcoder.community.service;

import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @ClassName: UserService
 * @author: jinhua
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //通过Id查name
    public User findUserById(int id){
        return userMapper.selectById(id);
    }

}

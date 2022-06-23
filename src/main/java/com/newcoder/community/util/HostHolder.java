package com.newcoder.community.util;

import com.newcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Description: 持有用户信息，代替Session对象
 * @ClassName: HostHolder
 * @author: jinhua
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    // 存值
    public void setUser(User user){
        users.set(user);
    }

    // 取值
    public User getUser(){
        return users.get();
    }

    // 将map中的值清理
    public void clean(){
        users.remove();
    }
}

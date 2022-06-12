package com.newcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.Priority;

/**
 * @Description:
 * @ClassName: AlphagoImpl
 * @author: jinhua
 */
@Repository
@Primary
public class AlphagoImpl implements Alphago {
    @Override
    public String select() {
        return "Mybatis";
    }
}

package com.newcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Description:
 * @ClassName: CommunityUtil
 * @author: jinhua
 */
public class CommunityUtil {
    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    //对注册的明文密码进行加密
    //MD5加密，只能加密不能解密
    //hello + 随机字符串 -> 进行加密处理
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}

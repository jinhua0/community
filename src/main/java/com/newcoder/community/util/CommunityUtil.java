package com.newcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

/**
 * @Description: 生成随机字符串，密码加密的工具
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

    // 得到JSON 格式的字符串
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()){
                map.put(key, map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }
}

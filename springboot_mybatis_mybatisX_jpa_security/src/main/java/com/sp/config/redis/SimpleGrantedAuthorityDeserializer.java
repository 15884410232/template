package com.sp.config.redis;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Type;

/**
 * FastJson——自己实现SimpleGrantedAuthority类的反序列化方法。
 */
public class SimpleGrantedAuthorityDeserializer implements ObjectDeserializer {
    @Override
    public SimpleGrantedAuthority deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject jsonObject = parser.parseObject();
        String authority = Convert.toStr(jsonObject.get("authority"));
        return new SimpleGrantedAuthority(authority);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}

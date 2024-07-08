package com.sp.config.kaptcha;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码图片样式配置
 * @author chenqi
 * @date 2022/3/27
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public Producer KaptchaProducer() {
        Properties kaptchaProperties = new Properties();
        //无边框
        kaptchaProperties.put("kaptcha.border", "no");
        //验证码长度
        kaptchaProperties.put("kaptcha.textproducer.char.length","4");
        //图片高度
        kaptchaProperties.put("kaptcha.image.height","40");
        //图片宽度
        kaptchaProperties.put("kaptcha.image.width","100");
        //图片样式的实现类，默认WaterRipple（水纹），还有下面2种可选
        // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy    阴影com.google.code.kaptcha.impl.ShadowGimpy
        kaptchaProperties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        //背景颜色渐变开始色
        kaptchaProperties.put("kaptcha.background.clear.from","204,201,201");
        //北京颜色结束色
        kaptchaProperties.put("kaptcha.background.clear.to","204,201,201");
        //文件颜色
        kaptchaProperties.put("kaptcha.textproducer.font.color","orange");
        //文字大小
        kaptchaProperties.put("kaptcha.textproducer.font.size","32");
        //文字干扰实现类，默认DefaultNoise，还可以选择com.google.code.kaptcha.impl.NoNoise没有干扰线的实现类
        kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        //kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        //验证码内容可选项
        kaptchaProperties.put("kaptcha.textproducer.char.string","acdefhkmnprtwxy2345678");
        Config config = new Config(kaptchaProperties);
        return config.getProducerImpl();
    }
}

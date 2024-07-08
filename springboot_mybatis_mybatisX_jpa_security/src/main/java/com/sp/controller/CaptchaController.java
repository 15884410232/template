package com.sp.controller;

import com.google.code.kaptcha.Producer;
import com.sp.common.util.RedisUtil;
import com.sp.common.util.ResultUtil;
import com.sp.model.dto.response.CaptchaVo;
import com.sp.model.dto.response.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 获取验证码图片
 * @author chenqi
 * 2019.1.1
 */
@Slf4j
@RestController
public class CaptchaController {

    @Qualifier("KaptchaProducer")
    @Autowired
	private Producer captchaProducer;

    @Resource
    private RedisUtil redisUtil;

	/**生成登录的验证码*/
	@RequestMapping(value = "pass/captcha")
    public Result<CaptchaVo> handleRequest(HttpSession session, HttpServletResponse response) throws Exception {
		log.info("开始执行------------生成登录验证码");
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/png");
        CaptchaVo captchaVo = new CaptchaVo();
        //生成的验证码
        String capText = captchaProducer.createText();

        // 存储验证码到Redis
        String key = UUID.randomUUID().toString();
        redisUtil.set(key, capText, 60);
        captchaVo.setCaptchaId(key);

        //验证码相关的图片
        BufferedImage bi = captchaProducer.createImage(capText);

        byte[] pngs = convertBufferedImageToBytes(bi, "png");
        captchaVo.setCaptchaImage(pngs);

        log.info("登录验证码:{}",capText);
        return ResultUtil.success(captchaVo);




//        try {
//            out.flush();
//        } finally {
//            out.close();
//        }
    }

    public static byte[] convertBufferedImageToBytes(BufferedImage image, String formatName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, formatName, baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }
}

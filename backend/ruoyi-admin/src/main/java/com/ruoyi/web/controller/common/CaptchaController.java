package com.ruoyi.web.controller.common;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.mail.EmailUtil;
import com.ruoyi.common.utils.mail.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Producer;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.service.ISysConfigService;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 生成验证码
     */
    @PostMapping("/captchaImage")
    public AjaxResult getCode(@RequestBody RegisterBody user) {
        Integer code = ValidateCode.generateValidateCode(6);
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY;
        redisCache.setCacheObject(verifyKey, String.valueOf(code), 1, TimeUnit.MINUTES);
        emailUtil.sendSimpleMail(user.getUsername(), "Verify your email address", "验证码" + code + "用于QQ邮箱注册，1分钟内有效，请勿泄漏和转发。如非本人操作，请忽略");
        return AjaxResult.success();
    }
}

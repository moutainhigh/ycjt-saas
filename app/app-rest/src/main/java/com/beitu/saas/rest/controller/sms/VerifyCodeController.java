package com.beitu.saas.rest.controller.sms;

import com.beitu.saas.app.annotations.SignIgnore;
import com.beitu.saas.app.annotations.VisitorAccessible;
import com.beitu.saas.app.api.ApiResponse;
import com.beitu.saas.app.application.SendApplication;
import com.beitu.saas.common.config.ConfigUtil;
import com.beitu.saas.common.consts.RedisKeyConsts;
import com.beitu.saas.common.consts.TimeConsts;
import com.beitu.saas.rest.controller.sms.request.VerifyCodeSendRequest;
import com.beitu.saas.sms.enums.SmsErrorCodeEnum;
import com.beitu.saas.sms.enums.VerifyCodeTypeEnum;
import com.fqgj.base.services.redis.RedisClient;
import com.fqgj.common.api.annotations.ParamsValidate;
import com.fqgj.common.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SendApplication sendApplication;

    @Autowired
    private ConfigUtil configUtil;

    @VisitorAccessible
    @SignIgnore
    @ParamsValidate
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiOperation(value = "获取验证码", response = ApiResponse.class)
    public ApiResponse sendVerifyCode(@RequestBody @Valid VerifyCodeSendRequest req) {
        String mobile = req.getMobile();
        if (!redisClient.setnx(RedisKeyConsts.H5_LOGIN_VERIFYCODE_KEY, mobile, mobile)) {
            return new ApiResponse(SmsErrorCodeEnum.REPEAT_REQUEST);
        }
        redisClient.expire(RedisKeyConsts.H5_LOGIN_VERIFYCODE_KEY, TimeConsts.ONE_MINUTE, mobile);
        String verifyCode = RandomUtil.getVerifyCode(configUtil.getVerifyCodeLength());
        if (configUtil.isServerTest()) {
            verifyCode = configUtil.getVerifyCodeTestCode();
        }
        if (configUtil.getVerifyCodeReviewMobile().equals(mobile)) {
            verifyCode = configUtil.getVerifyCodeReviewCode();
        }
        VerifyCodeTypeEnum type = VerifyCodeTypeEnum.getEnumByName(req.getType());
        sendApplication.sendCodeAndNotifyMessage(mobile, verifyCode, type);
        redisClient.set(RedisKeyConsts.H5_SAVE_LOGIN_VERIFYCODE_KEY, verifyCode, TimeConsts.TWO_MINUTE, mobile);
        return new ApiResponse();
    }

}

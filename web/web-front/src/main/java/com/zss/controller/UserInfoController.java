package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.zss.entity.UserInfo;
import com.zss.result.Result;
import com.zss.result.ResultCodeEnum;
import com.zss.service.UserInfoService;
import com.zss.util.MD5;
import com.zss.vo.LoginVo;
import com.zss.vo.RegisterVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/23 10:00<br/>
 *
 * @author 16574<br />
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController<UserInfo>{

    @Reference
    private UserInfoService userInfoService;
    //去注册页面
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") Long phone, HttpServletRequest request) {
        //发送验证码
//        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
//        String phoneNumber = String.valueOf(phone);
//         SMSUtils2.sendShortMessage(phoneNumber,code);
        String code = "2222";
        //保存到会话域
        request.getSession().setAttribute("code",code);
        return Result.ok(code);
    }
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        //获取注册的信息
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String phone = registerVo.getPhone();
        String code = registerVo.getCode();
        //校验参数
        if (StringUtil.isEmpty(nickName) | StringUtil.isEmpty(password) | StringUtil.isEmpty(phone) | StringUtil.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //验证验证码
        String currentCode = (String) request.getSession().getAttribute("code");
        if (!code.equals(currentCode)){
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //验证手机号
           UserInfo userInfo   = userInfoService.getByPhone(phone);
        if (null != userInfo) {
                return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //注册到数据库
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setPhone(phone);
        userInfo1.setStatus(1);
        userInfoService.insert(userInfo1);
        return Result.ok();
    }

    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //判断参数
        if(StringUtil.isEmpty(phone) || StringUtil.isEmpty(password) ) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //判断账号
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if( null == userInfo){
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断密码
        if (!MD5.encrypt(password).equals(userInfo.getPassword())){
            return  Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //校验是否被禁用
        if (userInfo.getStatus() == 0){
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        request.getSession().setAttribute("user",userInfo);
        Map<String,Object> map = new HashMap<>();
        map.put("phone",userInfo.getPhone());
        map.put("nickName",userInfo.getNickName());
        return Result.ok(map);
    }
    //登出
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return Result.ok();
    }

}

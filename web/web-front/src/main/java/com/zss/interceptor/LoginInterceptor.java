package com.zss.interceptor;

import com.zss.entity.UserInfo;
import com.zss.result.Result;
import com.zss.result.ResultCodeEnum;
import com.zss.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/24 10:14<br/>
 *
 * @author 16574<br />
 */

@SuppressWarnings({"unchecked", "rawtypes"})
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (null == userInfo) {
            Result result = Result.build("没有登录", ResultCodeEnum.LOGIN_AUTH);
            WebUtil.writeJSON(response, result);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

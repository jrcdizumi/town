package com.goodtown.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodtown.utils.JwtHelper;
import com.goodtown.utils.Result;
import com.goodtown.utils.ResultCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    // 添加静态的ThreadLocal变量
    private static ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();
    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handler) throws Exception {

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)){
            Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().print(json);
            //拦截
            return false;
        }else{
            // 从token中获取用户信息，存储到ThreadLocal
            Long userId = jwtHelper.getUserId(token);
            userThreadLocal.set(userId);
            //放行
            return true;
        }
    }

    // 请求处理完成后清理ThreadLocal，防止内存泄漏
    @Override
    public void afterCompletion(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handler, @SuppressWarnings("null") Exception ex) throws Exception {
        userThreadLocal.remove();
    }

    // 提供获取用户信息的方法
    public static Long getUserId() {
        return userThreadLocal.get();
    }
}

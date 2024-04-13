package top.galaxyrockets.cslabmanagementplatform.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import cn.hutool.core.util.StrUtil;

import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.common.AuthAccess;
import top.galaxyrockets.cslabmanagementplatform.exception.ServiceException;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) 
        throws Exception {

        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }

        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }

        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            throw new ServiceException("401", "请登录");
        }

        User user = userMapper.selectById(Integer.valueOf(userId));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new ServiceException("401", "请登录");
        }

        return true;
    }

}

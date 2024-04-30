package top.galaxyrockets.cslabmanagementplatform.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.date.DateUtil;

import jakarta.annotation.Resource;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import top.galaxyrockets.cslabmanagementplatform.domain.po.User;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public class TokenUtil {

    private static UserMapper staticUserMapper;

    @Resource
    private UserMapper userMapper;

    @PostConstruct
    public void setUserMapper() {
        staticUserMapper = userMapper;
    }
    
    public static String generateToken(String userId, String password) {
        return JWT.create().withAudience(userId)
                  .withExpiresAt(DateUtil.offsetHour(new Date(), 1))
                  .sign(Algorithm.HMAC256(password));
    }

    public static User getCurrentUser() {
        try {
            @SuppressWarnings("null")
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}

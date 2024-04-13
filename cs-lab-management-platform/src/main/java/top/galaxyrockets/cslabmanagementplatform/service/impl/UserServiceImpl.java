package top.galaxyrockets.cslabmanagementplatform.service.impl;

import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.exception.ServiceException;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.service.IUserService;
import top.galaxyrockets.cslabmanagementplatform.util.TokenUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(User user) {
        User userDb = getByUsername(user.getUsername());
        
        if (userDb == null) {
            throw new ServiceException("账号或密码错误");
        }

        if (!userDb.getPassword().equals(user.getPassword())) {
            throw new ServiceException("账号或密码错误");
        }

        String token = TokenUtil.generateToken(userDb.getUserId().toString(), userDb.getUsername());
        userDb.setToken(token);

        return userDb;
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

}

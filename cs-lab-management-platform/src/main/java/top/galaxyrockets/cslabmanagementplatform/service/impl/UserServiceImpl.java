package top.galaxyrockets.cslabmanagementplatform.service.impl;

import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.exception.ServiceException;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.service.IUserService;
import top.galaxyrockets.cslabmanagementplatform.util.TokenUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;

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

    @Override
    public Page<User> page(Integer current, Integer size, User user) {
        var wrapper = new LambdaQueryWrapper<User>();
        wrapper.orderByDesc(User::getUserId)
               .like(StrUtil.isNotBlank(user.getRole()), User::getRole, user.getRole())
               .like(StrUtil.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
               .like(StrUtil.isNotBlank(user.getFullName()), User::getFullName, user.getFullName())
               .like(StrUtil.isNotBlank(user.getTitle()), User::getTitle, user.getTitle())
               .like(StrUtil.isNotBlank(user.getMajor()), User::getMajor, user.getMajor())
               .like(StrUtil.isNotBlank(user.getStudentClass()), User::getStudentClass, user.getStudentClass());
        return page(new Page<>(current, size), wrapper);
    }

}
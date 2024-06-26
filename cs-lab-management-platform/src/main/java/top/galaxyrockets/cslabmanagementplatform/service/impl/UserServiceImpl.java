package top.galaxyrockets.cslabmanagementplatform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.galaxyrockets.cslabmanagementplatform.util.TokenUtil;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.service.IUserService;
import top.galaxyrockets.cslabmanagementplatform.domain.po.User;
import top.galaxyrockets.cslabmanagementplatform.exception.ServiceException;

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

        String token = TokenUtil.generateToken(userDb.getUserId().toString(), userDb.getPassword());
        userDb.setToken(token);

        return userDb;
    }

    @Override
    public User getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));
    }

    @Override
    public Page<User> page(Integer current, Integer size, User user) {
        var wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.orderByDesc(User::getUserId)
               .eq(StrUtil.isNotBlank(user.getRole()), User::getRole, user.getRole())
               .like(StrUtil.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
               .like(StrUtil.isNotBlank(user.getFullName()), User::getFullName, user.getFullName())
               .like(StrUtil.isNotBlank(user.getTitle()), User::getTitle, user.getTitle());
        return page(Page.of(current, size), wrapper);
    }   

    @Override
    public Page<User> pageStudents(Integer current, Integer size, User user) {
        var wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.orderByDesc(User::getUserId)
               .eq(StrUtil.isNotBlank(user.getRole()), User::getRole, user.getRole())
               .like(StrUtil.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
               .like(StrUtil.isNotBlank(user.getFullName()), User::getFullName, user.getFullName())
               .like(StrUtil.isNotBlank(user.getMajor()), User::getMajor, user.getMajor())
               .like(StrUtil.isNotBlank(user.getStudentClass()), User::getStudentClass, user.getStudentClass());
        return page(Page.of(current, size), wrapper);
    }

    @Override
    public boolean updateById(User user) {
        var wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.eq(User::getUsername, user.getUsername());
        User userDb = getOne(wrapper);
        if (userDb != null && !userDb.getUserId().equals(user.getUserId())) {
            throw new ServiceException("相同的工号或学号已存在");
        }
        return super.updateById(user);
     }

    @Override
    public boolean save(User user) {
        var wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.eq(User::getUsername, user.getUsername());
        if (getOne(wrapper) != null) {
            throw new ServiceException("相同的工号或学号已存在");
        }
        return super.save(user);
    }

    @Override
    public List<User> listTechnicians() {
        var wrapper = Wrappers.lambdaQuery(User.class).eq(User::getRole, "实验员");
        return list(wrapper);
    }

}

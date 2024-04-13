package top.galaxyrockets.cslabmanagementplatform.service;

import top.galaxyrockets.cslabmanagementplatform.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface IUserService extends IService<User> {

    /**
     * Login the user and return the user if login successfully, otherwise null.
     * @param user the user to login
     * @return the user if login successfully, otherwise null
     */
    public User login(User user);   

    /**
     * Get user by username.
     * @param username the username of the user
     * @return the user with the given username, or null if not found
     */
    public User getByUsername(String username);

}

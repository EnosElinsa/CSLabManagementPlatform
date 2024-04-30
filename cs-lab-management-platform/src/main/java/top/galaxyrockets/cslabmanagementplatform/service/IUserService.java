package top.galaxyrockets.cslabmanagementplatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.domain.po.User;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface IUserService extends IService<User> {

    /**
     * Page query users in role of technician or teacher. 
     * @param current current page
     * @param size page size
     * @param user the user to query
     * @return page of technicians or teachers
     */
    public Page<User> page(Integer current, Integer size, User user);

    /**
     * Page query users in role of student.
     * @param current current page
     * @param size page size
     * @param user the user to query
     * @return page of students
     */
    public Page<User> pageStudents(Integer current, Integer size, User user);

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

    /**
     * List all technicians.
     * @return a list of all technicians
     */
    public List<User> listTechnicians();
    
}

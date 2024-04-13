package top.galaxyrockets.cslabmanagementplatform.controller;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.common.Result;
import top.galaxyrockets.cslabmanagementplatform.common.AuthAccess;
import top.galaxyrockets.cslabmanagementplatform.service.IUserService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User userDb = userService.login(user);

        return Result.success(userDb);
    }

}


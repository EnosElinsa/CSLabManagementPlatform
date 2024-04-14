package top.galaxyrockets.cslabmanagementplatform.controller;

import java.util.List;
import java.io.IOException;

import jakarta.annotation.Resource;

import cn.hutool.poi.excel.ExcelUtil;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/page")
    public Result page(@RequestParam Integer current,
                       @RequestParam Integer size,
                        User user) {
        return Result.success(userService.page(current, size, user));
    } 

    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        if (userService.save(user)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{userId}")
    public Result removeById(@PathVariable Integer userId) {
        if (userService.removeById(userId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> userIds) {
        if (userService.removeBatchByIds(userIds)) {
            return Result.success();
        }

        return Result.error();
    }

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User userDb = userService.login(user);

        return Result.success(userDb);
    }

    @PostMapping("/import")
    public Result importByExcel(MultipartFile file) throws IOException {
        var reader = ExcelUtil.getReader(file.getInputStream());
        reader.setIgnoreEmptyRow(true);
        var userList = reader.readAll(User.class);
        reader.close();

        boolean result = true;
        try {
            result = userService.saveBatch(userList);
        } catch (Exception e) {
            return Result.error("导入失败，请检查数据格式是否正确");
        }

        if (result) {
            return Result.success();
        }
        return Result.error("导入失败，请检查数据格式是否正确");
    }

}


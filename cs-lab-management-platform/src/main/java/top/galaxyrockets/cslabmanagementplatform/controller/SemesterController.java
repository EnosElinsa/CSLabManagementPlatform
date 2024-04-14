package top.galaxyrockets.cslabmanagementplatform.controller;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.galaxyrockets.cslabmanagementplatform.common.Result;
import top.galaxyrockets.cslabmanagementplatform.entity.Semester;
import top.galaxyrockets.cslabmanagementplatform.service.ISemesterService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/semester")
public class SemesterController {

    @Resource
    private ISemesterService semesterService;

    @PostMapping("/save")
    public Result save(@RequestBody Semester semester) {
        if (semesterService.save(semester)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Semester semester) {
        if (semesterService.updateById(semester)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{semesterId}")
    public Result removeById(@PathVariable Integer semesterId) {
        if (semesterService.removeById(semesterId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> semesterIds) {
        if (semesterService.removeBatchByIds(semesterIds)) {
            return Result.success();
        }

        return Result.error();
    }

}

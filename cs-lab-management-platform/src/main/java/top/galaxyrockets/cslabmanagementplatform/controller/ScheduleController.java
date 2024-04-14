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
import top.galaxyrockets.cslabmanagementplatform.entity.Schedule;
import top.galaxyrockets.cslabmanagementplatform.service.IScheduleService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Resource
    private IScheduleService scheduleService;

    @PostMapping("/save")
    public Result save(@RequestBody Schedule schedule) {
        if (scheduleService.save(schedule)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Schedule schedule) {
        if (scheduleService.updateById(schedule)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{scheduleId}")
    public Result removeById(@PathVariable Integer scheduleId) {
        if (scheduleService.removeById(scheduleId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> scheduleIds) {
        if (scheduleService.removeBatchByIds(scheduleIds)) {
            return Result.success();
        }

        return Result.error();
    }

}

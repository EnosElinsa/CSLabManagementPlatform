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
import top.galaxyrockets.cslabmanagementplatform.entity.LabScheduleRequest;
import top.galaxyrockets.cslabmanagementplatform.service.ILabScheduleRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/labScheduleRequest")
public class LabScheduleRequestController {

    @Resource
    private ILabScheduleRequestService labScheduleRequestService;

    @PostMapping("/save")
    public Result save(@RequestBody LabScheduleRequest request) {
        if (labScheduleRequestService.save(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody LabScheduleRequest request) {
        if (labScheduleRequestService.updateById(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{requestId}")
    public Result removeById(@PathVariable Integer requestId) {
        if (labScheduleRequestService.removeById(requestId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> requestIds) {
        if (labScheduleRequestService.removeBatchByIds(requestIds)) {
            return Result.success();
        }

        return Result.error();
    }

}


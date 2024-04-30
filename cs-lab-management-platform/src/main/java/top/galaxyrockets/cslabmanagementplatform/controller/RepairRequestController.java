package top.galaxyrockets.cslabmanagementplatform.controller;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.galaxyrockets.cslabmanagementplatform.common.Result;
import top.galaxyrockets.cslabmanagementplatform.domain.po.RepairRequest;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.RepairRequestVo;
import top.galaxyrockets.cslabmanagementplatform.service.IRepairRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/repairRequest")
public class RepairRequestController {

    @Resource
    private IRepairRequestService repairRequestService;

    @GetMapping("/page")
    public Result page(@RequestParam Integer current,
                       @RequestParam Integer size,
                        RepairRequestVo request) {
        return Result.success(repairRequestService.page(current, size, request));
    }

    @GetMapping("/pageByTechnicianId")
    public Result page(@RequestParam Integer current,
                       @RequestParam Integer size,
                       @RequestParam Integer technicianId,
                        RepairRequestVo request) {
        return Result.success(repairRequestService.pageByTechnicianId(current, size, request, technicianId));
    }

    @PostMapping("/save")
    public Result save(@RequestBody RepairRequest request) {
        if (repairRequestService.save(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody RepairRequest request) {
        if (repairRequestService.updateById(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{requestId}")
    public Result removeById(@PathVariable Integer requestId) {
        if (repairRequestService.removeById(requestId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> requestIds) {
        if (repairRequestService.removeBatchByIds(requestIds)) {
            return Result.success();
        }

        return Result.error();
    }
}


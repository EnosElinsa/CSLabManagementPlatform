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
import top.galaxyrockets.cslabmanagementplatform.vo.LabBorrowRequestVo;
import top.galaxyrockets.cslabmanagementplatform.entity.LabBorrowRequest;
import top.galaxyrockets.cslabmanagementplatform.service.ILabBorrowRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/labBorrowRequest")
public class LabBorrowRequestController {

    @Resource
    private ILabBorrowRequestService labBorrowRequestService;

    @GetMapping("/page")
    public Result page(@RequestParam Integer current,
                       @RequestParam Integer size,
                        LabBorrowRequestVo request) {
        return Result.success(labBorrowRequestService.page(current, size, request));
    }

    @PostMapping("/save")
    public Result save(@RequestBody LabBorrowRequest request) {
        if (labBorrowRequestService.save(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody LabBorrowRequest request) {
        if (labBorrowRequestService.updateById(request)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{requestId}")
    public Result removeById(@PathVariable Integer requestId) {
        if (labBorrowRequestService.removeById(requestId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Integer> requestIds) {
        if (labBorrowRequestService.removeBatchByIds(requestIds)) {
            return Result.success();
        }

        return Result.error();
    }
    
}


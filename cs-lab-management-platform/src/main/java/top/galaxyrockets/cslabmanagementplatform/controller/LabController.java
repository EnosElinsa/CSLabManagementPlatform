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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import top.galaxyrockets.cslabmanagementplatform.vo.LabVo;
import top.galaxyrockets.cslabmanagementplatform.entity.Lab;
import top.galaxyrockets.cslabmanagementplatform.common.Result;
import top.galaxyrockets.cslabmanagementplatform.service.ILabService;


/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@RestController
@RequestMapping("/lab")
public class LabController {

    @Resource
    private ILabService labService;

    @GetMapping("/page")
    public Result page(@RequestParam Integer current,
                       @RequestParam Integer size,
                        LabVo labVo) {
        return Result.success(labService.page(current, size, labVo));
    }
    
    @GetMapping("/list")
    public Result list() {
        return Result.success(labService.list());
    }

    @GetMapping("/listByCategory/{category}")
    public Result listByCategory(@PathVariable String category) {
        var wrapper = Wrappers.lambdaQuery(Lab.class).eq(Lab::getCategory, category);
        return Result.success(labService.list(wrapper));
    }

    @PostMapping("/save")
    public Result save(@RequestBody Lab lab) {
        if (labService.save(lab)) {
            return Result.success();
        }

        return Result.error();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Lab lab) {
        if (labService.updateById(lab)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeById/{labId}")
    public Result removeById(@PathVariable String labId) {
        if (labService.removeById(labId)) {
            return Result.success();
        }

        return Result.error();
    }

    @DeleteMapping("/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<String> labIds) {
        if (labService.removeBatchByIds(labIds)) {
            return Result.success();
        }

        return Result.error();
    }

}


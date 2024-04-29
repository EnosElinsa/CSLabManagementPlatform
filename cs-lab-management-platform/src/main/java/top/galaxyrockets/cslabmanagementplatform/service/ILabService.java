package top.galaxyrockets.cslabmanagementplatform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.vo.LabVo;
import top.galaxyrockets.cslabmanagementplatform.entity.Lab;


/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ILabService extends IService<Lab> {

    /**
     * Page query of labs
     * @param current current page
     * @param size page size
     * @param labVo query conditions
     * @return page of labs
     */
    IPage<LabVo> page(Integer current, Integer size, LabVo labVo);

}

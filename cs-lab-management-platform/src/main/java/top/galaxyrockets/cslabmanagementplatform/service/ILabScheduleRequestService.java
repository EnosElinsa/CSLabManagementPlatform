package top.galaxyrockets.cslabmanagementplatform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.domain.po.LabScheduleRequest;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.LabScheduleRequestVo;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ILabScheduleRequestService extends IService<LabScheduleRequest> {

    /**
     * Page query of lab schedule request.
     * @param current current page
     * @param size page size
     * @param labScheduleRequestVo query conditions
     * @return page of lab schedule request
     */
    public IPage<LabScheduleRequestVo> page(Integer current, Integer size, LabScheduleRequestVo labScheduleRequestVo);

}

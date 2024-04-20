package top.galaxyrockets.cslabmanagementplatform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.vo.RepairRequestVo;
import top.galaxyrockets.cslabmanagementplatform.entity.RepairRequest;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface IRepairRequestService extends IService<RepairRequest> {

    /**
     * Page query of repair request.
     * @param current current page
     * @param size page size
     * @param repairRequestVo query conditions
     * @return page of repair request
     */
    public IPage<RepairRequestVo> page(Integer current, Integer size, RepairRequestVo repairRequestVo);

    /**
     * Page query of repair request by technician id.
     * @param current current page
     * @param size page size
     * @param request query conditions
     * @param technicianId technician id
     * @return page of repair request
     */
    public IPage<RepairRequestVo> pageByTechnicianId(Integer current, Integer size, RepairRequestVo request, Integer technicianId);

}

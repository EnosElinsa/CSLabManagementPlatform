package top.galaxyrockets.cslabmanagementplatform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.domain.po.LabBorrowRequest;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.LabBorrowRequestVo;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ILabBorrowRequestService extends IService<LabBorrowRequest> {

    /**
     * Page query of lab borrow request.
     * @param current current page
     * @param size page size
     * @param labBorrowRequestVo query conditions
     * @return page of lab borrow request
     */
    public IPage<LabBorrowRequestVo> page(Integer current, Integer size, LabBorrowRequestVo labBorrowRequestVo);

}

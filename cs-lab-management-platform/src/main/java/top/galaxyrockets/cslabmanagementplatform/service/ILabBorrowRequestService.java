package top.galaxyrockets.cslabmanagementplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.entity.LabBorrowRequest;
/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ILabBorrowRequestService extends IService<LabBorrowRequest> {

    /**
     * Page query of lab borrow requests
     * @param current current page
     * @param size page size
     * @param request request condition
     * @return page of lab borrow requests
     */
    // public Page<LabBorrowRequest> page(Integer current, Integer size, LabBorrowRequest request);

}

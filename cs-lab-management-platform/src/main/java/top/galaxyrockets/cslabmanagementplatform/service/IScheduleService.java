package top.galaxyrockets.cslabmanagementplatform.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.galaxyrockets.cslabmanagementplatform.domain.po.Schedule;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.ScheduleVo;


/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface IScheduleService extends IService<Schedule> {

    /**
     * Page query of schedule.
     * @param current current page
     * @param size page size
     * @param scheduleVo query conditions
     * @return page of schedule
     */
    public IPage<ScheduleVo> page(Integer current, Integer size, ScheduleVo scheduleVo);

    /**
     * List all schedules by semester id.
     * @param semesterId semester id
     * @return list of schedule
     */
    public List<ScheduleVo> listBySemesterId(Integer semesterId);

}

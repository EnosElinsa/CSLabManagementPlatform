package top.galaxyrockets.cslabmanagementplatform.service;

import top.galaxyrockets.cslabmanagementplatform.entity.Semester;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ISemesterService extends IService<Semester> {

    public Page<Semester> page(Integer current, Integer size, Semester semester);

    public Semester getCurrentSemester();

    public boolean setCurrentSemester(Integer semesterId);
    
}

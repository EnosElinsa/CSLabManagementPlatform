package top.galaxyrockets.cslabmanagementplatform.service;

import top.galaxyrockets.cslabmanagementplatform.entity.Semester;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public interface ISemesterService extends IService<Semester> {

    /**
     * Page query of semester list.
     * @param current current page
     * @param size page size
     * @param semester the query conditions
     * @return the page of semester list
     */
    public Page<Semester> page(Integer current, Integer size, Semester semester);

    /**
     * Get the current semester, that is whose field of isCurrentSemester is true.
     * @return the current semester
     */
    public Semester getCurrentSemester();

    /**
     * Set the current semester by semesterId, that is set the field of isCurrentSemester to true.
     * @param semesterId  the id of the semester to be set as current
     * @return true if the semester is set successfully, otherwise false
     */
    public boolean setCurrentSemester(Integer semesterId);
    
}

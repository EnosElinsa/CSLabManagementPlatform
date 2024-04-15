package top.galaxyrockets.cslabmanagementplatform.service.impl;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import top.galaxyrockets.cslabmanagementplatform.entity.Semester;
import top.galaxyrockets.cslabmanagementplatform.mapper.SemesterMapper;
import top.galaxyrockets.cslabmanagementplatform.service.ISemesterService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements ISemesterService {

    @Override
    public Page<Semester> page(Integer current, Integer size, Semester semester) {
        var wrapper = new LambdaQueryWrapper<Semester>();
        wrapper.orderByDesc(Semester::getSemesterId)
               .like(StrUtil.isNotBlank(semester.getSemester()), Semester::getSemester, semester.getSemester());
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public Semester getCurrentSemester() {
        var wrapper = new LambdaQueryWrapper<Semester>();
        wrapper.eq(Semester::getIsCurrentSemester, true);
        return getOne(wrapper);
    }

    @Override
    public boolean setCurrentSemester(Integer semesterId) {
        var semester = getById(semesterId);
        if (semester == null) {
            return false;
        }
        var wrapper = new LambdaQueryWrapper<Semester>();
        wrapper.eq(Semester::getIsCurrentSemester, true);
        var currentSemester = getOne(wrapper);
        if (currentSemester == null) {
            return false;
        }
        currentSemester.setIsCurrentSemester(false);
        semester.setIsCurrentSemester(true);
        return updateById(semester) && updateById(currentSemester);
    }

}

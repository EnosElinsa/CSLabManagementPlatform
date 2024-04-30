package top.galaxyrockets.cslabmanagementplatform.service.impl;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.galaxyrockets.cslabmanagementplatform.domain.po.Semester;
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
        var wrapper = Wrappers.lambdaQuery(Semester.class);
        wrapper.orderByDesc(Semester::getSemesterId)
               .like(StrUtil.isNotBlank(semester.getSemester()), Semester::getSemester, semester.getSemester());
        return page(Page.of(current, size), wrapper);
    }

    @Override
    public Semester getCurrentSemester() {
        var wrapper = Wrappers.lambdaQuery(Semester.class).eq(Semester::getIsCurrentSemester, true);
        return getOne(wrapper);
    }

    @Override
    public boolean setCurrentSemester(Integer semesterId) {
        var semester = getById(semesterId);
        if (semester == null) {
            return false;
        }
        var wrapper = Wrappers.lambdaQuery(Semester.class).eq(Semester::getIsCurrentSemester, true);
        var currentSemester = getOne(wrapper);
        if (currentSemester == null) {
            return false;
        }
        currentSemester.setIsCurrentSemester(false);
        semester.setIsCurrentSemester(true);
        return updateById(semester) && updateById(currentSemester);
    }

}

package top.galaxyrockets.cslabmanagementplatform.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import top.galaxyrockets.cslabmanagementplatform.domain.po.Lab;
import top.galaxyrockets.cslabmanagementplatform.domain.po.Schedule;
import top.galaxyrockets.cslabmanagementplatform.domain.po.Semester;
import top.galaxyrockets.cslabmanagementplatform.domain.po.User;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.ScheduleVo;
import top.galaxyrockets.cslabmanagementplatform.mapper.ScheduleMapper;
import top.galaxyrockets.cslabmanagementplatform.service.IScheduleService;
import top.galaxyrockets.cslabmanagementplatform.exception.ServiceException;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

    @Override
    public IPage<ScheduleVo> page(Integer current, Integer size, ScheduleVo scheduleVo) {
        var wrapper = Wrappers.lambdaQuery(Schedule.class)
                              .orderByDesc(Schedule::getScheduleId)
                              .like(StrUtil.isNotBlank(scheduleVo.getLabId()), Schedule::getLabId, 
                                scheduleVo.getLabId())
                              .like(StrUtil.isNotBlank(scheduleVo.getCourseName()), Schedule::getCourseName, 
                                scheduleVo.getCourseName())
                              .like(StrUtil.isNotBlank(scheduleVo.getStudentClass()), Schedule::getStudentClass, 
                                scheduleVo.getStudentClass());
        IPage<Schedule> schedulePage = page(Page.of(current, size), wrapper);
        IPage<ScheduleVo> scheduleVoPage = schedulePage.convert(ScheduleVo::new);
        
        if (scheduleVoPage.getRecords().size() > 0) {
            addInfo(scheduleVoPage);
        }

        return scheduleVoPage;
    }

    private void addInfo(IPage<ScheduleVo> scheduleVoPage) {
        var semesterIds = scheduleVoPage.getRecords()
                                        .stream()
                                        .map(ScheduleVo::getSemesterId)
                                        .collect(Collectors.toSet());
        var semesters = Db.lambdaQuery(Semester.class).in(Semester::getSemesterId, semesterIds).list();
        var semesterMap = semesters.stream()
                            .collect(Collectors.toMap(Semester::getSemesterId, Semester::getSemester));

        var teacherIds = scheduleVoPage.getRecords()
                                      .stream()
                                      .map(ScheduleVo::getTeacherId)
                                      .collect(Collectors.toSet());
        var teachers = Db.lambdaQuery(User.class).in(User::getUserId, teacherIds).list();
        var fullNameMap = teachers.stream()
                                  .collect(Collectors.toMap(User::getUserId, User::getFullName));

        var labIds = scheduleVoPage.getRecords()
                                   .stream()
                                   .map(ScheduleVo::getLabId)
                                   .collect(Collectors.toSet());
        var labs = Db.lambdaQuery(Lab.class).in(Lab::getLabId, labIds).list();
        var labNameMap = labs.stream()
                         .collect(Collectors.toMap(Lab::getLabId, Lab::getName));

        scheduleVoPage.getRecords().forEach(vo -> {
            vo.setSemester(semesterMap.get(vo.getSemesterId()));
            vo.setFullName(fullNameMap.get(vo.getTeacherId()));
            vo.setName(labNameMap.get(vo.getLabId()));
        });
    }

    @Override
    public boolean save(Schedule schedule) {
        if (isConflicted(schedule)) {
            return false;
        }
        return super.save(schedule);
    }   

    private boolean isConflicted(Schedule schedule) {
        var wrapper = Wrappers.lambdaQuery(Schedule.class)
                              .eq(Schedule::getLabId, schedule.getLabId())
                              .eq(Schedule::getSemesterId, schedule.getSemesterId())
                              .eq(Schedule::getSession, schedule.getSession())
                              .eq(Schedule::getDay, schedule.getDay());
        var sameLabSchedules = list(wrapper);
        if (sameLabSchedules.isEmpty()) {
            return false;
        }

        for (var sameLabSchedule : sameLabSchedules) {
            if (isWeeksOverlap(sameLabSchedule, schedule)) {
                throw new ServiceException("排课冲突，请检查课表是否有冲突。冲突的课程: " + sameLabSchedule.getCourseName() + ", " + 
                  sameLabSchedule.getStudentClass() + ", 第" + sameLabSchedule.getStartWeek() + "-" + sameLabSchedule.getEndWeek() + "周, "
                  + sameLabSchedule.getDay() + ", " + "第" + sameLabSchedule.getSession() + "节");
            }
        }

        return true;
    }

    private boolean isWeeksOverlap(Schedule a, Schedule b) {
        if (a.getEndWeek() < b.getStartWeek() || a.getStartWeek() > b.getEndWeek()) {
            return false;
        }
        return true;
    }

    @Override
    public List<ScheduleVo> listBySemesterId(Integer semesterId) {
        var wrapper = Wrappers.lambdaQuery(Schedule.class)
                              .eq(Schedule::getSemesterId, semesterId);
        var schedules = list(wrapper);
        var scheduleVos = schedules.stream().map(ScheduleVo::new).collect(Collectors.toList());

        if (scheduleVos.size() > 0) {
            addInfo(scheduleVos);
        }

        return scheduleVos;
    }

    private void addInfo(List<ScheduleVo> scheduleVos) {
        var semesterIds = scheduleVos.stream()
                                     .map(ScheduleVo::getSemesterId)
                                     .collect(Collectors.toSet());

        var semesters = Db.lambdaQuery(Semester.class).in(Semester::getSemesterId, semesterIds).list();
        var semesterMap = semesters.stream()
                                   .collect(Collectors.toMap(Semester::getSemesterId, Semester::getSemester));

        var teacherIds = scheduleVos.stream()
                                    .map(ScheduleVo::getTeacherId)
                                    .collect(Collectors.toSet());    
                                    
        var teachers = Db.lambdaQuery(User.class).in(User::getUserId, teacherIds).list();
        var fullNameMap = teachers.stream()
                                  .collect(Collectors.toMap(User::getUserId, User::getFullName));

        var labIds = scheduleVos.stream()
                                .map(ScheduleVo::getLabId)
                                .collect(Collectors.toSet());

        var labs = Db.lambdaQuery(Lab.class).in(Lab::getLabId, labIds).list();
        var labNameMap = labs.stream()
                             .collect(Collectors.toMap(Lab::getLabId, Lab::getName));

        scheduleVos.forEach(vo -> {
            vo.setSemester(semesterMap.get(vo.getSemesterId()));
            vo.setFullName(fullNameMap.get(vo.getTeacherId()));
            vo.setName(labNameMap.get(vo.getLabId()));
        });
    }

}

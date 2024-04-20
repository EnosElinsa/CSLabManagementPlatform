package top.galaxyrockets.cslabmanagementplatform.service.impl;

import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;

import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.entity.Semester;
import top.galaxyrockets.cslabmanagementplatform.entity.LabScheduleRequest;
import top.galaxyrockets.cslabmanagementplatform.vo.LabScheduleRequestVo;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.SemesterMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.LabScheduleRequestMapper;
import top.galaxyrockets.cslabmanagementplatform.service.ILabScheduleRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class LabScheduleRequestServiceImpl extends ServiceImpl<LabScheduleRequestMapper, LabScheduleRequest> implements ILabScheduleRequestService {

    @Resource
    private SemesterMapper semesterMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public IPage<LabScheduleRequestVo> page(Integer current, Integer size, LabScheduleRequestVo request) {
        var wrapper = Wrappers.lambdaQuery(LabScheduleRequest.class)
                              .orderByDesc(LabScheduleRequest::getRequestId)
                              .eq(request.getTeacherId() != null, LabScheduleRequest::getTeacherId, request.getTeacherId())
                              .like(StrUtil.isNotBlank(request.getCourseName()), LabScheduleRequest::getCourseName, 
                                request.getCourseName())
                              .like(StrUtil.isNotBlank(request.getLabCategory()), LabScheduleRequest::getLabCategory, 
                                request.getLabCategory())
                              .like(StrUtil.isNotBlank(request.getStudentClass()), LabScheduleRequest::getStudentClass, 
                                request.getStudentClass());
        IPage<LabScheduleRequest> requestPage = page(new Page<>(current, size), wrapper);
        IPage<LabScheduleRequestVo> requestVoPage = requestPage.convert(LabScheduleRequestVo::new);
        
        if (requestVoPage.getRecords().size() > 0) {
            addInfo(requestVoPage);
        }

        return requestVoPage;
    }

    private void addInfo(IPage<LabScheduleRequestVo> requestVoPage) {
        var semesterIds = requestVoPage.getRecords()
                                       .stream()
                                       .map(LabScheduleRequestVo::getSemesterId)
                                       .collect(Collectors.toSet());
        var semesters = semesterMapper.selectBatchIds(semesterIds);
        var semesterMap = semesters.stream()
                            .collect(Collectors.toMap(Semester::getSemesterId, Semester::getSemester));

        var teacherIds = requestVoPage.getRecords()
                                      .stream()
                                      .map(LabScheduleRequestVo::getTeacherId)
                                      .collect(Collectors.toSet());
        var teachers = userMapper.selectBatchIds(teacherIds);
        var fullNameMap = teachers.stream()
                                  .collect(Collectors.toMap(User::getUserId, User::getFullName));

        requestVoPage.getRecords().forEach(vo -> {
            vo.setSemester(semesterMap.get(vo.getSemesterId()));
            vo.setFullName(fullNameMap.get(vo.getTeacherId()));
        });
    }

}

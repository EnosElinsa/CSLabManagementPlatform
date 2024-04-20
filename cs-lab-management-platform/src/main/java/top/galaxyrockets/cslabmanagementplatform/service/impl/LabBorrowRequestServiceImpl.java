package top.galaxyrockets.cslabmanagementplatform.service.impl;

import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;

import top.galaxyrockets.cslabmanagementplatform.vo.LabBorrowRequestVo;
import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.entity.Semester;
import top.galaxyrockets.cslabmanagementplatform.entity.LabBorrowRequest;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.SemesterMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.LabBorrowRequestMapper;
import top.galaxyrockets.cslabmanagementplatform.service.ILabBorrowRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class LabBorrowRequestServiceImpl extends ServiceImpl<LabBorrowRequestMapper, LabBorrowRequest> implements ILabBorrowRequestService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SemesterMapper semesterMapper;

    @Override
    public IPage<LabBorrowRequestVo> page(Integer current, Integer size, LabBorrowRequestVo request) {
        var wrapper = Wrappers.lambdaQuery(LabBorrowRequest.class)
                                    .orderByDesc(LabBorrowRequest::getRequestId)
                                    .eq(request.getStudentId() != null, LabBorrowRequest::getStudentId, request.getStudentId())
                                    .like(StrUtil.isNotBlank(request.getLabId()), LabBorrowRequest::getLabId, 
                                        request.getLabId())
                                    .like(StrUtil.isNotBlank(request.getStatus()), LabBorrowRequest::getStatus, 
                                        request.getStatus());

        IPage<LabBorrowRequest> requestPage = page(new Page<>(current, size), wrapper);
        IPage<LabBorrowRequestVo> requestVoPage = requestPage.convert(LabBorrowRequestVo::new);
                
        if (requestVoPage.getRecords().size() > 0) {
            addInfo(requestVoPage);
        }

        return requestVoPage;
    }

    private void addInfo(IPage<LabBorrowRequestVo> requestVoPage) {
        var semesterIds = requestVoPage.getRecords()
                                       .stream()
                                       .map(LabBorrowRequestVo::getSemesterId)
                                       .collect(Collectors.toSet());
        var semesters = semesterMapper.selectBatchIds(semesterIds);
        var semesterMap = semesters.stream()
                                   .collect(Collectors.toMap(Semester::getSemesterId, Semester::getSemester));

        var studentIds = requestVoPage.getRecords()
                                      .stream()
                                      .map(LabBorrowRequestVo::getStudentId)
                                      .collect(Collectors.toSet());
        var students = userMapper.selectBatchIds(studentIds);
        var studentNameMap = students.stream()
                                     .collect(Collectors.toMap(User::getUserId, User::getFullName));
        var majorMap = students.stream()
                               .collect(Collectors.toMap(User::getUserId, User::getMajor));
        var studentClassMap = students.stream()
                                      .collect(Collectors.toMap(User::getUserId, User::getStudentClass));

        requestVoPage.getRecords().forEach(vo -> {
            vo.setSemester(semesterMap.get(vo.getSemesterId()));
            vo.setStudentName(studentNameMap.get(vo.getStudentId()));
            vo.setMajor(majorMap.get(vo.getStudentId()));
            vo.setStudentClass(studentClassMap.get(vo.getStudentId()));
        });
    }

}

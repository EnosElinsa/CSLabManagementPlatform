package top.galaxyrockets.cslabmanagementplatform.service.impl;

import jakarta.annotation.Resource;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;

import top.galaxyrockets.cslabmanagementplatform.vo.RepairRequestVo;
import top.galaxyrockets.cslabmanagementplatform.entity.Lab;
import top.galaxyrockets.cslabmanagementplatform.entity.RepairRequest;
import top.galaxyrockets.cslabmanagementplatform.entity.User;
import top.galaxyrockets.cslabmanagementplatform.mapper.LabMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.UserMapper;
import top.galaxyrockets.cslabmanagementplatform.mapper.RepairRequestMapper;
import top.galaxyrockets.cslabmanagementplatform.service.IRepairRequestService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class RepairRequestServiceImpl extends ServiceImpl<RepairRequestMapper, RepairRequest> implements IRepairRequestService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LabMapper labMapper;

    @Override
    public IPage<RepairRequestVo> page(Integer current, Integer size, RepairRequestVo request) {
        var wrapper = Wrappers.lambdaQuery(RepairRequest.class)
                              .orderByDesc(RepairRequest::getRequestId)
                              .eq(RepairRequest::getTeacherId, request.getTeacherId())
                              .like(StrUtil.isNotBlank(request.getLabId()), RepairRequest::getLabId, request.getLabId());
                                
        IPage<RepairRequest> requestPage = page(new Page<>(current, size), wrapper);
        IPage<RepairRequestVo> requestVoPage = requestPage.convert(RepairRequestVo::new);
        
        if (requestVoPage.getRecords().size() > 0) {
            addInfo(requestVoPage);
        }

        return requestVoPage;
    }

    @Override
    public IPage<RepairRequestVo> pageByTechnicianId(Integer current, Integer size, RepairRequestVo request, Integer technicianId) {
        var labWrapper = Wrappers.lambdaQuery(Lab.class).eq(Lab::getTechnicianId, technicianId);
        var labIds = labMapper.selectList(labWrapper).stream().map(Lab::getLabId).collect(Collectors.toList());

        var wrapper = Wrappers.lambdaQuery(RepairRequest.class)
                              .orderByDesc(RepairRequest::getRequestId)
                              .in(RepairRequest::getLabId, labIds)
                              .like(StrUtil.isNotBlank(request.getLabId()), RepairRequest::getLabId, request.getLabId());
        IPage<RepairRequest> requestPage = page(new Page<>(current, size), wrapper);
        IPage<RepairRequestVo> requestVoPage = requestPage.convert(RepairRequestVo::new);
        
        if (requestVoPage.getRecords().size() > 0) {
            addInfo(requestVoPage);
        }

        return requestVoPage;
    }

    private void addInfo(IPage<RepairRequestVo> requestVoPage) {
        var teacherIds = requestVoPage.getRecords()
                                      .stream()
                                      .map(RepairRequestVo::getTeacherId)
                                      .collect(Collectors.toSet());
        var teachers = userMapper.selectBatchIds(teacherIds);
        var fullNameMap = teachers.stream()
                                  .collect(Collectors.toMap(User::getUserId, User::getFullName));

        var labIds = requestVoPage.getRecords()
                                  .stream()
                                  .map(RepairRequestVo::getLabId)
                                  .collect(Collectors.toSet());
        var labs = labMapper.selectBatchIds(labIds);
        var labNameMap = labs.stream()
                             .collect(Collectors.toMap(Lab::getLabId, Lab::getName));

        requestVoPage.getRecords().forEach(vo -> {
            vo.setFullName(fullNameMap.get(vo.getTeacherId()));
            vo.setName(labNameMap.get(vo.getLabId()));
        });
    }

    @Override
    public boolean save(RepairRequest request) {
        request.setDate(LocalDate.now());
        return super.save(request);
    }
}

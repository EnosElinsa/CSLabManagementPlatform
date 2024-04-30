package top.galaxyrockets.cslabmanagementplatform.service.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.galaxyrockets.cslabmanagementplatform.domain.po.Lab;
import top.galaxyrockets.cslabmanagementplatform.domain.po.User;
import top.galaxyrockets.cslabmanagementplatform.domain.vo.LabVo;
import top.galaxyrockets.cslabmanagementplatform.mapper.LabMapper;
import top.galaxyrockets.cslabmanagementplatform.service.ILabService;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Service
public class LabServiceImpl extends ServiceImpl<LabMapper, Lab> implements ILabService {

    @Override
    public IPage<LabVo> page(Integer current, Integer size, LabVo labVo) {
        var wrapper = Wrappers.lambdaQuery(Lab.class)
                              .orderByDesc(Lab::getLabId)
                              .like(StrUtil.isNotBlank(labVo.getLabId()), Lab::getLabId, labVo.getLabId())
                              .like(StrUtil.isNotBlank(labVo.getName()), Lab::getName, labVo.getName())
                              .like(StrUtil.isNotBlank(labVo.getCategory()), Lab::getCategory, labVo.getCategory());
        var labPage = page(Page.of(current, size), wrapper);
        var labVoPage = labPage.convert(LabVo::new);

        if (labVoPage.getRecords().size() > 0) {
            addInfo(labVoPage);
        }

        return labVoPage;
    }

    private void addInfo(IPage<LabVo> labVoPage) {
        var technicianIds = labVoPage.getRecords()
                                     .stream()
                                     .map(LabVo::getTechnicianId)
                                     .collect(Collectors.toSet());
        var technicians = Db.lambdaQuery(User.class).in(User::getUserId, technicianIds).list();
        var technicianNameMap = technicians.stream()
                                           .collect(Collectors.toMap(User::getUserId, User::getFullName));

        labVoPage.getRecords().forEach(lab -> {
            lab.setTechnicianName(technicianNameMap.get(lab.getTechnicianId()));
        });
    }

}

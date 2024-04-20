package top.galaxyrockets.cslabmanagementplatform.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import top.galaxyrockets.cslabmanagementplatform.entity.LabScheduleRequest;

/**
 * @author EnosElinsa
 * @since 2024/4/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabScheduleRequestVo implements Serializable{

    private static final long serialVersionUID = 1L;

    public LabScheduleRequestVo(LabScheduleRequest labScheduleRequest) {
        this.requestId = labScheduleRequest.getRequestId();
        this.semesterId = labScheduleRequest.getSemesterId();
        this.teacherId = labScheduleRequest.getTeacherId();
        this.courseName = labScheduleRequest.getCourseName();
        this.labCategory = labScheduleRequest.getLabCategory();
        this.studentClass = labScheduleRequest.getStudentClass();
        this.studentCount = labScheduleRequest.getStudentCount();
        this.startWeek = labScheduleRequest.getStartWeek();
        this.endWeek = labScheduleRequest.getEndWeek();
        this.session = labScheduleRequest.getSession();
        this.status = labScheduleRequest.getStatus();
    }

    private Integer requestId;

    private Integer semesterId;
    private String semester;

    private Integer teacherId;
    private String fullName;

    private String courseName;

    private String labCategory;

    private String studentClass;

    private Integer studentCount;

    private Integer startWeek;

    private Integer endWeek;

    private String session;

    private String status;

}

package top.galaxyrockets.cslabmanagementplatform.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import top.galaxyrockets.cslabmanagementplatform.entity.Schedule;

/**
 * @author EnosElinsa
 * @since 2024/04/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public ScheduleVo(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.requestId = schedule.getRequestId();
        this.semesterId = schedule.getSemesterId();
        this.labId = schedule.getLabId();
        this.startWeek = schedule.getStartWeek();
        this.endWeek = schedule.getEndWeek();
        this.day = schedule.getDay();
        this.session = schedule.getSession();
        this.courseName = schedule.getCourseName();
        this.teacherId = schedule.getTeacherId();
        this.studentClass = schedule.getStudentClass(); 
    }

    private Integer scheduleId;

    private Integer requestId;

    private Integer semesterId;
    private String semester;

    private String labId;
    private String name;

    private Integer startWeek;

    private Integer endWeek;

    private String day;

    private String session;

    private String courseName;

    private Integer teacherId;
    private String fullName;

    private String studentClass;

}

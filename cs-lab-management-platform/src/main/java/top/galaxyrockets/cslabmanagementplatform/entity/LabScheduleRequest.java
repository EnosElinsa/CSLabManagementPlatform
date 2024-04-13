package top.galaxyrockets.cslabmanagementplatform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


/**
 * <p>
 * The info of a lab schedule request sent by a teacher.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lab_schedule_request")
public class LabScheduleRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key.
     */
    @TableId(value = "request_id", type = IdType.AUTO)
    private Integer requestId;

    /**
     * The id of the requested semester.
     */
    private Integer semesterId;

    /**
     * The id of the teacher that sends the request.
     */
    private Integer teacherId;

    /**
     * The name of the course with the maximum length of 32 characters.
     */
    private String courseName;

    /**
     * The category of the requested lab, including '软件','硬件' and '网络'.
     */
    private String labCategory;

    /**
     * The class of the students attending the course with the maximum length of 32 characters.
     */
    private String studentClass;

    /**
     * The requested number of the students for the lab.
     */
    private Integer studentCount;

    /**
     * The requested start week.
     */
    private Integer startWeek;

    /**
     * The requested end week.
     */
    private Integer endWeek;

    /**
     * The requested session, including '1-2', '3-5', '6-7', '8-9', '10-12' and '13-15'.
     */
    private String session;

    /**
     * The status of the lab schedule request, including '已排课' and '未排课'.
     */
    private String status;

}

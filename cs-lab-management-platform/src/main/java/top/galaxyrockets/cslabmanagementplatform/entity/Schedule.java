package top.galaxyrockets.cslabmanagementplatform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 * The info of a lab schedule that corresponds a lab schedule request.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key.
     */
    @TableId(value = "schedule_id", type = IdType.AUTO)
    private Integer scheduleId;

    /**
     * The id of the corresponding lab schedule request.
     */
    private Integer requestId;

    /**
     * The id of the scheduled semester.
     */
    private Integer semesterId;

    /**
     * The id of the scheduled lab.
     */
    private String labId;

    /**
     * The scheduled start week.
     */
    private Integer startWeek;

    /**
     * The scheduled end week.
     */
    private Integer endWeek;

    /**
     * The scheduled session, including '1-2', '3-5', '6-7', '8-9', '10-12' and '13-15'.
     */
    private String session;

    /**
     * The name of the course  with the maximum length of 32 characters.
     */
    private String courseName;

    /**
     * The id of the teacher that requests the lab.
     */
    private Integer teacherId;

    /**
     * The class of the students in the course with the maximum length of 32 characters.
     */
    private String studentClass;

}

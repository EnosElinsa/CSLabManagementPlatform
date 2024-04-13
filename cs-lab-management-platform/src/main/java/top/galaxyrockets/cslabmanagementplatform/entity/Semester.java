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
 * One of the two periods that the school or college year is divided into.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("semester")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key.
     */
    @TableId(value = "semester_id", type = IdType.AUTO)
    private Integer semesterId;

    /**
     * The actual semester. 	An semester is in the format like '2023-2024-1', with '1' denoting 'Autumn' and '2' denoting 'Spring'.
     */
    private String semester;

    /**
     * The number of the weeks of a semester, usually 18-20.
     */
    private Integer weekCount;

    /**
     * Whether a semester is the current semester.
     */
    private Boolean isCurrentSemester;

}

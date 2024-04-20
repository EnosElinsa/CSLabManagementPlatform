package top.galaxyrockets.cslabmanagementplatform.entity;

import java.time.LocalDate;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * <p>
 * The info of a equipment repair request.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("repair_request")
public class RepairRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key.
     */
    @TableId(value = "request_id", type = IdType.AUTO)
    private Integer requestId;

    /**
     * The id of the teacher that sends the equipment repair request.
     */
    private Integer teacherId;

    /**
     * The id of the lab that has the equipment to be repaired.
     */
    private String labId;

    /**
     * The description of the malfunction of the equipment with the maximum length of 256 characters.
     */
    private String description;

    /**
     * The date of the equipment repair request.
     */
    private LocalDate date;

    /**
     * The status of the equipment repair request, including '已维修', '未维修' and '维修中'.
     */
    private String status;

}

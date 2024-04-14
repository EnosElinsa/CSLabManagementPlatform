package top.galaxyrockets.cslabmanagementplatform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * <p>
 * The info of a lab.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lab")
public class Lab implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key, denoting the actual location of the lab in the building.
     */
    @TableId(value = "lab_id")
    private String labId;

    /**
     * The name of the lab with maximum length of 32 characters.
     */
    private String name;

    /**
     * The category of the lab, including '软件', '硬件' and '网络'. 
     */
    private String category;

    /**
     * The count of the equipment in the lab.
     */
    private Integer equipmentCount;

    /**
     * The id of the lab technician that supervises the lab. This lab technician is responsible for the repair of all the equipment in the lab.
     */
    private Integer technicianId;
    
}

package top.galaxyrockets.cslabmanagementplatform.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.galaxyrockets.cslabmanagementplatform.entity.Lab;
import lombok.AllArgsConstructor;

/**
 * @author EnosElinsa
 * @since 2024/4/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public LabVo(Lab lab) {
        this.labId = lab.getLabId();
        this.name = lab.getName();
        this.category = lab.getCategory();
        this.equipmentCount = lab.getEquipmentCount();
        this.technicianId = lab.getTechnicianId();
    }

    private String labId;

    private String name;

    private String category;

    private Integer equipmentCount;

    private Integer technicianId;
    private String technicianName;

}

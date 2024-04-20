package top.galaxyrockets.cslabmanagementplatform.vo;

import java.time.LocalDate;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import top.galaxyrockets.cslabmanagementplatform.entity.RepairRequest;

/**
 * @author EnosElinsa
 * @since 2024/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public RepairRequestVo(RepairRequest repairRequest) {
        this.requestId = repairRequest.getRequestId();
        this.teacherId = repairRequest.getTeacherId();
        this.labId = repairRequest.getLabId();
        this.description = repairRequest.getDescription();
        this.date = repairRequest.getDate();
        this.status = repairRequest.getStatus();
    }

    private Integer requestId;

    private Integer teacherId;
    private String fullName;

    private String labId;
    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String status;

}

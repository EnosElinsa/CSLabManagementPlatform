package top.galaxyrockets.cslabmanagementplatform.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import top.galaxyrockets.cslabmanagementplatform.entity.LabBorrowRequest;

/**
 * @author EnosElinsa
 * @since 2024/4/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabBorrowRequestVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public LabBorrowRequestVo(LabBorrowRequest labBorrowRequest) {
        this.requestId = labBorrowRequest.getRequestId();
        this.semesterId = labBorrowRequest.getSemesterId();
        this.studentId = labBorrowRequest.getStudentId();
        this.week = labBorrowRequest.getWeek();
        this.session = labBorrowRequest.getSession();
        this.labId = labBorrowRequest.getLabId();
        this.reason = labBorrowRequest.getReason();
        this.date = labBorrowRequest.getDate();
        this.status = labBorrowRequest.getStatus();
    }

    private Integer requestId;

    private Integer semesterId;
    private String semester;

    private Integer studentId;
    private String studentName;
    private String major;
    private String studentClass;

    private Integer week;

    private String session;

    private String labId;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String status;

}

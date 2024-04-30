package top.galaxyrockets.cslabmanagementplatform.domain.po;

import java.time.LocalDate;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


/**
 * <p>
 * The info of a lab borrowing request sent by a student.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lab_borrow_request")
public class LabBorrowRequest implements Serializable {

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
     * The id of the student that sends the lab borrowing request.
     */
    private Integer studentId;

    /**
     * The requested week for the lab.
     */
    private Integer week;

    /**
     * The scheduled day of the week, including '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' and '星期日'.
     */
    private String day;

    /**
     * The requested session, including '1-2','3-5','6-7','8-9','10-12' and '13-15'.
     */
    private String session;

    /**
     * The id of the requested lab.
     */
    private String labId;

    /**
     * The reason for the lab borrowing request with the maximum length of 128 characters.
     */
    private String reason;

    /**
     * The date of the lab borrowing request.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * The status of the lab borrowing request, including '未审核', '通过', '驳回' and '使用完毕'.
     */
    private String status;

    /**
     * The logic delete field.
     */
    private Boolean deleted;

}

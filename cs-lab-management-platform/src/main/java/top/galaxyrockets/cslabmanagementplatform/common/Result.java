package top.galaxyrockets.cslabmanagementplatform.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;

    private String message;

    private Object data;
     
    public static Result success() {
        return new Result("200", "操作成功", null);
    }

    public static Result success(Object data) {
        return new Result("200", "操作成功", data);
    }

    public static Result error() {
        return new Result("500", "操作失败", null);
    }

    public static Result error(String message) {
        return new Result("500", message, null);
    }

    public static Result error(String code, String message) {
        return new Result(code, message, null);
    }

}
package top.galaxyrockets.cslabmanagementplatform.exception;

import lombok.Getter;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Getter
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String message) {
        super(message);
        this.code = "500";
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;  
    }
    
}

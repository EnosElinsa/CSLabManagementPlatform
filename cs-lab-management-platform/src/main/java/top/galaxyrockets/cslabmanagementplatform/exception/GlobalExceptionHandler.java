package top.galaxyrockets.cslabmanagementplatform.exception;

import org.springframework.web.bind.annotation.ResponseBody;

import top.galaxyrockets.cslabmanagementplatform.common.Result;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 * @description The global exception handler for the project.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Result serviceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
    
}

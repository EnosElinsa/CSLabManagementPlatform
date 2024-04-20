package top.galaxyrockets.cslabmanagementplatform.exception;

import org.springframework.web.bind.annotation.ResponseBody;

import top.galaxyrockets.cslabmanagementplatform.common.Result;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

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

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public Result sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        e.printStackTrace();
        return Result.error("输入的值不存在或者删除的项目的值与其他项目的对应的值有关联");
    }
    
}

package org.example.Controller;

import org.example.exception.BusinessException;
import org.example.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.geom.RectangularShape;

@RestControllerAdvice
public class ProjectExceptionAdvice {


    @ExceptionHandler(SystemException.class)
     public Result doException(SystemException exception){
         /*System.out.println("异常");
         return new Result(666, null, "出现异常");*/

        //记录日志
        //发送消息给运维
        //发邮件给开发人员
        return new Result(exception.getCode(), null, exception.getMessage());
     }

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException exception){
        return new Result(exception.getCode(), null, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception){
        return new Result(Code.UNKNOWN_ERR, null, "This is an unknown error!");
    }




}

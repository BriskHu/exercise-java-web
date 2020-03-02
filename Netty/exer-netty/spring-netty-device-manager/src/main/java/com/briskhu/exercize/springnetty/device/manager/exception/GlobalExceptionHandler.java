package com.briskhu.exercize.springnetty.device.manager.exception;

import com.briskhu.util.web.result.BasicResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理<p/>
 *
 * @author Brisk Hu
 * created on 2020-03-02
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /* ---------------------------------------- methods ---------------------------------------- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResult paramsValidException(MethodArgumentNotValidException manve){
        LOGGER.error("[paramsValidException] 参数验证不通过: {}.", manve);
        return BasicResult.failAsRequestArgs(manve.getBindingResult().getFieldError().getDefaultMessage());
    }

}



package com.halo.health.exception;

import java.util.ArrayList;
import java.util.List;

import com.halo.health.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述：     处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception: ", e);
        return ApiRestResponse.error(HaloMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(HaloMallException.class)
    @ResponseBody
    public Object handleHaloMallException(HaloMallException e) {
        log.error("HaloMallException: ", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleHttpRequestMethodException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: ", e);
        return ApiRestResponse.error(HaloMallExceptionEnum.REQUEST_TYPE_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException: ", e);
        return ApiRestResponse.error(HaloMallExceptionEnum.REQUEST_PARAMETER_ERROR);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object handleHttpMessageNotReadableExceptionException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: ", e);
        return ApiRestResponse.error(HaloMallExceptionEnum.REQUEST_BODY_ERROR);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: ", e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(HaloMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(HaloMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}

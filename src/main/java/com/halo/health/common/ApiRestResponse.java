package com.halo.health.common;

import com.halo.health.exception.HaloMallExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一返回对象
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ApiRestResponse<T> {
    private Integer status;

    private String msg;

    private T data;

    private static final int OK_CODE = 20000;

    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg);
    }

    public static <T> ApiRestResponse<T> error(HaloMallExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }


    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}

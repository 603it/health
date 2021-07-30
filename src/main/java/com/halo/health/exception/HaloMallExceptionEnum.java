package com.halo.health.exception;

/**
 * 描述：     异常枚举
 */
public enum HaloMallExceptionEnum {
    //用户登陆用
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    //用户注册用
    PASSWORD_TOO_SHORT(10011, "密码长度不能小于8位"),
    NAME_EXISTED(10012, "用户名已存在"),
    //更改密码使用
    LOGIN_FAILED(10021, "用户名或密码错误"),
    PASSWORD_SAME(10022, "新旧密码不能相同"),
    NEW_PASSWORD_SAME(10023, "两次新密码不相同"),
    FAIL_PASSWORD(10024, "原密码错误！"),
    //管理员查询用户详情用
    NO_USER(10031, "该用户不存在"),

    //其他
    REQUEST_PARAM_ERROR(10041, "参数错误"),
    PARA_NOT_NULL(10042, "参数不能为空"),
    CREATE_FAILED(10043, "新增失败"),
    UPDATE_FAILED(10044, "更新失败"),
    INSERT_FAILED(10045, "插入失败，请重试"),
    //拦截器用
    NEED_LOGIN(10061, "用户未登录"),
    NEED_ADMIN(10062, "无管理员权限"),

    SYSTEM_ERROR(40000, "系统异常，请从控制台或日志中查看具体错误信息"),
    REQUEST_TYPE_ERROR(40001, "傻孩子，检查一下你的请求方式"),
    REQUEST_PARAMETER_ERROR(40002, "傻孩子，注意你的参数名称！"),
    REQUEST_BODY_ERROR(40003, "请求体中未带参数！");



    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    HaloMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

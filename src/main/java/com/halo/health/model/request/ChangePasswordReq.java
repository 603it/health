package com.halo.health.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordReq {
    @NotNull(message = "请填写旧密码")
    String password;
    @NotNull(message = "请填写第一次新密码")
    String newPassword1;
    @NotNull(message = "请填写第二次新密码")
    String newPassword2;
}

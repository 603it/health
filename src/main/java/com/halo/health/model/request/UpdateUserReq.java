package com.halo.health.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserReq {

    @NotNull(message = "姓名不能为空！")
    private String nickname;

    @NotNull(message = "年龄不能为空！")
    private Integer age;

    @NotNull(message = "性别不能为空！")
    private String gender;

    @NotNull(message = "身高不能为空！")
    private Integer height;

    @NotNull(message = "体重不能为空！")
    private Integer weight;
}

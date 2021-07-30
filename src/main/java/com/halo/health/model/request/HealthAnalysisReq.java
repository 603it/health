package com.halo.health.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HealthAnalysisReq {

    @NotNull(message = "血氧不能为空！")
    private Double bloodOxygen;

    @NotNull(message = "心率不能为空！")
    private Double heartRate;

    @NotNull(message = "温度不能为空！")
    private Double temperature;

}

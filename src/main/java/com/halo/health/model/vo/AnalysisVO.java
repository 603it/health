package com.halo.health.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 罗铭森
 * @date 2021/7/27 16:04
 */
@Data
public class AnalysisVO {

    private Double bloodOxygen;

    private Double heartRate;

    private Double temperature;

    private String proposal;

    private Date createTime;
}

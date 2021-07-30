package com.halo.health.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserAndAnalysisVO {

    private String username;

    private String nickname;

    private Integer age;

    private String gender;

    private Integer height;

    private Integer weight;

    private List<AnalysisVO> analysisVOList;
}

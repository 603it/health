package com.halo.health.controller;

import com.halo.health.common.ApiRestResponse;
import com.halo.health.model.pojo.Analysis;
import com.halo.health.model.request.HealthAnalysisReq;
import com.halo.health.model.vo.AnalysisVO;
import com.halo.health.common.Pageing;
import com.halo.health.service.AnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "健康分析接口")
@RequestMapping("/health")
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    @ApiOperation("健康分析")
    @PostMapping("/analysis")
    public ApiRestResponse healthAnalysis(@RequestBody HealthAnalysisReq healthAnalysisReq) {
        Analysis analysis = analysisService.analysis(healthAnalysisReq);
        return ApiRestResponse.success(analysis);
    }

    @ApiOperation("健康分析列表——普通用户")
    @GetMapping("/analysis/list")
    public ApiRestResponse listAnalysisForUser(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Pageing<AnalysisVO> list = analysisService.list(pageNum, pageSize);
        return ApiRestResponse.success(list);
    }



}

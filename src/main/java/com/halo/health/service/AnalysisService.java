package com.halo.health.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.halo.health.model.pojo.Analysis;
import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.health.model.pojo.User;
import com.halo.health.model.request.HealthAnalysisReq;
import com.halo.health.model.vo.AnalysisVO;
import com.halo.health.common.Pageing;

/**
 *
 */
public interface AnalysisService extends IService<Analysis> {

    Analysis analysis(HealthAnalysisReq healthAnalysisReq);

    Pageing<AnalysisVO> list(Integer pageNum, Integer pageSize);

    IPage<Analysis> listForAdmin(Integer pageNum, Integer pageSize);
}

package com.halo.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.health.exception.HaloMallException;
import com.halo.health.exception.HaloMallExceptionEnum;
import com.halo.health.filter.UserFilter;
import com.halo.health.model.pojo.Analysis;
import com.halo.health.model.pojo.User;
import com.halo.health.model.request.HealthAnalysisReq;
import com.halo.health.model.vo.AnalysisVO;
import com.halo.health.common.Pageing;
import com.halo.health.service.AnalysisService;
import com.halo.health.model.dao.AnalysisMapper;
import com.halo.health.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisServiceImpl extends ServiceImpl<AnalysisMapper, Analysis>
        implements AnalysisService {

    @Autowired
    AnalysisMapper analysisMapper;

    @Autowired
    UserService userService;

    /**
     * 健康分析核心算法
     *
     * @param healthAnalysisReq
     * @return
     */
    @Override
    public Analysis analysis(HealthAnalysisReq healthAnalysisReq) {
        StringBuilder analysisString = new StringBuilder();
        User user = userService.getById(UserFilter.currentUser.getId());
        //人体正常情况下体温都是正常的，所以将正常体温提至if判断里面,满足此条件，后面的条件将不在判断，提升性能
        if (healthAnalysisReq.getTemperature() >= 35.8 && healthAnalysisReq.getTemperature() <= 37.6) {
            analysisString.append("体温正常！平时还是要注意休息，常运动哦！");
        } else if (healthAnalysisReq.getTemperature() < 35) {
            analysisString.append("体温低于35度!!! 属于低体温症，请及时就医！");
        } else if(healthAnalysisReq.getTemperature() < 35.8){
            analysisString.append("体温过低！应适当保暖，服用能量较高的、易消化的食物，比如巧克力、糖类");
        }else if(healthAnalysisReq.getTemperature() < 38) {
            analysisString.append("体温有点高呀！身体有点低热，多喝热水，注意休息，适当采取物理降温方式，必要时可以吃点退烧药！");
        }else {
            analysisString.append("体温异常！体温异常！请立即就医！请立即就医！！！");
        }

        //



        //
        Analysis analysis = new Analysis();
        BeanUtils.copyProperties(healthAnalysisReq, analysis);
        analysis.setUserId(UserFilter.currentUser.getId());
        analysis.setProposal("多喝热水！！！");
        analysis.setCreateTime(new Date());
        int insert = analysisMapper.insert(analysis);
        if (insert == 0) {
            throw new HaloMallException(HaloMallExceptionEnum.INSERT_FAILED);
        }

        return analysis;
    }

    @Override
    public Pageing<AnalysisVO> list(Integer pageNum, Integer pageSize) {
        //分页查询
        Page<Analysis> page = new Page<>(pageNum, pageSize);
        IPage<Analysis> analysisIPage = analysisMapper.selectPage(page, new QueryWrapper<Analysis>()
                .orderByDesc("create_time")
                .eq("user_id", UserFilter.currentUser.getId()));

        //去掉一些不需要返回给前端的字段
        List<AnalysisVO> analysisVOList = listTransformation(analysisIPage.getRecords());

        //统一分页对象封装
        Pageing<AnalysisVO> pageVO = new Pageing<>();
        pageVO.setPages(analysisIPage.getPages());
        pageVO.setCurrent(analysisIPage.getCurrent());
        pageVO.setSize(analysisIPage.getSize());
        pageVO.setTotal(analysisIPage.getTotal());
        pageVO.setItems(analysisVOList);

        return pageVO;
    }

    @Override
    public IPage<Analysis> listForAdmin(Integer pageNum, Integer pageSize) {
        //分页查询
        QueryWrapper<Analysis> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        Page<Analysis> page = new Page<>(pageNum, pageSize);
        return analysisMapper.selectPage(page, wrapper);
    }

    @Override
    public List<AnalysisVO> listForUser(Integer userId) {

        List<Analysis> analysisList = analysisMapper.selectList(new QueryWrapper<Analysis>().eq("user_id", userId));
        //去掉一些不需要返回给前端的字段
        return listTransformation(analysisList);
    }

    private List<AnalysisVO> listTransformation(List<Analysis> analysisList) {
        List<AnalysisVO> analysisVOList = new ArrayList<>();
        analysisList.forEach(analysis -> {
            AnalysisVO analysisVO = new AnalysisVO();
            BeanUtils.copyProperties(analysis, analysisVO);
            analysisVOList.add(analysisVO);
        });
        return analysisVOList;
    }
}





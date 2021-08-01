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
        int flag = 0;
        //血氧正常范围在90-100，越高越好，就算血氧测出高于100也只是偶尔情况，并不能长期保持。这里不做分析！
        if (healthAnalysisReq.getBloodOxygen() >= 90) {
            analysisString.append("血氧正常！");
            flag++;
        } else {
            analysisString.append("血氧饱和度低于90! 需要进行吸氧，建议就医 积极配合医生查找引起血氧饱和度降低的原因！");
        }

        //心率的正常范围在60-100
        if (healthAnalysisReq.getHeartRate() >= 60 && healthAnalysisReq.getHeartRate() < 100) {
            analysisString.append("心率正常！");
            flag++;
        } else if (healthAnalysisReq.getHeartRate() < 50) {
            analysisString.append("心率低于50，除非你是运动员或长期从事重体力劳动，或者你在睡觉。如果有明显的不适症状，比如胸闷、呼吸困难、心率偏快，突然之间近期出现心率减慢这种情况，就要进一步查明导致心率减慢的原因，比如是否由于发生甲状腺功能减退、高钾血症，因为这些情况都可能继发引起心率减慢。建议就医做心电图进一步检查");
        } else if (healthAnalysisReq.getHeartRate() < 60) {
            analysisString.append("心率低于60属于心动过速！ 注意生活规律，不能过度劳累，适当加强体育锻炼，吃饭时切忌吃得太多，否则心脏血流不通畅，心跳也会减缓，每天可以适当喝些白开水，对身体也很有好处。");
        } else {
            analysisString.append("心率高于100属于心动过速，常在剧烈运动及情绪激动以后出现。如果你长期心动过速，就需要结合病史，通过心电图的检查，在医生的指导下明确诊断又有效的药物进行调整治疗，使心率恢复到正常范围之内。");
        }

        //人体正常情况下体温都是正常的，所以将正常体温提至if判断里面,满足此条件，后面的条件将不在判断，提升性能
        if (healthAnalysisReq.getTemperature() >= 35.8 && healthAnalysisReq.getTemperature() <= 37.6) {
            analysisString.append("体温正常！");
            flag++;
        } else if (healthAnalysisReq.getTemperature() < 35) {
            analysisString.append("体温低于35度!!! 属于低体温症，请及时就医！");
        } else if (healthAnalysisReq.getTemperature() < 35.8) {
            analysisString.append("体温过低！应适当保暖，服用能量较高的、易消化的食物，比如巧克力、糖类。");
        } else if (healthAnalysisReq.getTemperature() < 38) {
            analysisString.append("体温有点高呀！身体有点低热，多喝热水，注意休息，适当采取物理降温方式，必要时可以吃点退烧药！");
        } else {
            analysisString.append("体温太高了！建议您立即就医！！！");
        }
        if (flag == 3) {
            analysisString.append("建议保持规律的作息生活，正常的饮食习惯，勤加锻炼，继续保持健康生活哟！！！");
        }
        //封装analysis对象将他插入到表中
        Analysis analysis = new Analysis();
        BeanUtils.copyProperties(healthAnalysisReq, analysis);
        analysis.setUserId(UserFilter.currentUser.getId());
        analysis.setProposal(analysisString.toString());
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





package com.halo.health.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.halo.health.common.ApiRestResponse;
import com.halo.health.model.pojo.Analysis;
import com.halo.health.model.pojo.User;
import com.halo.health.model.vo.UserAndAnalysisVO;
import com.halo.health.service.AnalysisService;
import com.halo.health.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "管理员接口")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AnalysisService analysisService;

    @ApiOperation("查看用户列表")
    @GetMapping("/listOfUser")
    public ApiRestResponse listOfUser(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<User> userIPage = userService.listOfUser(pageNum, pageSize);
        return ApiRestResponse.success(userIPage);
    }

    @ApiOperation("查看分析列表")
    @GetMapping("/listOfAnalysis")
    public ApiRestResponse listOfAnalysis(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<Analysis> analysisIPage = analysisService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(analysisIPage);
    }

    @ApiOperation("查看用户信息")
    @GetMapping("/getUserInfo")
    public ApiRestResponse getUserInfo(@RequestParam String username) {
        UserAndAnalysisVO userInfoDetail = userService.getUserInfoDetail(username);
        return ApiRestResponse.success(userInfoDetail);
    }



}

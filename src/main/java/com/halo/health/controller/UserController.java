package com.halo.health.controller;

import com.halo.health.common.ApiRestResponse;
import com.halo.health.common.Constant;
import com.halo.health.exception.HaloMallExceptionEnum;
import com.halo.health.filter.UserFilter;
import com.halo.health.model.pojo.User;
import com.halo.health.model.request.UpdateUserReq;
import com.halo.health.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Api(tags={"用户接口"})
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ApiRestResponse register(@ApiParam(value = "用户名", required = true) @RequestParam("username") String username,@ApiParam(value = "密码", required = true) @RequestParam("password") String password) {
        //用户名不用为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_USER_NAME);
        }
        //密码不能为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(HaloMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(username,password);
        return ApiRestResponse.success();
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApiRestResponse login(@ApiParam(value = "用户名", required = true) @RequestParam("username") String username,
                                 @ApiParam(value = "密码", required = true) @RequestParam("password") String password,
                                 HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        user.setPassword(null);
        session.setAttribute(Constant.HALO_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    @ApiOperation("查看用户详细信息")
    @GetMapping("/user/getInfo")
    public User getInfo() {
        return userService.getBaseMapper().selectById(UserFilter.currentUser.getId());
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/user/update")
    public ApiRestResponse updateUserInfo(@RequestBody @Valid UpdateUserReq updateUserReq) {
        userService.updateInformation(updateUserReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("退出登录")
    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.HALO_MALL_USER);
        return ApiRestResponse.success();
    }

    @ApiOperation("管理员登录")
    @PostMapping("/adminLogin")
    public ApiRestResponse adminLogin(@RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            //是管理员，执行操作
            //保存用户信息时，不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.HALO_MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(HaloMallExceptionEnum.NEED_ADMIN);
        }
    }
}

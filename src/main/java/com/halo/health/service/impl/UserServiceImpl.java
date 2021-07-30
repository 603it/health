package com.halo.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.health.exception.HaloMallException;
import com.halo.health.exception.HaloMallExceptionEnum;
import com.halo.health.filter.UserFilter;
import com.halo.health.model.pojo.User;
import com.halo.health.model.dao.UserMapper;
import com.halo.health.model.request.UpdateUserReq;
import com.halo.health.service.UserService;
import com.halo.health.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean checkAdminRole(User user) {
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }

    @Override
    public void register(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new HaloMallException(HaloMallExceptionEnum.NAME_EXISTED);
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(Md5Util.jm(password));
        user.setRole(1);
        userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                .eq("password", Md5Util.jm(password));
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new HaloMallException(HaloMallExceptionEnum.LOGIN_FAILED);
        }
        return user;
    }

    @Override
    public void updateInformation(UpdateUserReq updateUserReq) {
        //从session中获取user信息
        User currentUser = UserFilter.currentUser;
        log.info("updateInformation——user拷贝前:"+currentUser.toString());
        BeanUtils.copyProperties(updateUserReq,currentUser);
        log.info("updateInformation——user拷贝后:"+currentUser.toString());

        userMapper.updateById(currentUser);
    }

    @Override
    public IPage<User> listOfUser(Integer pageNum, Integer pageSize) {
        //分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> analysisIPage = userMapper.selectPage(page, null);
        return analysisIPage;
    }

}





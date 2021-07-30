package com.halo.health.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.halo.health.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.health.model.request.UpdateUserReq;

/**
 *
 */
public interface UserService extends IService<User> {

    boolean checkAdminRole(User user);

    void register(String username, String password);

    User login(String username, String password);

    void updateInformation(UpdateUserReq updateUserReq);

    IPage<User> listOfUser(Integer pageNum, Integer pageSize);
}

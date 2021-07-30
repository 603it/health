package com.halo.health.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.health.model.pojo.User;
import com.halo.health.model.request.ChangePasswordReq;
import com.halo.health.model.request.UpdateUserReq;
import com.halo.health.model.vo.UserAndAnalysisVO;

/**
 *
 */
public interface UserService extends IService<User> {

    boolean checkAdminRole(User user);

    void register(String username, String password);

    User login(String username, String password);

    void updateInformation(UpdateUserReq updateUserReq);

    void changePassword(ChangePasswordReq changePasswordReq);

    IPage<User> listOfUser(Integer pageNum, Integer pageSize);

    UserAndAnalysisVO getUserInfoDetail(String username);

}

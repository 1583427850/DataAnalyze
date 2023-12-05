package com.bigdata.dataanalyze.service;

import com.bigdata.dataanalyze.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author lin
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-12-05 15:42:23
*/
public interface UserService extends IService<User> {

    /**
     * 获取用户登录信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);
}

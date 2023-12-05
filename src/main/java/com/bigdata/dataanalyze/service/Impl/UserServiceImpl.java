package com.bigdata.dataanalyze.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bigdata.dataanalyze.common.constant.UserConstant;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.mapper.UserMapper;
import com.bigdata.dataanalyze.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author lin
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-12-05 15:42:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 获取用户登录信息
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object user = request.getSession().getAttribute(UserConstant.USER_SESSION_KEY);
        if(user==null){
            return null;
        }
        if(user instanceof User){
            return (User)user;
        }
        return null;
    }
}





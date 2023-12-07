package com.bigdata.dataanalyze.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.ErrorCode;
import com.bigdata.dataanalyze.common.ResultUtils;
import com.bigdata.dataanalyze.common.constant.UserConstant;
import com.bigdata.dataanalyze.common.dto.LoginDto;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginDto dto, HttpServletRequest request){
        if(dto==null || dto.getUserAccount()==null || dto.getUserPassword()==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"请输入账号密码");
        }

        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUseraccount, dto.getUserAccount()));
        if(one==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"账号不存在");
        }
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_SESSION_KEY,one);
        return ResultUtils.success(one);
    }

}

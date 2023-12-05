package com.bigdata.dataanalyze.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.service.UserService;
import generator.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lin
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-12-05 15:42:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





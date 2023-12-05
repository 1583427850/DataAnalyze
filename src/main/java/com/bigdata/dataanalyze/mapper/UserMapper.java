package com.bigdata.dataanalyze.mapper;

import com.bigdata.dataanalyze.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lin
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-12-05 15:42:23
* @Entity com.bigdata.dataanalyze.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





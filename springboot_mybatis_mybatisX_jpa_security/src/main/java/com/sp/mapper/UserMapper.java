package com.sp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

/**
* @author 13967
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-06-25 15:26:50
* @Entity com.sp.model.entity.User
*/
@Primary
@Mapper
public interface UserMapper extends BaseMapper<User> {

//    User findOneByUsername(@Param("username") String username);

}





package com.sp.mapper;

import com.sp.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserExtMapper {

    User findOneByUsername(@Param("username") String username);

}

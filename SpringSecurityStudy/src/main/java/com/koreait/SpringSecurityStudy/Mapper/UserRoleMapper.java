package com.koreait.SpringSecurityStudy.Mapper;

import com.koreait.SpringSecurityStudy.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int insert(UserRole userRole);
}
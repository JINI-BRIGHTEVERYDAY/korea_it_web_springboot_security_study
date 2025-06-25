package com.koreait.SpringSecurityStudy.repository;

import com.koreait.SpringSecurityStudy.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRoleRepository {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Optional<UserRole> addUserRole() {
        return userRoleMapper.insert(userRole) < 1
                ? Optional.empty() : Optional.of(userRole);
    }
}

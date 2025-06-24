package com.koreait.SpringSecurityStudy.service;


import com.koreait.SpringSecurityStudy.repository.UserRepository;
import dto.ApiRespDto;
import dto.SignupReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> addUser(SignupReqDto signupReqDto) {
        int result = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
        return new ApiRespDto<>("success", "회원가입 성공", result);
    }

}

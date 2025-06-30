package com.koreait.SpringSecurityStudy.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

//Spring security에서 기본으로 제공하는 OAuth2UserService를 상속받아서 커스텀
@Service
public class OAuth2PrincipalUserService extends DefaultOAuth2UserService {

    // OAuth2 로그인 성공시 호출되는 메소드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // Spring Security가 OAuth2 provider에게 AccessToken으로 사용자 정보를 요청함
        // 그 결과로 받은 사용자 정보 (JSON)을 파싱한 객체를 리턴받는다

        OAuth2User oAuth2User = super.loadUser(userRequest);

        //return super.loadUser(userRequest);

        // 사용자 정보 (Map 형태) 추출함
        Map<String, Object> attributes = oAuth2User.getAttributes();

        //어떤 OAuth2 Provider인지 확인
        // Provider => 공급처 (google, naver, kakao)
        String provider = userRequest.getClientRegistration().getClientId();

        //로그인한 사용자의 식별자(id), 이메일
        //로그인시 사용한 이메일
        String email = null;
        //공급처에서 발행한 사용자 식별자
        String id = null;

        //provider 종류에 따라서 사용자 정보 파싱 방식이 다르므로 분기 처리한다

        switch (provider) {
            case "google":
                id = attributes.get("sub").toString();
                email = (String) attributes.get("email");
                break;
        }

        // 우리가 필요한 정보만 골라서 새롭게 attributes를 구성하도록 한다

        Map<String, Object> newAttributes = Map.of(
                "id", id,
                "provider", provider,
                "email", email
        );

        // 권한을 설정 => 임시 권한을 부여 (ROLE_TEMPORARY)
        // 실제 권한 => OAuthSuccessHandler에서 판단
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_TEMPORARY"));

        // spring security가 사용할 OAuth2User 객체 생성에서 반환
        // id => principal.getName()으로 가져올 때 사용된다.
        return new DefaultOAuth2User(authorities, newAttributes, "id");






    }
}

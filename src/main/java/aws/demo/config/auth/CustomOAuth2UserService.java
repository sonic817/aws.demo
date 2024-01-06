package aws.demo.config.auth;

import aws.demo.config.auth.dto.OAuthAttributes;
import aws.demo.config.auth.dto.SessionUser;
import aws.demo.domain.user.User;
import aws.demo.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 로그인 이후 사용자의 정보를 가지고 가입, 정보수정, 세션 저장 등
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /**
     * 사용자 정보 가져오기
     * 소셜 로그인 이후에 호출
     * 사용자의 정보를 처리하고 가공하여 반환합니다.
     *
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService를 이용하여 사용자 정보를 가져옴
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 소셜 미디어 서비스에서 제공하는 사용자 정보 중 registrationId와 userNameAttributeName을 추출
        // 구글인지 네이버인지 등 구분하는 용도
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 추출한 정보로 OAuthAttributes 객체 생성
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 사용자 정보를 저장 또는 업데이트하고 세션에 저장
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        // 권한 부여 및 사용자 정보 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * 소셜 미디어 서비스로부터 받아온 사용자 정보를 DB에 저장 또는 업데이트
     *
     * @param attributes
     * @return
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
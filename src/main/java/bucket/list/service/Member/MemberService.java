package bucket.list.service.Member;

import bucket.list.domain.Member;
import bucket.list.dto.OAuthAttributes;
import bucket.list.dto.SecurityMember;
import bucket.list.dto.SessionMember;
import bucket.list.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public Member saveMember(Member member){
        memberExist(member);
        return memberRepository.save(member);
    }

    private void memberExist(Member member){ // 중복가입확인여부 메서드
        Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
        if(findMember!=null){
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    //데이터 베이스에서 회원정보를 가져오는 UserDetailService인터페이스에 구현 메서드
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByMemberId(memberId);

        if(member==null){
            throw new UsernameNotFoundException(memberId);
        }

        return new SecurityMember(member.get());

        //변경 전
//        return User.builder()
//                .username(member.getMemberId())
//                .password(member.getMemberPassword())
//                .roles(member.getRole().toString())
//                .build();


    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("member", new SessionMember(member));


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes){
        Member member = memberRepository.findByMemberEmail(attributes.getMemberEmail())
                .map(entity -> entity.update(attributes.getMemberName(), attributes.getPicture()))
                .orElse(attributes.toEntity());


        return memberRepository.save(member);
    }
}

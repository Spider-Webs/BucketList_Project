package bucket.list.config;

import bucket.list.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
UseDetail?
스프링 시큐리티 에서 사용자의 정보를 담는 인터페이스
스프링 시큐리티에서 사용자의 정보를 불러오기위해 구현해야하는 인터페이스
 */
@AllArgsConstructor
public class CustomMemberDetails implements UserDetails {

    private final Member member;

    /*
    계정의 권한 목록을 리턴
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_"+member.getRole());


        return collectors;
    }
    
    /*
    계정의 비밀번호 리턴
     */
    @Override
    public String getPassword() {
        return member.getMemberPassword();
    }

    /*
    계정의 고유값을 리턴,pk or 중복이없는 값 
    아이디 중복확인을 거치기때문에 id로 하였다
     */
    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    /*
    계정의 만료여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    계정의 잠김여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    비밀번호 만료여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    계정의 활성화여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

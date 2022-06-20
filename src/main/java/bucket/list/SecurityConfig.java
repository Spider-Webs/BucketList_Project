package bucket.list;

import bucket.list.service.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//WebSecurityConfigurerAdapter 를 상속 받아 메소드 오버라이딩을 통해 보안설정을 커스터마이징 할 수 있음
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    //페이지 권한 설정, 로그인페이지 설정, 로그아웃 메소드등 설정관련
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") //로그인 url설정
                .defaultSuccessUrl("/") //성공시 이동할 url
                .usernameParameter("memberId") //로그인 사용할 파라미터 이름
                .passwordParameter("memberPassword")
                .failureUrl("/members/login/error") //로그인 실패시 이동할 url
                .and()
                .logout() //로그아웃
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//로그아웃 url
                .logoutSuccessUrl("/"); //성공시 이동할 url

        http.authorizeRequests()
                .antMatchers("/","/about/*/read","/about","/participation","/participation/*"
                        ,"/community","/community/finddetail/*","/customer","/members/**").permitAll() //permitAll을 통해 인증없이 해당경로에 접근할 수 있다
//                .antMatchers("/about/*","/about/edit/*","/about/delete/*").hasAnyRole("ADMIN")//해당 나열한 url은 admin만 접근가능하다
                .anyRequest().authenticated()//그 이외에는 인증을 요구한다
                .and()//여기서부터 추가부분 oauth
                .oauth2Login()
                .userInfoEndpoint()
                .userService(memberService);

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());//인증되지않는 사용자가 리소스에 접근하였을때 수행되는 핸들러

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/imgs/**","/css2/**", "/js/**");
    }

    @Bean
    //비밀번호 db저장시 암호화하여 저장되는 함수
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

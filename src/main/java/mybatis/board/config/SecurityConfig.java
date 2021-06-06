package mybatis.board.config;

import mybatis.board.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserServiceImpl userService;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceImpl userService){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userService=userService;
    }

    /**
     실제 인증이 일어나는 클래스
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserServiceImpl userService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authenticationProvider;
    }

    /**
     * 인증을 담당할 프로바이더 구현체를 설정하는 메소드
     */
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider(userService));
    }

    /**
     * 스프링 시큐리티 룰을 무시하게 하는 Url 규칙.
     */
    @Override
    public void configure(WebSecurity web) { // scr/main/resources/static 하위 폴더들 접근 가능하게 하기
        web.ignoring().antMatchers("/css/**", "/jquery/**", "/img/**");
    }

    /**
     * 스프링 시큐리티 룰.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                     .antMatchers("/user/save").permitAll() //모두 허용
                     .antMatchers("/").hasAnyAuthority("ADMIN","USER")
                     .anyRequest().authenticated()
                .and()
                    .csrf().ignoringAntMatchers("/user/save")
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/list") //로그인 성공시 이동할 URL
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                .sessionManagement()
                .maximumSessions(1) //같은 아이디로 1명만 로그인
                .maxSessionsPreventsLogin(false) //false :신규 로그인은 허용, 기존 사용자는 세션 아웃  true: 이미 로그인한 세션이있으면 로그인 불가
                .expiredUrl("/login"); //세션 아웃되면 이동할 url

    }
}

/**
 * Provider로 인증작업을 많이하게됨. (일반적으로는 데이터베이스에 저장된 사용자 정보로 많이 인증)
 * DetailService를 이용하면 자동으로 AuthenticationManager에 DaoAuthenticationProvider에 연결됨.
 * DaoAuthenticationProvider의 이름에 맞게 데이터베이스 인증용 Provider , 인자로 userDetailsService를 받음
 * UserDetailsService로 username 을 이용하여 사용자 정보를 디비에서 조회하고 UserDetails 객체를 리턴
 *
 * DaoAutenticationProvider은
 * Protected void additionalAuthenticationChecks(UserDetail userDetails,UsernamePasswordAuthenticationToken token)
 * 을이용해서 검증을 진행.
 *
 * 아무튼 additionalAuthenticationChecks 메소드는 UserDetails와 UsernamePasswordAuthenticationToken을 인자로 받는데,
 * UserDetails는 UserDetailsService에서 디비조회후, 결과값이고
 * UsernamePasswordAuthenticationToken은 로그인 폼에서 넘긴 사용자가 입력한 유저아이디와 비밀번호이다.
 * 내부적으로 이둘을 additionalAuthenticationToken 으로 검증하게됨.(동일하면 성공,  틀리면 실패)
 */
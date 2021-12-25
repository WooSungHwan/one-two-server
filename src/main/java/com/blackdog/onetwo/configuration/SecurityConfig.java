package com.blackdog.onetwo.configuration;

import com.blackdog.onetwo.configuration.security.SecurityAccessDeniedHandler;
import com.blackdog.onetwo.configuration.security.SecurityAuthenticationEntryPoint;
import com.blackdog.onetwo.configuration.security.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by niceboyone on 2019. 7. 25..
 *
 * @EnableWebSecurity
 * 스프링 MVC 인수 결정자를 설정하여, 핸들러 메소드가 @AuthenticationPrincipal 애너테이션이 붙은 인자를 사용하여 인증한 사용자 주체를 받는다.
 * 또한 자동으로 숨겨진 사이트 간 요청 위조(CSRF, Cross-Site Request Forgery) 토큰필드(token field)를 스프링의 폼 바인딩 태그 라이브러리를 사용하여 추가하는 빈을 설정한다.
 * @EnableGlobalMethodSecurity
 * Controlelr 메서드에 권한을 추가할 수 있다.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;
    private final SecurityAccessDeniedHandler securityAccessDeniedHandler;
    private final SecurityUserDetailsService securityUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .formLogin().and()
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .accessDeniedHandler(securityAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/favicon.ico", "/resources/**", "/error/**")
                .permitAll()
                .antMatchers("/api/*/**")
//                .authenticated()
                .permitAll()
                .anyRequest()
                .permitAll();

//        http.addFilterBefore(authKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

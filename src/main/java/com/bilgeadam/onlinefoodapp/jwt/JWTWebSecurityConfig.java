package com.bilgeadam.onlinefoodapp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtUnauthorizationEntryPoint jwtUnauthorizationEntryPoint;
    private final UserDetailsService jwtUserDetailService;
    private final JwtTokenAuthorizationRequestFilter jwtTokenAuthorizationRequestFilter;
    @Autowired
    public JWTWebSecurityConfig(JwtUnauthorizationEntryPoint jwtUnauthorizationEntryPoint,
                                UserDetailsService jwtUserDetailService,
                                JwtTokenAuthorizationRequestFilter jwtTokenAuthorizationRequestFilter) {
        this.jwtUnauthorizationEntryPoint = jwtUnauthorizationEntryPoint;
        this.jwtUserDetailService = jwtUserDetailService;
        this.jwtTokenAuthorizationRequestFilter = jwtTokenAuthorizationRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(jwtUserDetailService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtUnauthorizationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest().authenticated();


        httpSecurity
                .addFilterBefore(jwtTokenAuthorizationRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
     webSecurity
             .ignoring()
             .antMatchers(HttpMethod.POST,"/authenticate")
             .antMatchers(HttpMethod.OPTIONS,"/**")
             .and()
             .ignoring()
             .antMatchers(HttpMethod.GET,"/");

    }
}

package com.ky.ulearning.gateway.config;

import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.filter.AccessFilter;
import com.ky.ulearning.gateway.common.filter.JwtAuthorizationTokenFilter;
import com.ky.ulearning.gateway.common.filter.PermissionFilter;
import com.ky.ulearning.gateway.common.security.JwtAccountDetailsService;
import com.ky.ulearning.gateway.common.security.JwtAuthenticationEntryPoint;
import com.ky.ulearning.gateway.common.security.JwtAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author luyuhao
 * @date 19/12/06 21:22
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccountDetailsService jwtAccountDetailsService;

    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Autowired
    private AccessFilter accessFilter;

    @Autowired
    private PermissionFilter permissionFilter;

    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;

    /**
     * 加载全局认证配置
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtAccountDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 默认角色前缀为"ROLE_"，将其设为""
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //禁用CSRF
                .csrf().disable()
                //授权异常捕获
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 过滤请求
                .authorizeRequests()
                //放行patterns
                .antMatchers(gatewayConfigParameters.getAuthenticateReleasePatterns()).anonymous()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and()
                .headers().frameOptions().disable();

        httpSecurity.addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(accessFilter, JwtAuthorizationTokenFilter.class)
                .addFilterAfter(permissionFilter, JwtAuthorizationTokenFilter.class)
                .formLogin().failureHandler(jwtAuthenticationFailureHandler);
    }
}

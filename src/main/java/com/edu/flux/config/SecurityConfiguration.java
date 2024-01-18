package com.edu.flux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 框架自动配置类
 * ---------------------------------------
 * 1.SecurityAutoConfiguration
 * 2.SecurityFilterAutoConfiguration
 * 3.ReactiveSecurityAutoConfiguration
 * 4.MethodSecurityAspectJAutoProxyRegistrar
 * ...
 */


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ReactiveUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorize -> {
            // 静态资源放行(匹配到所有系统原来定义的静态资源路径)
            authorize.matchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    // 其余的任何访问都需要认证
                    .anyExchange().authenticated();
        })
        // 指定认证管理器为数据库查询的认证管理器
        // 需要一个查询用户信息的接口实现类(这个接口就是根据用户名查询用户的详细信息，密码，权限等)
        // 将查询的用户信息返回，框架会自动将用户输入的密码和数据库的密码进行匹配, 如果一致则完成认证
        .authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService))
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

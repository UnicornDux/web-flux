package com.edu.flux.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Service
public class ReactiveUserDetailServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return databaseClient.sql("""
            select u.*, r.id rid, r.name, r.value, pm.id, pm.value pvalue
                from t_user u   
                left join t_user_role ur on ur.user_id = u.id
                left join t_roles r on r.id = ur.role_id 
                left join t_role_perm rp on rp.role_id = r.id
                left join t_perm pm on rp.perm_id = pm.id
            where u.username = ?"""
        ).bind(0, username)
        .fetch()
        .one()
        .map(map -> User.builder()
                    .username(username)
                    .password(map.get("password").toString())
                    //
                    //
                    .authorities(
                        (GrantedAuthority) Stream.of("download", "view", "delete", "open")
                                .map(SimpleGrantedAuthority::new)
                    )
                    .roles("admin", "sale")
                    .build()
        );
    }
}

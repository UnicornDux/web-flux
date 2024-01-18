package com.edu.flux.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/hello")
    public Mono<String> hello() {
       return Mono.just("hello world");
    }

    // 支持复杂的 SpEL 表达式
    // @PreAuthorize("hasAuthority('open')")
    @PreAuthorize("hasAuthority('open') || hasRole('admin')")
    @GetMapping("/haha")
    public Mono<String> world() {
        return Mono.just("world");
    }
}

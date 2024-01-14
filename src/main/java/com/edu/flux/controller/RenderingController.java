package com.edu.flux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

/**
 * 一种视图对象
 */
@Controller
public class RenderingController {


    @GetMapping("/baidu")
    public Rendering render() {
        // Rendering.redirectTo("/aaa");
        return Rendering.redirectTo("http://baidu.com").build();
    }
}

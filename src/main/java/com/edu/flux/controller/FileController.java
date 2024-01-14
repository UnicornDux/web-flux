package com.edu.flux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;


@Slf4j
@Controller
public class FileController {

    @PostMapping("/file")
    public Mono<Void> upload(FilePart part) {
       log.info("收到上传文件: {}", part.filename());
       // 直接保存到某个地方
       // part.transferTo("/tmp/file");

       // 获取文件流处理;
       // part.content().subscribe();
       return Mono.empty();
    }
}

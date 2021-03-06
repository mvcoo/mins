package com.example.mins.encrypt.controller;

import com.example.mins.encrypt.configuration.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @Encrypt
    @GetMapping("hello")
    public String getHello1(@Encrypt(encryptMethod = "des") @RequestBody Object object) {
        log.info("此处的数据1：{}", object);
        return object.toString();
    }

    @Encrypt
    @GetMapping("hello2")
    public String getHello1(@Encrypt(encryptMethod = "des") HttpEntity<String> type) {
        log.info("此处的数据1-1：{}", type.getBody());
        return type.getBody();
    }


    @Encrypt
    @PostMapping("world")
    public String getWorld(@Encrypt @RequestBody Object object) {
        log.info("此处的数据2：{}", object);
        return object.toString();
    }

}

package com.example.springboot.controller;

import com.example.springboot.common.response.WrapResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notice")
@WrapResponse
public class NoticeController {

    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> result = new HashMap<>();
        result.put("service", "notice");
        result.put("status", "ok");
        return result;
    }
}


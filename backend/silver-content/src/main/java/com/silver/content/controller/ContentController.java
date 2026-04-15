package com.silver.content.controller;

import com.silver.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    @GetMapping("/ping")
    public R<String> ping() {
        return R.success("content service alive");
    }
}

package com.silver.user.controller;

import com.silver.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/ping")
    public R<String> ping() {
        return R.success("user service alive");
    }
}

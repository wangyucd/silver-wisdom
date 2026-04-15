package com.silver.course.controller;

import com.silver.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @GetMapping("/ping")
    public R<String> ping() {
        return R.success("course service alive");
    }
}

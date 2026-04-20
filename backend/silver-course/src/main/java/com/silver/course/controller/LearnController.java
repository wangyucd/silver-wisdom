package com.silver.course.controller;

import com.silver.common.api.R;
import com.silver.course.model.GeneratedLearnPageResponse;
import com.silver.course.service.CourseFacadeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course/learn")
public class LearnController {

    private final CourseFacadeService courseFacadeService;

    public LearnController(CourseFacadeService courseFacadeService) {
        this.courseFacadeService = courseFacadeService;
    }

    @GetMapping("/generated")
    public R<GeneratedLearnPageResponse> generated(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return R.success(courseFacadeService.generatedPage(pageNum, pageSize));
    }
}

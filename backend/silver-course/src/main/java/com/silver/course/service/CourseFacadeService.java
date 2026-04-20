package com.silver.course.service;

import com.silver.course.model.GeneratedLearnPageResponse;

public interface CourseFacadeService {

    GeneratedLearnPageResponse generatedPage(int pageNum, int pageSize);
}

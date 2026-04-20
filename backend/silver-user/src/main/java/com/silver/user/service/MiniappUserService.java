package com.silver.user.service;

import com.silver.user.model.request.OnboardingRequest;
import com.silver.user.model.request.UpdateUserStatusRequest;
import com.silver.user.model.response.UserDetailResponse;
import com.silver.user.model.response.UserPageResponse;
import com.silver.user.model.response.UserTagResponse;

public interface MiniappUserService {

    UserTagResponse saveOnboarding(OnboardingRequest request);

    UserTagResponse currentUserTags();

    UserPageResponse adminPageUsers(int pageNum, int pageSize, String keyword, String status);

    UserDetailResponse adminUserDetail(Long userId);

    void updateUserStatus(Long userId, UpdateUserStatusRequest request);
}

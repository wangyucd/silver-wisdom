package com.silver.user.service;

import com.silver.user.model.request.AdminLoginRequest;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;

public interface AdminAuthService {

    AdminLoginResponse login(AdminLoginRequest request);

    void logout();

    AdminProfileResponse currentAdmin();
}

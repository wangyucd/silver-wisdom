package com.silver.user.service;

import com.silver.user.model.request.WxLoginRequest;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;

public interface AuthService {

    LoginResponse wxLogin(WxLoginRequest request);

    void logout();

    CurrentUserResponse currentUser();
}

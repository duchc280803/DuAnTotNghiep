package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.LoginRequest;
import com.example.duantotnghiep.request.RegisterRequest;
import com.example.duantotnghiep.response.JwtTokenResponse;

public interface UserService {

    JwtTokenResponse login(LoginRequest loginRequest);

    JwtTokenResponse register(RegisterRequest registerRequest);
}

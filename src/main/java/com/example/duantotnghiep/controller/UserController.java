package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.LoginRequest;
import com.example.duantotnghiep.request.RegisterRequest;
import com.example.duantotnghiep.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/auth/")
//@RequiredArgsConstructor
public class UserController {

//    private final UserServiceImpl userService;
//
//    @PostMapping("login")
//    public ResponseEntity<LoginRequest> login(@RequestBody LoginRequest loginRequest) {
//        return new ResponseEntity(userService.login(loginRequest), HttpStatus.OK);
//    }
//
//    @PostMapping("register")
//    public ResponseEntity<RegisterRequest> register(@RequestBody RegisterRequest registerRequest) {
//        return new ResponseEntity(userService.register(registerRequest), HttpStatus.CREATED);
//    }
}

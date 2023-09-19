package com.example.duantotnghiep.service.impl;

//@Service
//@RequiredArgsConstructor
public class UserServiceImpl {

//    private final AccountRepository accountRepository;
//
//    private final JwtService jwtService;
//
//    private final AuthenticationManager authenticationManager;
//
//    private final PasswordEncoder encoder;
//
//    @Override
//    public JwtTokenResponse login(LoginRequest loginRequest) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        TaiKhoan taiKhoan = accountRepository.findByUsername(loginRequest.getUsername()).get();
//        String jwt = jwtService.generateToken(new UserCustomDetails(taiKhoan));
//        return JwtTokenResponse.builder().token(jwt).build();
//    }
//
//    @Override
//    public JwtTokenResponse register(RegisterRequest registerRequest) {
//            TaiKhoan user = TaiKhoan.builder()
//                    .username(registerRequest.getUsername())
//                .matKhau(encoder.encode(registerRequest.getPassword()))
//                .email(registerRequest.getEmail())
//                .name(registerRequest.getFullName())
//                .vaiTro(registerRequest.getRole())
//                .build();
//        accountRepository.save(user);
//        String jwt = jwtService.generateToken(new UserCustomDetails(user));
//        return JwtTokenResponse.builder().token(jwt).build();
//    }
}

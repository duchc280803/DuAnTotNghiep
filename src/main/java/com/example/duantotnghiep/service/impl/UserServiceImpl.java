package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.RefreshToken;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.VaiTro;
import com.example.duantotnghiep.jwt.JwtService;
import com.example.duantotnghiep.model.UserCustomDetails;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.repository.RefreshTokenRepository;
import com.example.duantotnghiep.repository.VaiTroRepository;
import com.example.duantotnghiep.request.LoginRequest;
import com.example.duantotnghiep.request.RegisterRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TokenResponse;
import com.example.duantotnghiep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VaiTroRepository vaiTroRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    // TODO Phương thức login
    public TokenResponse login(LoginRequest loginRequest) {
        // Xác thực người dùng
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Kiểm tra xem tài khoản đã tồn tại hay không
        Optional<TaiKhoan> optionalPhatTu = accountRepository.findByUsername(loginRequest.getUsername());
        if (optionalPhatTu.isPresent()) {
            // Tài khoản tồn tại, tạo mới hoặc cập nhật token
            RefreshToken refreshToken = createToken(loginRequest.getUsername());
            String jwtToken = jwtService.generateToken(new UserCustomDetails(optionalPhatTu.get()));

            return TokenResponse.builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .message("Login thành công")
                    .build();
        } else {
            return TokenResponse.builder()
                    .message("Sai username hoặc password")
                    .build();
        }
    }

    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        Optional<TaiKhoan> optionalPhatTu = accountRepository.findByUsername(registerRequest.getUsername());

        if (optionalPhatTu.isPresent()) {
            return MessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }

        Optional<VaiTro> quyenHan = vaiTroRepository.findByName(registerRequest.getRole());

        if (quyenHan.isEmpty()) {
            return MessageResponse.builder().message("Quyền hạn không hợp lệ").build();
        }

        TaiKhoan user = TaiKhoan.builder()
                .id(UUID.randomUUID())
                .username(registerRequest.getUsername())
                .matKhau(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .vaiTro(quyenHan.get())
                .build();

        try {
            TaiKhoan savedUser = accountRepository.save(user);
            String jwtToken = jwtService.generateToken(new UserCustomDetails(savedUser));
            return MessageResponse.builder().message("Registered Successfully").build();
        } catch (Exception e) {
            return MessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }

    @Override
    public RefreshToken createToken(String username) {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .taiKhoan(accountRepository.findByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .thoiGianHetHan(LocalDate.from(LocalDateTime.now().plusMinutes(10)))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override// TODO kiểm tra xem token hết hạn chưa
    public RefreshToken verifyExpiration(RefreshToken token) {
        // trả về thời gian hết hạn của token < Thời gian hiện tại tức token hết hạn
        if (token.getThoiGianHetHan().compareTo(ChronoLocalDate.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(token); // Xóa token
            throw new RuntimeException(token.getToken() +
                    " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

}
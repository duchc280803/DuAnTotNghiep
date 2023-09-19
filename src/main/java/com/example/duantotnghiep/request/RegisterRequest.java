package com.example.duantotnghiep.request;

import com.example.duantotnghiep.entity.VaiTro;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String username;

    private String password;

    private String email;

    private String fullName;

    private VaiTro role;
}

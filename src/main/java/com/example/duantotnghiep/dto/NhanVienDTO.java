package com.example.duantotnghiep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienDTO {

    private UUID id;

    private String username;

    private String password;

    private String email;
}

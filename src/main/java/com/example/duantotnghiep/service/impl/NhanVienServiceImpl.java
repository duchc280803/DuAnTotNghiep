package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.dto.NhanVienDTO;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.NhanVienMapper;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity getAll() {
        return new ResponseEntity(accountRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NhanVienDTO> createNhanVien(NhanVienDTO nhanVienDTO) {
        TaiKhoan taiKhoan = NhanVienMapper.taiKhoan(nhanVienDTO);
        TaiKhoan createTaiKhoan = accountRepository.save(taiKhoan);
        return new ResponseEntity<>(NhanVienMapper.nhanVienDTO(createTaiKhoan),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NhanVienDTO> updateNhanVien(NhanVienDTO nhanVienDTO, UUID id) {
        TaiKhoan findById = accountRepository.findById(id).orElse(null);
        findById.setUserName(nhanVienDTO.getUsername());
        findById.setMatKhau(nhanVienDTO.getPassword());
        findById.setEmail(nhanVienDTO.getEmail());
        TaiKhoan nhanvienUpdate = accountRepository.save(findById);
        return new ResponseEntity<>(NhanVienMapper.nhanVienDTO(nhanvienUpdate),HttpStatus.OK);
    }
}

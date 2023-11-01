package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
<<<<<<< HEAD

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

=======
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;


>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
    @Override
    public Optional<TaiKhoan> findByUserName(String username) {
        return taiKhoanRepository.findByUsername(username);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791

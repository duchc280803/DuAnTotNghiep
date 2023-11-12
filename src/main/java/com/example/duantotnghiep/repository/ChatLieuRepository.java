package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, UUID> {

    List<ChatLieu> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.ChatLieu(th.id, th.tenChatLieu, th.trangThai)\n" +
            "FROM ChatLieu th\n" +
            "WHERE (:trangThai IS NULL AND th.trangThai = 1) OR th.trangThai = :trangThai " +
            "AND (:tenChatLieu IS NULL OR th.tenChatLieu LIKE %:tenChatLieu%)")
    Page<ChatLieu> getAllChatLieu(@Param("trangThai") Integer trangThai, @Param("tenChatLieu") String tenChatLieu, Pageable pageable);
}

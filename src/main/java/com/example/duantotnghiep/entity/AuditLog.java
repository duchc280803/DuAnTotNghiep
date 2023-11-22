package com.example.duantotnghiep.entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {
    private String action;
    private String username;
    private String password;
    private String Id;
    private String Ma;
    private String Ten;
    // Hoặc bạn có thể sử dụng một phương thức bảo mật khác để ẩn mật khẩu
    private LocalDateTime timestamp;

    public AuditLog() {
    }


    public AuditLog(String action, String username, String password, String id, String ma, String ten, LocalDateTime timestamp) {
        this.action = action;
        this.username = username;
        this.password = password;
        Id = id;
        Ma = ma;
        Ten = ten;
        this.timestamp = timestamp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
// Các getter và setter
}
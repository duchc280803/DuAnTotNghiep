package com.example.duantotnghiep.enums;

public enum StatusOrderEnums {

    CHO_THANH_TOAN(1),

    VAN_CHUYEN(2),

    GIAO_HANG(3),

    THANH_CONG(4),

    DA_HUY(5);

    private final int value;

    StatusOrderEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

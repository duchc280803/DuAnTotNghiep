package com.example.duantotnghiep.enums;

public enum BrandEnums {

    DANG_CO_SAN_PHAM(1),

    DA_THANH_TOAN(2);

    private final int value;

    BrandEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

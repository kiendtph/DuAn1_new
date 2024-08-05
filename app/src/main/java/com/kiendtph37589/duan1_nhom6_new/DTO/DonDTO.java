package com.kiendtph37589.duan1_nhom6_new.DTO;

public class DonDTO {
    private String maSP;
    private Long soLuong;

    public DonDTO() {
    }

    public DonDTO(String maSP, Long soLuong) {
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}

package com.kiendtph37589.duan1_nhom6_new.DTO;

import java.util.List;

public class HangDTO {
    private String maHang;
    private String tenHang;
    private Long time;
    private List<SanPhamDTO> sanPham;

    public HangDTO() {
    }

    public HangDTO(String maHang, String tenHang, List<SanPhamDTO> sanPham) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.sanPham = sanPham;
    }

    public HangDTO(String maHang, String tenHang, Long time) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.time = time;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<SanPhamDTO> getSanPham() {
        return sanPham;
    }

    public void setSanPham(List<SanPhamDTO> sanPham) {
        this.sanPham = sanPham;
    }
}

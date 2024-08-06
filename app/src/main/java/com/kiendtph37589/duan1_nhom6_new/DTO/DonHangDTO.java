package com.kiendtph37589.duan1_nhom6_new.DTO;

import java.util.List;

public class DonHangDTO {
    private String maDonHang;
    private String maKhachHang;
    private List<DonDTO> listSP;
    private Long time;
    private int trangThai;
    private Long giaDon;
    private String ngayMua;

    public DonHangDTO() {
    }

    public DonHangDTO(String maDonHang, String maKhachHang, List<DonDTO> listSP, Long time, int trangThai, Long giaDon, String ngayMua) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.listSP = listSP;
        this.time = time;
        this.trangThai = trangThai;
        this.giaDon = giaDon;
        this.ngayMua = ngayMua;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public List<DonDTO> getListSP() {
        return listSP;
    }

    public void setListSP(List<DonDTO> listSP) {
        this.listSP = listSP;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Long getGiaDon() {
        return giaDon;
    }

    public void setGiaDon(Long giaDon) {
        this.giaDon = giaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class MenuKuliner extends KulinerItem {
    private String jenis;
    private int harga;

    public MenuKuliner(String idMenu, String namaMenu, String jenis, int harga, int rating) {
        super(idMenu, namaMenu, rating); // panggil constructor superclass
        this.jenis = jenis;
        this.harga = harga;
    }

    // Getter & Setter
    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override // OVERRIDING
    public String toString() {
        return super.toString() + " | Jenis: " + jenis + " | Harga: Rp" + harga;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class TempatKuliner extends KulinerItem {
    private String lokasi;
    private List<MenuKuliner> daftarMenu;

    public TempatKuliner(String idTempat, String namaTempat, String lokasi, int rating) {
        super(idTempat, namaTempat, rating); // panggil constructor superclass
        this.lokasi = lokasi;
        this.daftarMenu = new ArrayList<>();
    }

    // Getter & Setter
    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public List<MenuKuliner> getDaftarMenu() {
        return daftarMenu;
    }

    public void addMenu(MenuKuliner menu) {
        daftarMenu.add(menu);
    }

    public boolean removeMenu(MenuKuliner menu) {
        return daftarMenu.remove(menu);
    }

    @Override // OVERRIDING
    public String toString() {
        return super.toString() + " | Lokasi: " + lokasi + " | Jumlah Menu: " + daftarMenu.size();
    }
}
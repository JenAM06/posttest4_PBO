/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public abstract class KulinerItem {
    private String id;
    private String nama;
    private int rating;

    public KulinerItem(String id, String nama, int rating) {
        this.id = id;
        this.nama = nama;
        this.rating = rating;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override // OVERRIDING
    public String toString() {
        return "[" + id + "] " + nama + " | Rating: " + rating;
    }
}
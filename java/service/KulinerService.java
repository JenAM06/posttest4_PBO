// File: service/KulinerService.java

// File: service/KulinerService.java

package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.MenuKuliner;
import model.TempatKuliner;

// KulinerService implements IManajemenKuliner (Memenuhi Kriteria Kombinasi Abstraction)
public class KulinerService implements IManajemenKuliner {
    private final List<TempatKuliner> daftarTempat = new ArrayList<>();
    private int nextTempatId = 1;
    private int nextMenuId = 1;

    public KulinerService() {
        initDummyData();
    }

    // ---------- Initialization ----------
    private void initDummyData() {
        // Menggunakan method createTempat dari KulinerService
        TempatKuliner t1 = createTempat("Warung Nasi Kuning Bu Ani", "Jl. Ahmad Yani", 5);
        addMenuToTempat(t1.getId(), "Nasi Kuning Komplit", 1, 20000, 5);
        addMenuToTempat(t1.getId(), "Teh Manis", 2, 5000, 4);

        TempatKuliner t2 = createTempat("Cafe Kopi Kita", "Jl. Lambung Mangkurat", 4);
        addMenuToTempat(t2.getId(), "Kopi Hitam", 2, 15000, 4);
        addMenuToTempat(t2.getId(), "Roti Bakar", 1, 12000, 4);

        TempatKuliner t3 = createTempat("Sate Madura Pak Kumis", "Jl. Pahlawan", 5);
        addMenuToTempat(t3.getId(), "Sate Ayam", 1, 25000, 5);

        TempatKuliner t4 = createTempat("Bakso Mantap", "Jl. Pemuda", 3);
        addMenuToTempat(t4.getId(), "Bakso Urat", 1, 18000, 3);

        TempatKuliner t5 = createTempat("Ayam Geprek Pedas", "Jl. Antasari", 4);
        addMenuToTempat(t5.getId(), "Ayam Geprek", 1, 20000, 4);
        addMenuToTempat(t5.getId(), "Es Teh Jumbo", 2, 7000, 3);

        TempatKuliner t6 = createTempat("Soto Banjar Hj. Ida", "Jl. Juanda", 5);
        addMenuToTempat(t6.getId(), "Soto Banjar", 1, 22000, 5);

        TempatKuliner t7 = createTempat("Martabak Manis 77", "Jl. Merdeka", 4);
        addMenuToTempat(t7.getId(), "Martabak Coklat Keju", 1, 30000, 5);
    }

    // ---------- Create (Implementasi dari Interface) ----------
    @Override
    public TempatKuliner createTempat(String nama, String lokasi, int rating) {
        if (!validateRating(rating)) throw new IllegalArgumentException("Rating harus 1-5");
        String id = "T" + (nextTempatId++);
        TempatKuliner tempat = new TempatKuliner(id, nama, lokasi, rating);
        daftarTempat.add(tempat);
        return tempat;
    }

    public boolean addMenuToTempat(String idTempatOrName, String namaMenu, int jenisPil, int harga, int rating) {
        TempatKuliner tempat = findTempat(idTempatOrName);
        if (tempat == null) return false;
        if (!validateJenis(jenisPil) || !validateHarga(harga) || !validateRating(rating)) return false;
        String jenis = (jenisPil == 1) ? "Makanan" : "Minuman";
        String idMenu = "M" + (nextMenuId++);
        MenuKuliner menu = new MenuKuliner(idMenu, namaMenu, jenis, harga, rating);
        tempat.addMenu(menu);
        return true;
    }

    // ---------- Read (Implementasi dari Interface) ----------
    @Override
    public List<TempatKuliner> getAllTempat() {
        return new ArrayList<>(daftarTempat);
    }
    
    // METHOD OVERLOADING (Polymorphism)
    @Override
    public TempatKuliner findTempat(String idOrName) { 
        if (idOrName == null) return null;
        String key = idOrName.trim();
        for (TempatKuliner t : daftarTempat) {
            if (t.getId().equalsIgnoreCase(key) || t.getNama().equalsIgnoreCase(key)) return t;
        }
        // Mencoba mencari menggunakan overloading jika input adalah angka
        try {
            int numericId = Integer.parseInt(key);
            return findTempat(numericId);
        } catch (NumberFormatException e) {
            return null; // Bukan ID numerik atau Nama
        }
    }
    
    // METHOD OVERLOADING (Polymorphism)
    // Mencari TempatKuliner hanya berdasarkan ID numerik (misal: 1 untuk "T1")
    public TempatKuliner findTempat(int id) { 
        String targetId = "T" + id;
        for (TempatKuliner t : daftarTempat) {
            if (t.getId().equalsIgnoreCase(targetId)) return t;
        }
        return null;
    }

    public List<String> search(String keyword) {
        List<String> hasil = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return hasil;
        String key = keyword.toLowerCase(Locale.ROOT).trim();

        // 1) Match tempat (by name or location) -> include all its menus
        for (TempatKuliner t : daftarTempat) {
            if (t.getNama().toLowerCase(Locale.ROOT).contains(key) ||
                t.getLokasi().toLowerCase(Locale.ROOT).contains(key)) {
                hasil.add(t.toString());
                for (MenuKuliner m : t.getDaftarMenu()) {
                    hasil.add("    - " + m.toString());
                }
            }
        }

        // 2) Match menu name / jenis, but avoid duplicates: if no tempat matched, list matching menus
        boolean anyTempatMatched = hasil.stream().anyMatch(s -> s.startsWith("[T"));
        if (!anyTempatMatched) {
            for (TempatKuliner t : daftarTempat) {
                for (MenuKuliner m : t.getDaftarMenu()) {
                    if (m.getNama().toLowerCase(Locale.ROOT).contains(key) ||
                        m.getJenis().toLowerCase(Locale.ROOT).contains(key)) {
                        hasil.add("Tempat: " + t.getNama());
                        hasil.add("    - " + m.toString());
                    }
                }
            }
        }
        return hasil;
    }

    // Metode filter ganda (menggunakan rentang)
    public List<String> filter(int minRating, int maxRating, int minHarga, int maxHarga) {
        List<String> hasil = new ArrayList<>();
        for (TempatKuliner t : daftarTempat) {
            boolean hasMatch = false;
            for (MenuKuliner m : t.getDaftarMenu()) {
                if (m.getRating() >= minRating && m.getRating() <= maxRating &&
                    m.getHarga() >= minHarga && m.getHarga() <= maxHarga) {
                    if (!hasMatch) {
                        hasil.add("-------------------------------------------------");
                        hasil.add(t.toString());
                        hasMatch = true;
                    }
                    hasil.add("    - " + m.toString());
                }
            }
        }
        if (!hasil.isEmpty()) {
            hasil.add("-------------------------------------------------");
        }
        return hasil;
    }

    @Override
    public List<String> recommendBestMenus() {
        List<String> hasil = new ArrayList<>();
        int maxRating = 0;
        for (TempatKuliner t : daftarTempat) {
            for (MenuKuliner m : t.getDaftarMenu()) {
                if (m.getRating() > maxRating) {
                    maxRating = m.getRating();
                }
            }
        }
        if (maxRating == 0) return hasil;
        for (TempatKuliner t : daftarTempat) {
            for (MenuKuliner m : t.getDaftarMenu()) {
                if (m.getRating() == maxRating) {
                    hasil.add(m.getNama() + " (di " + t.getNama() + ") - Rating: " + m.getRating());
                }
            }
        }
        return hasil;
    }

    // ---------- Update (Implementasi dari Interface) ----------
    @Override
    public boolean updateTempat(String idOrName, String newName, String newLokasi, Integer newRating) {
        TempatKuliner t = findTempat(idOrName);
        if (t == null) return false;
        if (newName != null && !newName.trim().isEmpty()) t.setNama(newName);
        if (newLokasi != null && !newLokasi.trim().isEmpty()) t.setLokasi(newLokasi);
        if (newRating != null) {
            if (!validateRating(newRating)) return false;
            t.setRating(newRating);
        }
        return true;
    }

    public boolean updateMenu(String idTempatOrName, String idOrNamaMenu, String newNama, Integer jenisPil,
                             Integer newHarga, Integer newRating) {
        TempatKuliner t = findTempat(idTempatOrName);
        if (t == null) return false;
        MenuKuliner m = findMenu(t, idOrNamaMenu);
        if (m == null) return false;
        if (newNama != null && !newNama.trim().isEmpty()) m.setNama(newNama);
        if (jenisPil != null) {
            if (!validateJenis(jenisPil)) return false;
            m.setJenis((jenisPil == 1) ? "Makanan" : "Minuman");
        }
        if (newHarga != null) {
            if (!validateHarga(newHarga)) return false;
            m.setHarga(newHarga);
        }
        if (newRating != null) {
            if (!validateRating(newRating)) return false;
            m.setRating(newRating);
        }
        return true;
    }

    // ---------- Delete (Implementasi dari Interface) ----------
    @Override
    public boolean deleteTempat(String idOrName) {
        TempatKuliner t = findTempat(idOrName);
        if (t == null) return false;
        return daftarTempat.remove(t);
    }

    public boolean deleteMenu(String idTempatOrName, String idOrNamaMenu) {
        TempatKuliner t = findTempat(idTempatOrName);
        if (t == null) return false;
        MenuKuliner m = findMenu(t, idOrNamaMenu);
        if (m == null) return false;
        return t.removeMenu(m);
    }

    // ---------- Helpers ----------
    public MenuKuliner findMenu(TempatKuliner tempat, String idOrNamaMenu) {
        if (tempat == null || idOrNamaMenu == null) return null;
        String key = idOrNamaMenu.trim();
        for (MenuKuliner m : tempat.getDaftarMenu()) {
            if (m.getId().equalsIgnoreCase(key) || m.getNama().equalsIgnoreCase(key)) return m;
        }
        return null;
    }

    public TempatKuliner findTempatByMenu(MenuKuliner menu) {
        for (TempatKuliner t : daftarTempat) {
            if (t.getDaftarMenu().contains(menu)) return t;
        }
        return null;
    }

    // ---------- Validation ----------
    private boolean validateRating(int rating) { return rating >= 1 && rating <= 5; }
    private boolean validateHarga(int harga) { return harga >= 0; }
    private boolean validateJenis(int jenisPil) { return jenisPil == 1 || jenisPil == 2; }
}

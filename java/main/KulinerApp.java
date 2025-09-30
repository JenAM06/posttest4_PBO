/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// File: main/KulinerApp.java


package main;

import java.util.List;
import java.util.Scanner;
import model.MenuKuliner;
import model.TempatKuliner;
import service.KulinerService;

public class KulinerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final KulinerService service = new KulinerService();

    public static void main(String[] args) {
        System.out.println("=== EKSPLORASI KULINER SAMARINDA ===");
        while (true) {
            showMainMenu();
            int pilihan = readIntSafe();
            switch (pilihan) {
                case 1: handleCreate(); break;
                case 2: handleShowAll(); break;
                case 3: handleSearchFilterRecommend(); break;
                case 4: handleUpdate(); break;
                case 5: handleDelete(); break;
                case 6: System.out.println("Terima kasih! Keluar program."); return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- MENU UTAMA ---");
        System.out.println("1. Tambah kuliner");
        System.out.println("2. Tampilkan semua kuliner");
        System.out.println("3. Cari, Filter, & Rekomendasi");
        System.out.println("4. Ubah kuliner");
        System.out.println("5. Hapus kuliner");
        System.out.println("6. Keluar");
        System.out.print("Pilih (1-6): ");
    }

    private static int readIntSafe() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Input tidak boleh kosong. Silakan coba lagi: ");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Input harus berupa angka. Silakan coba lagi: ");
            }
        }
    }

    private static String readStringSafe() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Input tidak boleh kosong. Silakan coba lagi: ");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    private static void handleCreate() {
        System.out.println("\n--- CREATE ---");
        System.out.println("1. Tambah Tempat");
        System.out.println("2. Tambah Menu ke Tempat");
        System.out.print("Pilih: ");
        int pilih = readIntSafe();

        if (pilih == 1) {
            System.out.print("Nama Tempat: ");
            String nama = readStringSafe();
            System.out.print("Lokasi: ");
            String lokasi = readStringSafe();

            int rating;
            while (true) {
                System.out.print("Rating (1-5): ");
                rating = readIntSafe();
                if (rating >= 1 && rating <= 5) break;
                System.out.println("Rating tidak valid. Harus antara 1-5.");
            }

            try {
                TempatKuliner t = service.createTempat(nama, lokasi, rating);
                System.out.println("Berhasil menambah tempat: " + t.getNama() + " (ID: " + t.getId() + ")");
            } catch (IllegalArgumentException ex) {
                System.out.println("Gagal menambah tempat: " + ex.getMessage());
            }

        } else if (pilih == 2) {
            System.out.print("Masukkan ID atau Nama Tempat: ");
            String key = readStringSafe();

            TempatKuliner tempat = service.findTempat(key);
            if (tempat == null) {
                System.out.println("Tempat tidak ditemukan. Gagal menambahkan menu.");
                return;
            }

            System.out.print("Nama Menu: ");
            String namaMenu = readStringSafe();

            int jenis;
            while (true) {
                System.out.print("Jenis (1=Makanan, 2=Minuman): ");
                jenis = readIntSafe();
                if (jenis == 1 || jenis == 2) break;
                System.out.println("Jenis tidak valid. Pilih 1 atau 2.");
            }

            int harga;
            while (true) {
                System.out.print("Harga: ");
                harga = readIntSafe();
                if (harga >= 0) break;
                System.out.println("Harga tidak valid. Harga harus bilangan non-negatif.");
            }

            int rating;
            while (true) {
                System.out.print("Rating (1-5): ");
                rating = readIntSafe();
                if (rating >= 1 && rating <= 5) break;
                System.out.println("Rating tidak valid. Harus antara 1-5.");
            }

            boolean ok = service.addMenuToTempat(key, namaMenu, jenis, harga, rating);
            if (ok) System.out.println("Menu berhasil ditambahkan ke " + tempat.getNama());
            else System.out.println("Gagal menambahkan menu. Terjadi kesalahan internal.");
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    private static void handleShowAll() {
        List<TempatKuliner> semua = service.getAllTempat();
        if (semua.isEmpty()) {
            System.out.println("Belum ada data.");
        } else {
            for (TempatKuliner t : semua) {
                System.out.println("-------------------------------------------------");
                System.out.println(t.getId() + " - " + t.getNama() + " | Lokasi: " + t.getLokasi() + " | Rating: " + t.getRating());
                if (t.getDaftarMenu().isEmpty()) {
                    System.out.println("    (Belum ada menu)");
                } else {
                    for (MenuKuliner m : t.getDaftarMenu()) {
                        // Menggunakan MenuKuliner.toString() (Overriding)
                        System.out.println("    - " + m.getId() + " - " + m.getNama() + " | Jenis: " + m.getJenis() + " | Harga: Rp" + m.getHarga() + " | Rating: " + m.getRating());
                    }
                }
            }
            System.out.println("-------------------------------------------------");
        }
    }

    private static void handleSearchFilterRecommend() {
        boolean looping = true;
        while (looping) {
            System.out.println("\n--- CARI, FILTER, & REKOMENDASI ---");
            System.out.println("1. Cari kuliner");
            System.out.println("2. Filter kuliner");
            System.out.println("3. Rekomendasi makanan (rating tertinggi)");
            System.out.println("4. Kembali ke menu utama");
            System.out.print("Pilih: ");
            int pilih = readIntSafe();
            switch (pilih) {
                case 1:
                    System.out.print("Mau makan apa hari ini? (nama makanan/lokasi/nama tempat): ");
                    String key = readStringSafe();
                    List<String> hasilCari = service.search(key);
                    if (hasilCari.isEmpty()) System.out.println("Tidak ditemukan hasil.");
                    else hasilCari.forEach(System.out::println);
                    break;
                case 2:
                    handleFilter();
                    break;
                case 3:
                    List<String> rec = service.recommendBestMenus();
                    if (rec.isEmpty()) System.out.println("Belum ada menu untuk direkomendasikan.");
                    else {
                        System.out.println("Rekomendasi (rating tertinggi):");
                        rec.forEach(s -> System.out.println(" - " + s));
                    }
                    break;
                case 4:
                    looping = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void handleFilter() {
        int minRating = 0, maxRating = 5;
        int minHarga = 0, maxHarga = Integer.MAX_VALUE;
        boolean filterLoop = true;

        while (filterLoop) {
            System.out.println("\n-- FILTER --");
            System.out.println("1. Filter berdasarkan Harga");
            System.out.println("2. Filter berdasarkan Rating");
            System.out.println("3. Tampilkan hasil");
            System.out.print("Pilih: ");
            int fp = readIntSafe();

            switch (fp) {
                case 1:
                    System.out.println("\n--- PILIH RENTANG HARGA ---");
                    System.out.println("1. Rp0 - Rp15.000");
                    System.out.println("2. Rp15.001 - Rp30.000");
                    System.out.println("3. > Rp30.000");
                    System.out.print("Pilih (1-3): ");
                    int hargaPilihan = readIntSafe();
                    switch (hargaPilihan) {
                        case 1: minHarga = 0; maxHarga = 15000; break;
                        case 2: minHarga = 15001; maxHarga = 30000; break;
                        case 3: minHarga = 30001; maxHarga = Integer.MAX_VALUE; break;
                        default: System.out.println("Pilihan harga tidak valid."); minHarga = 0; maxHarga = Integer.MAX_VALUE;
                    }
                    System.out.println("Filter harga diterapkan.");
                    break;
                case 2:
                    int ratingInput;
                    while (true) {
                        System.out.print("Masukkan rating minimal (1-5): ");
                        ratingInput = readIntSafe();
                        if (ratingInput >= 1 && ratingInput <= 5) {
                            minRating = ratingInput;
                            System.out.println("Filter rating diterapkan.");
                            break;
                        }
                        System.out.println("Rating tidak valid. Harus antara 1-5.");
                    }
                    break;
                case 3:
                    List<String> hasilFilter = service.filter(minRating, maxRating, minHarga, maxHarga);
                    if (hasilFilter.isEmpty()) {
                        System.out.println("Tidak ditemukan hasil filter.");
                    } else {
                        System.out.println("\nHasil Filter:");
                        hasilFilter.forEach(System.out::println);
                    }
                    filterLoop = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void handleUpdate() {
        System.out.print("\nMasukkan ID atau Nama Tempat yang ingin diupdate: ");
        String key = readStringSafe();
        TempatKuliner t = service.findTempat(key);
        if (t == null) {
            System.out.println("Tempat tidak ditemukan.");
            return;
        }
        boolean loop = true;
        while (loop) {
            System.out.println("\nSedang update: " + t.getNama());
            System.out.println("1. Update Nama Tempat");
            System.out.println("2. Update Lokasi Tempat");
            System.out.println("3. Update Rating Tempat");
            System.out.println("4. Update Menu");
            System.out.println("5. Selesai");
            System.out.print("Pilih: ");
            int p = readIntSafe();
            switch (p) {
                case 1:
                    System.out.print("Nama baru: ");
                    String newName = readStringSafe();
                    if (service.updateTempat(key, newName, null, null)) System.out.println("Nama berhasil diperbarui.");
                    else System.out.println("Gagal memperbarui nama.");
                    break;
                case 2:
                    System.out.print("Lokasi baru: ");
                    String newLok = readStringSafe();
                    if (service.updateTempat(key, null, newLok, null)) System.out.println("Lokasi berhasil diperbarui.");
                    else System.out.println("Gagal memperbarui lokasi.");
                    break;
                case 3:
                    int r;
                    while (true) {
                        System.out.print("Rating baru (1-5): ");
                        r = readIntSafe();
                        if (r >= 1 && r <= 5) break;
                        System.out.println("Rating tidak valid. Harus antara 1-5.");
                    }
                    if (service.updateTempat(key, null, null, r)) System.out.println("Rating berhasil diperbarui.");
                    else System.out.println("Gagal memperbarui rating. Pastikan input valid.");
                    break;
                case 4:
                    handleUpdateMenu(t);
                    break;
                case 5:
                    loop = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void handleUpdateMenu(TempatKuliner tempat) {
        if (tempat.getDaftarMenu().isEmpty()) {
            System.out.println("Belum ada menu di tempat ini.");
            return;
        }
        System.out.println("Daftar menu:");
        for (MenuKuliner m : tempat.getDaftarMenu()) System.out.println(" - " + m.getId() + " - " + m.getNama());

        System.out.print("Masukkan ID Menu atau Nama Menu yang ingin diupdate: ");
        String keyMenu = readStringSafe();
        MenuKuliner menu = service.findMenu(tempat, keyMenu);
        if (menu == null) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }
        boolean loop = true;
        while (loop) {
            System.out.println("\nSedang update menu: " + menu.getNama() + " di " + tempat.getNama());
            System.out.println("1. Ubah Nama");
            System.out.println("2. Ubah Jenis (1=Makanan,2=Minuman)");
            System.out.println("3. Ubah Harga");
            System.out.println("4. Ubah Rating");
            System.out.println("5. Selesai");
            System.out.print("Pilih: ");
            int p = readIntSafe();
            switch (p) {
                case 1:
                    System.out.print("Nama baru: ");
                    String nn = readStringSafe();
                    if (service.updateMenu(tempat.getId(), keyMenu, nn, null, null, null)) System.out.println("Nama menu diperbarui.");
                    else System.out.println("Gagal memperbarui nama.");
                    break;
                case 2:
                    int j;
                    while (true) {
                        System.out.print("Jenis (1=Makanan,2=Minuman): ");
                        j = readIntSafe();
                        if (j == 1 || j == 2) break;
                        System.out.println("Jenis tidak valid. Pilih 1 atau 2.");
                    }
                    if (service.updateMenu(tempat.getId(), keyMenu, null, j, null, null)) System.out.println("Jenis diperbarui.");
                    else System.out.println("Gagal memperbarui jenis.");
                    break;
                case 3:
                    int h;
                    while (true) {
                        System.out.print("Harga baru: ");
                        h = readIntSafe();
                        if (h >= 0) break;
                        System.out.println("Harga tidak valid. Harga harus bilangan non-negatif.");
                    }
                    if (service.updateMenu(tempat.getId(), keyMenu, null, null, h, null)) System.out.println("Harga diperbarui.");
                    else System.out.println("Gagal memperbarui harga.");
                    break;
                case 4:
                    int rr;
                    while (true) {
                        System.out.print("Rating baru (1-5): ");
                        rr = readIntSafe();
                        if (rr >= 1 && rr <= 5) break;
                        System.out.println("Rating tidak valid. Harus antara 1-5.");
                    }
                    if (service.updateMenu(tempat.getId(), keyMenu, null, null, null, rr)) System.out.println("Rating diperbarui.");
                    else System.out.println("Gagal memperbarui rating.");
                    break;
                case 5:
                    loop = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void handleDelete() {
        System.out.println("\n--- DELETE ---");
        System.out.println("1. Hapus Tempat (beserta menu)");
        System.out.println("2. Hapus Menu dari Tempat");
        System.out.print("Pilih: ");
        int p = readIntSafe();
        switch (p) {
            case 1:
                System.out.print("Masukkan ID atau Nama Tempat: ");
                String key = readStringSafe();
                System.out.print("Yakin ingin menghapus " + key + "? (Y/N): ");
                String konfirmasi = readStringSafe();
                if (konfirmasi.equalsIgnoreCase("Y")) {
                    boolean ok = service.deleteTempat(key);
                    if (ok) System.out.println("Tempat berhasil dihapus.");
                    else System.out.println("Tempat tidak ditemukan atau gagal dihapus.");
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }
                break;
            case 2:
                System.out.print("Masukkan ID atau Nama Tempat: ");
                String ktemp = readStringSafe();
                TempatKuliner t = service.findTempat(ktemp);
                if (t == null) {
                    System.out.println("Tempat tidak ditemukan.");
                    return;
                }
                System.out.println("Daftar menu:");
                for (MenuKuliner m : t.getDaftarMenu()) System.out.println(" - " + m.getId() + " - " + m.getNama());
                System.out.print("Masukkan ID Menu atau Nama Menu: ");
                String km = readStringSafe();
                System.out.print("Yakin ingin menghapus menu ini? (Y/N): ");
                String konfirmasi2 = readStringSafe();
                if (konfirmasi2.equalsIgnoreCase("Y")) {
                    boolean ok2 = service.deleteMenu(t.getId(), km);
                    if (ok2) System.out.println("Menu berhasil dihapus.");
                    else System.out.println("Menu tidak ditemukan / gagal dihapus.");
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}

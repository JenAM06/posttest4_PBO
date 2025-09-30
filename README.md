# posttest4_PBO
Jen Agresia Misti

2409116007 | A

# Deskripsi singkat
Program ini dirancang untuk mengelola dan memfasilitasi pencarian data kuliner di Samarinda. Aplikasi ini membantu pengguna untuk mencatat tempat-tempat kuliner dan menu-menu di dalamnya, serta menyediakan fitur-fitur pencarian yang memudahkan pengguna mencari kriteria kuliner yang diinginkan.

# Perubahan Package

<img width="660" height="270" alt="image" src="https://github.com/user-attachments/assets/b2600fc9-095a-4d54-bd65-3d08c3e551a5" />

1. Package model

   Representasi data yang akan kita input sebagai data kuliner. Package terdiri dari:
   * KulinerItem.java
     Mendefinisikan kerangka abstrak untuk semua item kuliner. Menyimpan properti dasar (id, nama, rating) dan merupakan induk dari semua objek data.
   * TempatKuliner.java
     Representasi dari sebuah tempat makan/restoran. Saya melakukan extends dari KulinerItem dan menyimpan properti lokasi dan daftarMenu.
   * MenuKuliner.java
     Representasi dari makanan/minuman yang dijual. Saya melakukan extends dari KulinerItem dan menyimpan properti jenis dan harga.

2. Package service
   
   Package service untuk mengelola, memanipulasi, dan memvalidasi data objek yang ada di package model. Package terdiri dari:
   * IManajemenKuliner.java
     Mendefinisikan metode (method) apa saja yang harus ada untuk semua operasi manajemen kuliner (CRUD, pencarian, dan rekomendasi).
   * KulinerService.java
     Menyimpan data (daftarTempat) dan berisi semua logic bisnis yaitu, penambahan data, pencarian (searching), filtering, updating, dan deleting data.

3. Package View
   
   Package main berisi titik masuk (entry point) aplikasi dan berfungsi sebagai lapisan View/Controller sederhana. Tugas utamanya adalah berinteraksi dengan pengguna (input/output). Package terdiri dari:
   * KulinerApp.java
     Menampilkan menu, menerima input dari pengguna (menggunakan Scanner), menangani kesalahan input, dan memanggil method-method di KulinerService untuk menjalankan perintah.

# Penerapan Abstraction

## A. Abstract Class: KulinerItem.java
Tujuan: Mendefinisikan properti umum yang harus dimiliki oleh semua entitas kuliner (baik tempat maupun menu).

<img width="738" height="282" alt="image" src="https://github.com/user-attachments/assets/a9135fba-3b5a-4ac9-aa9c-4a3e5a68465f" />

Kelas ini dideklarasikan sebagai public abstract class KulinerItem. Kelas kuliner menyimpan properti dasar seperti id, nama, rating yang diwariskan oleh kelas turunannya. Kelas TempatKuliner dan MenuKuliner wajib meng-extend KulinerItem, sehingga mereka bisa mengimplementasikan propertinya.

## B. Interface: IManajemenKuliner.java
Tujuan: Mendefinisikan kumpulan method publik untuk service

<img width="1107" height="405" alt="image" src="https://github.com/user-attachments/assets/d7ca8b3c-eed6-4534-b744-457c0b81f928" />

Mendeklarasikan semua fungsi utama seperti createTempat, findTempat, updateTempat, dan recommendBestMenus.

# Penerapan Polymorphism

## A. Overriding (Run-time Polymorphism)
Konsep: Mendefinisikan ulang method yang sudah ada di superclass (kelas induk) pada subclass (kelas anak).

Menggunakan method toString() yang berasal dari superclass Object (di-override di KulinerItem)

<img width="810" height="149" alt="image" src="https://github.com/user-attachments/assets/0483fff3-923e-4c6f-bca9-d7df36672812" />

Setiap kelas memberikan implementasi yang untuk menampilkan data:

1. Menggunakan hasil super.toString() dan menambahkan informasi Jenis dan Harga.
   
   <img width="1063" height="136" alt="image" src="https://github.com/user-attachments/assets/f1d2f45f-471c-41de-a877-4cca4229bbe9" />

2. Menggunakan hasil super.toString() dan menambahkan informasi Lokasi dan jumlah menu.

   <img width="1051" height="143" alt="image" src="https://github.com/user-attachments/assets/85a068e1-ea77-4069-bf53-7064fced1695" />

## B. Overloading
Konsep: Mendefinisikan beberapa method dengan nama yang sama dalam satu kelas, tetapi dengan daftar parameter yang berbeda.

Method ini mencari tempat hanya berdasarkan ID numerik (misal: mencari ID "T3" hanya dengan input 3)

<img width="770" height="201" alt="image" src="https://github.com/user-attachments/assets/f07ab8bd-4681-43e8-ac2b-4caaac6af7a3" />


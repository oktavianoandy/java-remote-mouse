# Remote Mouse

## Cara pemakaian
1. Gunakan vpn apabila berbeda jaringan.
2. Atur resolusi client dan server agar sama.
3. Setting ip dan port di dalam file SETUP
4. Setting ingin menggunakan tcp atau udp untuk metode pengiriman screenshot dekstop server di dalam file Server, Client, MoveMouse, ScreenShare, dengan cara tinggal comment/uncomment baris program yang akan digunakan.
5. Jika menggunakan UDP, Ubah skala menjadi 2 ataupun 3. Jika 1 gambar susah dikirim karena bytenya terlalu besar.
6. Client sebagai yang meremote, server sebagai yang diremote.
7. Jalankan client terlebih dahulu, lalu jalankan server.

## Plus Minus
### Menggunakan TCP
Gambar lebih jelas, namun kadang tampilan layar ketika di remote bisa macet (server tidak mengirim gambar lagi), mungkin dikarenakan masalah jaringan.

### Menggunakan UDP
Gambar burem (ngeblur) tapi pengiriman gambar lebih lancar daripada tcp.


## Catatan
Sumber source code : [https://github.com/Rizki-SS/UDP-JavaRD](https://github.com/Rizki-SS/UDP-JavaRD)
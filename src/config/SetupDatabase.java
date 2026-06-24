package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {
    public static void main(String[] args) {
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS masyarakat (
                    nik VARCHAR(16) PRIMARY KEY,
                    nama_lengkap VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    no_telp VARCHAR(15),
                    password VARCHAR(255) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS petugas (
                    id_petugas INT AUTO_INCREMENT PRIMARY KEY,
                    nama_lengkap VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    no_telp VARCHAR(15) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    level ENUM('admin', 'petugas') NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS pengaduan (
                    id_pengaduan INT AUTO_INCREMENT PRIMARY KEY,
                    nik VARCHAR(16),
                    kategori VARCHAR(50),
                    tgl_kejadian DATE,
                    isi_laporan TEXT,
                    lokasi VARCHAR(255),
                    status ENUM('Menunggu','Diproses','Selesai','Ditolak') DEFAULT 'Menunggu',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    FOREIGN KEY (nik) REFERENCES masyarakat(nik)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tanggapan (
                    id_tanggapan INT AUTO_INCREMENT PRIMARY KEY,
                    id_pengaduan INT,
                    tgl_tanggapan DATE,
                    tanggapan TEXT,
                    id_petugas INT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    FOREIGN KEY (id_pengaduan) REFERENCES pengaduan(id_pengaduan),
                    FOREIGN KEY (id_petugas) REFERENCES petugas(id_petugas)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS login_logs (
                    id_log INT AUTO_INCREMENT PRIMARY KEY,
                    email VARCHAR(100) NOT NULL,
                    role ENUM('masyarakat', 'petugas') NOT NULL,
                    waktu_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.close();
            System.out.println("Tabel berhasil dibuat.");
        } catch (SQLException e) {
            System.err.println("Gagal membuat tabel: " + e.getMessage());
        }
    }
}

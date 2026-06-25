package config;

import java.sql.Connection;
import java.sql.Statement;

public class InsertDataPetugas {
    public static void main(String[] args) {
        try (
                Connection conn = Koneksi.getConnection();
                Statement stmt = conn.createStatement()
        ) {

            stmt.executeUpdate("""
                INSERT INTO petugas (nama_lengkap, email, no_telp, password, level)
                VALUES 
                ('Admin', 'admin@gmail.com', '081234567890', 'admin123', 'admin'),
                ('Budi', 'petugas@gmail.com', '081234123412', 'petugas123', 'petugas');
            """);

            System.out.println("Data petugas berhasil ditambahkan!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

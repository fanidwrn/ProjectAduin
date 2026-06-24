package core;

import config.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {

    public boolean registerMasyarakat(String nik, String namaLengkap, String email, String noTelp, String password) {
        String sql = "INSERT INTO masyarakat (nik, nama_lengkap, email, no_telp, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nik);
            pstmt.setString(2, namaLengkap);
            pstmt.setString(3, email);
            pstmt.setString(4, noTelp);
            pstmt.setString(5, password);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Register Error: " + e.getMessage());
            return false;
        }
    }

    public User login(String email, String password) {
        try (Connection conn = Koneksi.getConnection()) {
            String sqlMasyarakat = "SELECT * FROM masyarakat WHERE email = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlMasyarakat)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    Masyarakat masyarakat = new Masyarakat(
                            rs.getString("nik"),
                            rs.getString("nama_lengkap"),
                            rs.getString("email"),
                            rs.getString("no_telp"),
                            rs.getString("password")
                    );
                    logLogin(email, "masyarakat");
                    return masyarakat;
                }
            }

            String sqlPetugas = "SELECT * FROM petugas WHERE email = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPetugas)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    Petugas petugas = new Petugas(
                            rs.getInt("id_petugas"),
                            rs.getString("nama_lengkap"),
                            rs.getString("email"),
                            rs.getString("no_telp"),
                            rs.getString("password"),
                            rs.getString("level")
                    );
                    logLogin(email, "petugas");
                    return petugas;
                }
            }
        } catch (SQLException e) {
            System.err.println("Login Error: " + e.getMessage());
        }

        return null;
    }

    private void logLogin(String email, String role) {
        String sql = "INSERT INTO login_logs (email, role) VALUES (?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, role);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Log Login Error: " + e.getMessage());
        }
    }
}

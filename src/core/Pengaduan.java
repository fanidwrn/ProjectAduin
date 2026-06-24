package core;

import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pengaduan {
    private int idPengaduan;
    private String nik;
    private String kategori;
    private Date tglKejadian;
    private String isiLaporan;
    private String lokasi;
    private String status;
    private Timestamp createdAt;

    public Pengaduan() {}

    public Pengaduan(int idPengaduan, String nik, String kategori, Date tglKejadian, String isiLaporan, String lokasi, String status, Timestamp createdAt) {
        this.idPengaduan = idPengaduan;
        this.nik = nik;
        this.kategori = kategori;
        this.tglKejadian = tglKejadian;
        this.isiLaporan = isiLaporan;
        this.lokasi = lokasi;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getIdPengaduan() { return idPengaduan; }
    public void setIdPengaduan(int idPengaduan) { this.idPengaduan = idPengaduan; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public Date getTglKejadian() { return tglKejadian; }
    public void setTglKejadian(Date tglKejadian) { this.tglKejadian = tglKejadian; }

    public String getIsiLaporan() { return isiLaporan; }
    public void setIsiLaporan(String isiLaporan) { this.isiLaporan = isiLaporan; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public boolean simpan() {
        String sql = "INSERT INTO pengaduan (nik, kategori, tgl_kejadian, isi_laporan, lokasi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nik);
            pstmt.setString(2, this.kategori);
            pstmt.setDate(3, this.tglKejadian);
            pstmt.setString(4, this.isiLaporan);
            pstmt.setString(5, this.lokasi);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Buat Pengaduan Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Pengaduan> getRiwayatByNik(String nik) {
        List<Pengaduan> list = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan WHERE nik = ? ORDER BY created_at DESC";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nik);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPengaduan(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Get Riwayat Error: " + e.getMessage());
        }
        return list;
    }

    public static List<Pengaduan> getAllPengaduan() {
        List<Pengaduan> list = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan ORDER BY created_at DESC";
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToPengaduan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Get All Pengaduan Error: " + e.getMessage());
        }
        return list;
    }

    public static int getCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM pengaduan WHERE status = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Count by Status Error: " + e.getMessage());
        }
        return 0;
    }

    public static int getCountByStatusForMasyarakat(String status, String nik) {
        String sql = "SELECT COUNT(*) FROM pengaduan WHERE status = ? AND nik = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, nik);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Count by Status (Masyarakat) Error: " + e.getMessage());
        }
        return 0;
    }

    public static boolean beriTanggapan(Tanggapan tanggapan, String statusBaru) {
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sqlTanggapan = "INSERT INTO tanggapan (id_pengaduan, tgl_tanggapan, tanggapan, id_petugas) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlTanggapan)) {
                    pstmt1.setInt(1, tanggapan.getIdPengaduan());
                    pstmt1.setDate(2, tanggapan.getTglTanggapan());
                    pstmt1.setString(3, tanggapan.getTanggapan());
                    pstmt1.setInt(4, tanggapan.getIdPetugas());
                    pstmt1.executeUpdate();
                }

                String sqlUpdateStatus = "UPDATE pengaduan SET status = ? WHERE id_pengaduan = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdateStatus)) {
                    pstmt2.setString(1, statusBaru);
                    pstmt2.setInt(2, tanggapan.getIdPengaduan());
                    pstmt2.executeUpdate();
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Beri Tanggapan Transaction Error: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Beri Tanggapan Connection Error: " + e.getMessage());
            return false;
        }
    }

    public static Tanggapan getTanggapanByPengaduan(int idPengaduan) {
        String sql = "SELECT * FROM tanggapan WHERE id_pengaduan = ? ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPengaduan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Tanggapan t = new Tanggapan();
                    t.setIdTanggapan(rs.getInt("id_tanggapan"));
                    t.setIdPengaduan(rs.getInt("id_pengaduan"));
                    t.setTglTanggapan(rs.getDate("tgl_tanggapan"));
                    t.setTanggapan(rs.getString("tanggapan"));
                    t.setIdPetugas(rs.getInt("id_petugas"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    return t;
                }
            }
        } catch (SQLException e) {
            System.err.println("Get Tanggapan Error: " + e.getMessage());
        }
        return null;
    }

    private static Pengaduan mapResultSetToPengaduan(ResultSet rs) throws SQLException {
        Pengaduan p = new Pengaduan();
        p.setIdPengaduan(rs.getInt("id_pengaduan"));
        p.setNik(rs.getString("nik"));
        p.setKategori(rs.getString("kategori"));
        p.setTglKejadian(rs.getDate("tgl_kejadian"));
        p.setIsiLaporan(rs.getString("isi_laporan"));
        p.setLokasi(rs.getString("lokasi"));
        p.setStatus(rs.getString("status"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}

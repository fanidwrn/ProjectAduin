package core;

import java.sql.Date;
import java.sql.Timestamp;

public class Tanggapan {
    private int idTanggapan;
    private int idPengaduan;
    private Date tglTanggapan;
    private String tanggapan;
    private int idPetugas;
    private Timestamp createdAt;

    public Tanggapan() {}

    public Tanggapan(int idTanggapan, int idPengaduan, Date tglTanggapan, String tanggapan, int idPetugas, Timestamp createdAt) {
        this.idTanggapan = idTanggapan;
        this.idPengaduan = idPengaduan;
        this.tglTanggapan = tglTanggapan;
        this.tanggapan = tanggapan;
        this.idPetugas = idPetugas;
        this.createdAt = createdAt;
    }

    public int getIdTanggapan() { return idTanggapan; }
    public void setIdTanggapan(int idTanggapan) { this.idTanggapan = idTanggapan; }

    public int getIdPengaduan() { return idPengaduan; }
    public void setIdPengaduan(int idPengaduan) { this.idPengaduan = idPengaduan; }

    public Date getTglTanggapan() { return tglTanggapan; }
    public void setTglTanggapan(Date tglTanggapan) { this.tglTanggapan = tglTanggapan; }

    public String getTanggapan() { return tanggapan; }
    public void setTanggapan(String tanggapan) { this.tanggapan = tanggapan; }

    public int getIdPetugas() { return idPetugas; }
    public void setIdPetugas(int idPetugas) { this.idPetugas = idPetugas; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

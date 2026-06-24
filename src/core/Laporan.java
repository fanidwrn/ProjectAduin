package core;

public class Laporan {
    private Pengaduan pengaduan;
    private String namaPelapor;
    private String tanggapan;

    public Laporan(Pengaduan pengaduan, String namaPelapor, String tanggapan) {
        this.pengaduan = pengaduan;
        this.namaPelapor = namaPelapor;
        this.tanggapan = tanggapan;
    }

    public Pengaduan getPengaduan() { return pengaduan; }
    public void setPengaduan(Pengaduan pengaduan) { this.pengaduan = pengaduan; }

    public String getNamaPelapor() { return namaPelapor; }
    public void setNamaPelapor(String namaPelapor) { this.namaPelapor = namaPelapor; }

    public String getTanggapan() { return tanggapan; }
    public void setTanggapan(String tanggapan) { this.tanggapan = tanggapan; }
}

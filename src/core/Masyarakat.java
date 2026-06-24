package core;

public class Masyarakat extends User {
    private String nik;

    public Masyarakat(String nik, String namaLengkap, String email, String noTelp, String password) {
        super(namaLengkap, email, noTelp, password);
        this.nik = nik;
    }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    @Override
    public String getRole() {
        return "masyarakat";
    }
}

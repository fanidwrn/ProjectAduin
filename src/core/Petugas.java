package core;

public class Petugas extends User {
    private int idPetugas;
    private String level;

    public Petugas(int idPetugas, String namaLengkap, String email, String noTelp, String password, String level) {
        super(namaLengkap, email, noTelp, password);
        this.idPetugas = idPetugas;
        this.level = level;
    }

    public int getIdPetugas() { return idPetugas; }
    public void setIdPetugas(int idPetugas) { this.idPetugas = idPetugas; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    @Override
    public String getRole() {
        return level;
    }
}

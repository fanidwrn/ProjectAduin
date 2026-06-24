package core;

public abstract class User {
    protected String namaLengkap;
    protected String email;
    protected String noTelp;
    protected String password;

    public User(String namaLengkap, String email, String noTelp, String password) {
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.noTelp = noTelp;
        this.password = password;
    }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public abstract String getRole();
}

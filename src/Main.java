import views.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Memulai inisialisasi database...");
        try {
            config.SetupDatabase.main(args);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database. Pastikan XAMPP MySQL menyala.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}

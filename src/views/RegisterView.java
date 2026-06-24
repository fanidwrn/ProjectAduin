package views;

import core.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private AuthController authController;

    public RegisterView() {
        authController = new AuthController();
        setTitle("Registrasi - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5); // padding

        // Title
        JLabel titleLabel = new JLabel("Registrasi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 20, 5); // extra bottom padding
        panel.add(titleLabel, gbc);

        // Reset insets and width
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1;

        // Row 1: NIK
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("NIK:"), gbc);
        JTextField nikField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panel.add(nikField, gbc);

        // Row 2: Nama Lengkap
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Nama Lengkap:"), gbc);
        JTextField namaField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panel.add(namaField, gbc);

        // Row 3: Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panel.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panel.add(emailField, gbc);

        // Row 4: No. Telp
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panel.add(new JLabel("No. Telp:"), gbc);
        JTextField noTelpField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        panel.add(noTelpField, gbc);

        // Row 5: Password
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        panel.add(new JLabel("Password:"), gbc);
        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        // Buttons
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.decode("#1A38A1"));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(0, 35));
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(20, 5, 10, 5); // extra top padding
        panel.add(registerButton, gbc);

        JButton backButton = new JButton("Kembali ke Login");
        backButton.setPreferredSize(new Dimension(0, 35));
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(5, 5, 10, 5);
        panel.add(backButton, gbc);

        mainPanel.add(panel, BorderLayout.NORTH);
        add(new JScrollPane(mainPanel));

        registerButton.addActionListener(e -> {
            String nik = nikField.getText();
            String nama = namaField.getText();
            String email = emailField.getText();
            String noTelp = noTelpField.getText();
            String password = new String(passwordField.getPassword());

            if (nik.isEmpty() || nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data bertanda * tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = authController.registerMasyarakat(nik, nama, email, noTelp, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login.");
                new LoginView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi Gagal!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }
}

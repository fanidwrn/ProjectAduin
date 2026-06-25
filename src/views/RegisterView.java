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
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        Color primaryBlue = new Color(33, 64, 154);

        // Title
        JLabel appTitle = new JLabel("Adu.in", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 28));
        appTitle.setForeground(primaryBlue);
        gbc.gridy = 0; gbc.insets = new Insets(20, 20, 10, 20);
        panel.add(appTitle, gbc);

        // Subtitle
        JLabel titleLabel = new JLabel("Registrasi Masyarakat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1; gbc.insets = new Insets(0, 20, 10, 20);
        panel.add(titleLabel, gbc);

        // Deskripsi
        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>Lengkapi form di bawah ini untuk membuat<br>akun dan menggunakan sistem Adu.in</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        descLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2; gbc.insets = new Insets(0, 20, 20, 20);
        panel.add(descLabel, gbc);

        // Data diri Field
        addLabelAndField(panel, gbc, "NIK :", 3, 4);
        JTextField nikField = (JTextField) panel.getComponent(panel.getComponentCount() - 1);

        addLabelAndField(panel, gbc, "Nama Lengkap :", 5, 6);
        JTextField namaField = (JTextField) panel.getComponent(panel.getComponentCount() - 1);

        addLabelAndField(panel, gbc, "Email :", 7, 8);
        JTextField emailField = (JTextField) panel.getComponent(panel.getComponentCount() - 1);

        addLabelAndField(panel, gbc, "No. Telp :", 9, 10);
        JTextField noTelpField = (JTextField) panel.getComponent(panel.getComponentCount() - 1);

        // PASSWORD FIELD
        gbc.gridy = 11; gbc.insets = new Insets(5, 30, 2, 30);
        JLabel passLabel = new JLabel("Password :");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passLabel, gbc);

        gbc.gridy = 12; gbc.insets = new Insets(0, 30, 20, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(passwordField, gbc);

        // Tombol Registrasi
        JButton registerButton = new JButton("Registrasi");
        registerButton.setPreferredSize(new Dimension(0, 40));
        registerButton.setBackground(primaryBlue);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridy = 13; gbc.insets = new Insets(10, 30, 10, 30);
        panel.add(registerButton, gbc);

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(20, 48, 120));
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(primaryBlue);
            }
        });

        // Tombol Balik ke Login
        JButton backButton = new JButton("Kembali ke Login");
        backButton.setPreferredSize(new Dimension(0, 40));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBorder(BorderFactory.createLineBorder(primaryBlue, 2));
        gbc.gridy = 14; gbc.insets = new Insets(0, 30, 30, 30);
        panel.add(backButton, gbc);

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(245, 245, 245));
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.WHITE);
            }
        });

        // SCROLL PANEL
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        registerButton.addActionListener(e -> {
            String nik = nikField.getText();
            String nama = namaField.getText();
            String email = emailField.getText();
            String noTelp = noTelpField.getText();
            String password = new String(passwordField.getPassword());

            if (nik.isEmpty() || nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data tidak boleh kosong! Silahkan Isi Semua data yang benar!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, int gridyLabel, int gridyField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = gridyLabel;
        gbc.insets = new Insets(5, 30, 2, 30);
        panel.add(label, gbc);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(0, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = gridyField;
        gbc.insets = new Insets(0, 30, 10, 30);
        panel.add(textField, gbc);
    }
}
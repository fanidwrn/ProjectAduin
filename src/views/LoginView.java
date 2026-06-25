package views;

import core.AuthController;
import core.Session;
import core.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private AuthController authController;

    public LoginView() {
        authController = new AuthController();
        setTitle("Login - Adu.in");
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

        // Logo
        JLabel appTitle = new JLabel("Adu.in", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 28));
        appTitle.setForeground(primaryBlue);
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        panel.add(appTitle, gbc);

        // Selamat Datang
        JLabel titleLabel = new JLabel("Selamat Datang!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 10, 20);
        panel.add(titleLabel, gbc);

        // Deskripsi
        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>Silakan masuk untuk mulai membuat<br>laporan atau melacak status aduan Anda.</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        descLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 30, 20);
        panel.add(descLabel, gbc);

        // Email Label
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 30, 5, 30);
        panel.add(emailLabel, gbc);

        // Email TextField
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(0, 35));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10))); // Padding dalam textfield
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 15, 30);
        panel.add(emailField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 30, 5, 30);
        panel.add(passwordLabel, gbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 30, 30, 30);
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(0, 40));
        loginButton.setBackground(primaryBlue);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 30, 15, 30);
        panel.add(loginButton, gbc);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(20, 48, 120));
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(primaryBlue);
            }
        });

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(0, 40));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);
        registerButton.setBorder(BorderFactory.createLineBorder(primaryBlue, 2));
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 30, 20, 30);
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(245, 245, 245));
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(Color.WHITE);
            }
        });

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = authController.login(email, password);
            if (user != null) {
                Session.setCurrentUser(user);
                new DashboardView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Login Gagal! Email atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            dispose();
        });
    }
}
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

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Title
        JLabel titleLabel = new JLabel("Selamat Datang!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 40, 5);
        panel.add(titleLabel, gbc);

        // Reset insets
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridwidth = 1;

        // Email row
        JLabel emailLabel = new JLabel("email :");
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panel.add(emailField, gbc);

        // Password row
        JLabel passwordLabel = new JLabel("password :");
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        // Buttons
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(0, 35));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(30, 5, 10, 5);
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(0, 35));
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 10, 5);
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.NORTH); // Align to the top instead of center to avoid stretching vertically too much

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
                JOptionPane.showMessageDialog(this, "Login Berhasil!");
                new DashboardView().setVisible(true);
                dispose();
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

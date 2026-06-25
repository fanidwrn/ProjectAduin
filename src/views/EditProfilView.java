package views;

import core.AuthController;
import core.Masyarakat;
import core.Session;
import core.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditProfilView extends JFrame {
    private AuthController authController;
    private Color primaryBlue = Color.decode("#1A38A1");

    public EditProfilView() {
        authController = new AuthController();
        User currentUser = Session.getCurrentUser();

        setTitle("Edit Profil - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 70));

        JLabel titleLabel = new JLabel("Edit Profil", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Form fields
        JTextField nikField = new JTextField();
        if (currentUser instanceof Masyarakat) {
            nikField.setText(((Masyarakat) currentUser).getNik());
        } else {
            nikField.setEnabled(false);
            nikField.setBackground(new Color(240, 240, 240));
        }

        JTextField namaField = new JTextField(currentUser.getNamaLengkap());
        JTextField emailField = new JTextField(currentUser.getEmail());
        JTextField noTelpField = new JTextField(currentUser.getNoTelp());
        JPasswordField passwordField = new JPasswordField(currentUser.getPassword());

        addFormField(formPanel, "NIK :", nikField);
        addFormField(formPanel, "Nama Lengkap :", namaField);
        addFormField(formPanel, "Email :", emailField);
        addFormField(formPanel, "No. Telp :", noTelpField);
        addFormField(formPanel, "Password :", passwordField);

        formPanel.add(Box.createVerticalStrut(10));

        // Buttons
        JButton saveButton = new JButton("Simpan Perubahan");
        saveButton.setBackground(primaryBlue);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(true);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        saveButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                saveButton.setBackground(new Color(20, 48, 120));
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                saveButton.setBackground(primaryBlue);
            }
        });

        JButton backButton = new JButton("Batal");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBorder(BorderFactory.createLineBorder(primaryBlue, 1));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(new Color(245, 245, 245));
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(Color.WHITE);
            }
        });

        formPanel.add(saveButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backButton);

        // Wrap form panel in scroll pane just in case
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        saveButton.addActionListener(e -> {
            String nik = nikField.getText();
            String nama = namaField.getText();
            String email = emailField.getText();
            String noTelp = noTelpField.getText();
            String password = new String(passwordField.getPassword());

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || (currentUser instanceof Masyarakat && nik.isEmpty())) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update local object
            if (currentUser instanceof Masyarakat) {
                ((Masyarakat) currentUser).setNik(nik);
            }
            currentUser.setNamaLengkap(nama);
            currentUser.setEmail(email);
            currentUser.setNoTelp(noTelp);
            currentUser.setPassword(password);

            boolean success = authController.updateProfile(currentUser);
            if (success) {
                JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui!");
                new ProfilView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui profil!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new ProfilView().setVisible(true);
            dispose();
        });
    }

    private void addFormField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
    }
}

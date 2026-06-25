package views;

import core.Session;
import core.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfilView extends JFrame {

    private Color primaryBlue = Color.decode("#1A38A1");

    public ProfilView() {
        User currentUser = Session.getCurrentUser();

        setTitle("Profil - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 70));
        JLabel titleLabel = new JLabel("Profil", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(currentUser.getNamaLengkap());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String roleStr = currentUser.getRole();
        String roleCapitalized = roleStr.substring(0, 1).toUpperCase() + roleStr.substring(1).toLowerCase();
        JLabel roleLabel = new JLabel(roleCapitalized);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnEdit = createMenuButton("Edit Profil");
        btnEdit.addActionListener(e -> {
            new EditProfilView().setVisible(true);
            dispose();
        });

        JButton btnTentang = createMenuButton("Tentang Sistem");
        btnTentang.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Aplikasi Sistem Pengaduan Masyarakat\nVersi 1.0\nDibuat untuk memenuhi UAS PBO.", "Tentang Sistem", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.decode("#DC3545"));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setOpaque(true);
        btnLogout.setBorder(BorderFactory.createEmptyBorder());
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnLogout.setBackground(Color.decode("#C82333"));
                btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                btnLogout.setBackground(Color.decode("#DC3545"));
            }
        });

        btnLogout.addActionListener(e -> {
            Session.logout();
            new LoginView().setVisible(true);
            dispose();
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(roleLabel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnEdit);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnTentang);
        panel.add(Box.createVerticalStrut(30));
        panel.add(btnLogout);

        add(panel, BorderLayout.CENTER);
        add(createBottomNavBar(), BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(245, 245, 245));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    private JPanel createBottomNavBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setPreferredSize(new Dimension(400, 60));

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });

        JButton btnLaporan = new JButton("Laporan");
        btnLaporan.addActionListener(e -> {
            new LaporanView().setVisible(true);
            dispose();
        });

        JButton btnProfil = new JButton("Profil");
        btnProfil.setEnabled(false);

        navBar.add(btnHome);
        navBar.add(btnLaporan);
        navBar.add(btnProfil);

        return navBar;
    }
}

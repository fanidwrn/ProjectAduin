package views;

import core.Session;
import core.User;

import javax.swing.*;
import java.awt.*;

public class ProfilView extends JFrame {

    public ProfilView() {
        User currentUser = Session.getCurrentUser();

        setTitle("Profil - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A38A1"));
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("Profil Pengguna", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        panel.add(new JLabel("Nama Lengkap: " + currentUser.getNamaLengkap()));
        panel.add(new JLabel("Email: " + currentUser.getEmail()));
        panel.add(new JLabel("No. Telp: " + currentUser.getNoTelp()));
        panel.add(new JLabel("Role: " + currentUser.getRole().toUpperCase()));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            Session.logout();
            new LoginView().setVisible(true);
            dispose();
        });
        panel.add(btnLogout);

        add(panel, BorderLayout.CENTER);
        add(createBottomNavBar(), BorderLayout.SOUTH);
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

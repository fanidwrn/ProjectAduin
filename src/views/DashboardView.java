package views;

import core.Pengaduan;
import core.Session;
import core.User;
import core.Masyarakat;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private User currentUser;

    public DashboardView() {
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Anda harus login terlebih dahulu!");
            System.exit(0);
        }

        currentUser = Session.getCurrentUser();

        setTitle("Dashboard - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A38A1"));
        headerPanel.setPreferredSize(new Dimension(400, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("<html>Selamat Datang,<br>" + currentUser.getNamaLengkap() + "</html>");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int countMenunggu = 0, countDiproses = 0, countSelesai = 0;
        if (currentUser.getRole().equals("masyarakat")) {
            Masyarakat m = (Masyarakat) currentUser;
            countMenunggu = Pengaduan.getCountByStatusForMasyarakat("Menunggu", m.getNik());
            countDiproses = Pengaduan.getCountByStatusForMasyarakat("Diproses", m.getNik());
            countSelesai = Pengaduan.getCountByStatusForMasyarakat("Selesai", m.getNik());
        } else {
            countMenunggu = Pengaduan.getCountByStatus("Menunggu");
            countDiproses = Pengaduan.getCountByStatus("Diproses");
            countSelesai = Pengaduan.getCountByStatus("Selesai");
        }

        centerPanel.add(createStatCard("Menunggu", countMenunggu, new Color(220, 53, 69)));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createStatCard("Diproses", countDiproses, new Color(255, 193, 7)));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createStatCard("Selesai", countSelesai, new Color(40, 167, 69)));

        if (currentUser.getRole().equals("masyarakat")) {
            centerPanel.add(Box.createVerticalStrut(30));
            JButton btnTambah = new JButton("+ Buat Pengaduan");
            btnTambah.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnTambah.setBackground(new Color(34, 139, 34));
            btnTambah.setForeground(Color.WHITE);
            btnTambah.setFont(new Font("Arial", Font.BOLD, 16));
            btnTambah.setMaximumSize(new Dimension(300, 40));
            btnTambah.addActionListener(e -> {
                new FormPengaduanView().setVisible(true);
                dispose();
            });
            centerPanel.add(btnTambah);
        }

        add(new JScrollPane(centerPanel), BorderLayout.CENTER);
        add(createBottomNavBar(), BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, int count, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setPreferredSize(new Dimension(300, 80));
        card.setMaximumSize(new Dimension(400, 80));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblCount = new JLabel(String.valueOf(count));
        lblCount.setForeground(Color.WHITE);
        lblCount.setFont(new Font("Arial", Font.BOLD, 24));

        card.add(lblTitle, BorderLayout.WEST);
        card.add(lblCount, BorderLayout.EAST);

        return card;
    }

    private JPanel createBottomNavBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setPreferredSize(new Dimension(400, 60));

        JButton btnHome = new JButton("Home");
        btnHome.setEnabled(false);

        JButton btnLaporan = new JButton("Laporan");
        btnLaporan.addActionListener(e -> {
            new LaporanView().setVisible(true);
            dispose();
        });

        JButton btnProfil = new JButton("Profil");
        btnProfil.addActionListener(e -> {
            new ProfilView().setVisible(true);
            dispose();
        });

        navBar.add(btnHome);
        navBar.add(btnLaporan);
        navBar.add(btnProfil);

        return navBar;
    }
}

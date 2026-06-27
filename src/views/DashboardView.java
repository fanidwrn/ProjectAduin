package views;

import core.Pengaduan;
import core.Session;
import core.User;
import core.Masyarakat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        Color primaryBlue = Color.decode("#1A38A1");
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 120));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Judul dan Subjudul Header
        JLabel welcomeLabel = new JLabel("<html><div style='font-family: Arial;'>" +
                "<b style='font-size: 16px;'>Selamat Datang, " + currentUser.getNamaLengkap() + "!</b><br><br>" +
                "<span style='font-size: 11px; font-weight: normal;'>Bersama mewujudkan pelayanan yang lebih baik.</span>" +
                "</div></html>");
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // if masyarakat
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

        // Status
        centerPanel.add(createStatCard("Menunggu", countMenunggu, new Color(220, 53, 69), new Color(253, 242, 242)));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createStatCard("Diproses", countDiproses, new Color(230, 165, 0), new Color(255, 251, 234)));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createStatCard("Selesai", countSelesai, new Color(40, 167, 69), new Color(242, 253, 244)));

        // Tombol Khusus Role Masyarakat
        if (currentUser.getRole().equals("masyarakat")) {
            centerPanel.add(Box.createVerticalStrut(30));
            JButton btnTambah = new JButton("+ Buat Pengaduan");
            btnTambah.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnTambah.setBackground(primaryBlue);
            btnTambah.setForeground(Color.WHITE);
            btnTambah.setFont(new Font("Arial", Font.BOLD, 14));
            btnTambah.setMaximumSize(new Dimension(320, 40));
            btnTambah.setFocusPainted(false);
            btnTambah.setContentAreaFilled(false);
            btnTambah.setOpaque(true);
            btnTambah.setBorder(BorderFactory.createEmptyBorder());

            btnTambah.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btnTambah.setBackground(new Color(20, 48, 120));
                    btnTambah.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent evt) {
                    btnTambah.setBackground(primaryBlue);
                }
            });

            btnTambah.addActionListener(e -> {
                new FormPengaduanView().setVisible(true);
                dispose();
            });
            centerPanel.add(btnTambah);
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        add(createBottomNavBar(), BorderLayout.SOUTH);
    }


    // Kartu Status
    private JPanel createStatCard(String title, int count, Color borderColor, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setPreferredSize(new Dimension(320, 70));
        card.setMaximumSize(new Dimension(320, 70));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(borderColor);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblCount = new JLabel(String.valueOf(count));
        lblCount.setForeground(borderColor);
        lblCount.setFont(new Font("Arial", Font.BOLD, 26));

        card.add(lblTitle, BorderLayout.WEST);
        card.add(lblCount, BorderLayout.EAST);

        return card;
    }

    // Navbar
    private JPanel createBottomNavBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setPreferredSize(new Dimension(400, 60));

        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.setEnabled(false);

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

        navBar.add(btnDashboard);
        navBar.add(btnLaporan);
        navBar.add(btnProfil);

        return navBar;
    }
}
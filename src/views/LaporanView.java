package views;

import core.Pengaduan;
import core.Session;
import core.User;
import core.Masyarakat;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LaporanView extends JFrame {
    private User currentUser;

    public LaporanView() {
        currentUser = Session.getCurrentUser();

        setTitle("Laporan Pengaduan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1A38A1"));
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("Laporan Pengaduan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        List<Pengaduan> riwayat;
        if (currentUser.getRole().equals("masyarakat")) {
            Masyarakat m = (Masyarakat) currentUser;
            riwayat = Pengaduan.getRiwayatByNik(m.getNik());
        } else {
            riwayat = Pengaduan.getAllPengaduan();
        }

        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Pengaduan p : riwayat) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            card.setBackground(Color.WHITE);
            // Setting a reasonable maximum size for cards in BoxLayout
            card.setMaximumSize(new Dimension(360, Integer.MAX_VALUE));

            JPanel infoPanel = new JPanel(new GridLayout(4, 1, 2, 2));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.add(new JLabel("ID Pengaduan: " + p.getIdPengaduan()));
            infoPanel.add(new JLabel("Tanggal: " + (p.getCreatedAt() != null ? p.getCreatedAt().toString().substring(0, 10) : p.getTglKejadian().toString())));
            infoPanel.add(new JLabel("Kategori: " + p.getKategori()));

            JLabel statusLabel = new JLabel("Status: " + p.getStatus());
            if (p.getStatus().equalsIgnoreCase("Selesai")) {
                statusLabel.setForeground(new Color(34, 139, 34)); // Forest green
            } else if (p.getStatus().equalsIgnoreCase("Diproses")) {
                statusLabel.setForeground(new Color(255, 140, 0)); // Dark orange
            } else {
                statusLabel.setForeground(Color.RED);
            }
            infoPanel.add(statusLabel);

            String isi = p.getIsiLaporan();
            if (isi.length() > 50) {
                isi = isi.substring(0, 50) + "...";
            }
            JTextArea isiArea = new JTextArea("Isi: " + isi);
            isiArea.setWrapStyleWord(true);
            isiArea.setLineWrap(true);
            isiArea.setEditable(false);
            isiArea.setOpaque(false);
            isiArea.setFont(new Font("Arial", Font.PLAIN, 12));
            isiArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

            card.add(infoPanel, BorderLayout.NORTH);
            card.add(isiArea, BorderLayout.CENTER);

            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionPanel.setBackground(Color.WHITE);

            if (!currentUser.getRole().equals("masyarakat")) {
                JButton btnTanggapi = new JButton("Tanggapi");
                btnTanggapi.addActionListener(e -> {
                    new TanggapanView(p).setVisible(true);
                    dispose();
                });
                actionPanel.add(btnTanggapi);
            } else {
                JButton btnDetail = new JButton("Lihat Detail");
                btnDetail.addActionListener(e -> {
                    JTextArea fullText = new JTextArea(p.getIsiLaporan());
                    fullText.setLineWrap(true);
                    fullText.setWrapStyleWord(true);
                    fullText.setEditable(false);
                    JScrollPane scroll = new JScrollPane(fullText);
                    scroll.setPreferredSize(new Dimension(300, 150));
                    JOptionPane.showMessageDialog(this, scroll, "Detail Pengaduan", JOptionPane.INFORMATION_MESSAGE);
                });
                actionPanel.add(btnDetail);
            }
            card.add(actionPanel, BorderLayout.SOUTH);

            cardsPanel.add(card);
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between cards
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

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
        btnLaporan.setEnabled(false);

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

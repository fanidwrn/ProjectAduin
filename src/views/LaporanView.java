package views;

import core.Pengaduan;
import core.Session;
import core.User;
import core.Masyarakat;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LaporanView extends JFrame {
    private User currentUser;

    public LaporanView() {
        currentUser = Session.getCurrentUser();

        setTitle("Laporan Pengaduan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        Color primaryBlue = Color.decode("#1A38A1");

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("Laporan Pengaduan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        // TOP PANEL (HEADER + FILTER)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);

        // FILTER PANEL
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 250, 10, 0));

        String[] statusOptions = {"Status", "Menunggu", "Diproses", "Selesai", "Ditolak"};
        JComboBox<String> statusDropdown = new JComboBox<>(statusOptions);
        statusDropdown.setPreferredSize(new Dimension(110, 32));
        statusDropdown.setBackground(Color.WHITE);
        statusDropdown.setFont(new Font("Arial", Font.PLAIN, 12));
        statusDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        filterPanel.add(statusDropdown);
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // LIST KARTU PENGADUAN
        List<Pengaduan> riwayat;
        if (currentUser.getRole().equals("masyarakat")) {
            Masyarakat m = (Masyarakat) currentUser;
            riwayat = Pengaduan.getRiwayatByNik(m.getNik());
        } else {
            riwayat = Pengaduan.getAllPengaduan();
        }

        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        for (Pengaduan p : riwayat) {
            JPanel card = new JPanel(new GridBagLayout());
            card.setBackground(Color.WHITE);

            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryBlue, 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel idLabel = new JLabel("#" + p.getIdPengaduan());
            idLabel.setFont(new Font("Arial", Font.BOLD, 12));
            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            card.add(idLabel, gbc);

            JLabel statusLabel = new JLabel(p.getStatus());
            statusLabel.setFont(new Font("Arial", Font.BOLD, 12));

            String status = p.getStatus();
            if (status.equalsIgnoreCase("Menunggu") || status.equalsIgnoreCase("Ditolak")) {
                statusLabel.setForeground(new Color(220, 53, 69));
            } else if (status.equalsIgnoreCase("Diproses")) {
                statusLabel.setForeground(new Color(230, 165, 0));
            } else if (status.equalsIgnoreCase("Selesai")) {
                statusLabel.setForeground(new Color(40, 167, 69));
            } else {
                statusLabel.setForeground(Color.BLACK);
            }

            gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            card.add(statusLabel, gbc);

            String dateStr = p.getCreatedAt() != null ? p.getCreatedAt().toString().substring(0, 10) : p.getTglKejadian().toString();
            JLabel dateLabel = new JLabel(dateStr);
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
            gbc.insets = new Insets(2, 0, 10, 0);
            card.add(dateLabel, gbc);

            JLabel catLabel = new JLabel(p.getKategori());
            catLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridy = 2; gbc.insets = new Insets(0, 0, 5, 0);
            card.add(catLabel, gbc);

            String isi = p.getIsiLaporan();
            if (isi.length() > 80) {
                isi = isi.substring(0, 80) + "...";
            }
            JLabel isiLabel = new JLabel("<html><div style='width: 270px;'>" + isi + "</div></html>");
            isiLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            gbc.gridy = 3; gbc.insets = new Insets(0, 0, 15, 0);
            card.add(isiLabel, gbc);

            JButton actionBtn;
            if (!currentUser.getRole().equals("masyarakat")) {
                actionBtn = new JButton("Tanggapi");
                actionBtn.addActionListener(e -> {
                    new TanggapanView(p).setVisible(true);
                    dispose();
                });
            } else {
                actionBtn = new JButton("Lihat Detail");
                actionBtn.addActionListener(e -> {
                    new DetailPengaduanView(p).setVisible(true);
                    dispose();
                });
            }

            actionBtn.setBackground(Color.WHITE);
            actionBtn.setForeground(Color.BLACK);
            actionBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            actionBtn.setFocusPainted(false);
            actionBtn.setContentAreaFilled(false);
            actionBtn.setOpaque(true);
            actionBtn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            actionBtn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    actionBtn.setBackground(new Color(245, 245, 245));
                    actionBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent evt) {
                    actionBtn.setBackground(Color.WHITE);
                }
            });

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            btnPanel.setBackground(Color.WHITE);
            btnPanel.add(actionBtn);

            gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 0, 0);
            card.add(btnPanel, gbc);

            // POSISI KARTU
            JPanel cardWrapper = new JPanel(new BorderLayout());
            cardWrapper.setBackground(Color.WHITE);
            cardWrapper.setMaximumSize(new Dimension(340, Integer.MAX_VALUE));
            cardWrapper.add(card, BorderLayout.NORTH);
            cardWrapper.setName(p.getStatus()); // Add name for filtering

            cardWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

            cardsPanel.add(cardWrapper);
            Component rigidArea = Box.createRigidArea(new Dimension(0, 15));
            ((JComponent)rigidArea).setAlignmentX(Component.LEFT_ALIGNMENT);
            rigidArea.setName("SPACE_" + p.getStatus()); // Add name for filtering
            cardsPanel.add(rigidArea);
        }

        statusDropdown.addActionListener(e -> {
            String selected = (String) statusDropdown.getSelectedItem();
            for (Component comp : cardsPanel.getComponents()) {
                if (comp.getName() == null) continue;
                if (selected.equals("Status")) {
                    comp.setVisible(true);
                } else if (comp.getName().equalsIgnoreCase(selected) || comp.getName().equalsIgnoreCase("SPACE_" + selected)) {
                    comp.setVisible(true);
                } else {
                    comp.setVisible(false);
                }
            }
            cardsPanel.revalidate();
            cardsPanel.repaint();
        });

        JPanel cardsContainer = new JPanel(new BorderLayout());
        cardsContainer.setBackground(Color.WHITE);
        cardsContainer.add(cardsPanel, BorderLayout.NORTH);

        JPanel scrollContent = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                Dimension pref = super.getPreferredSize();
                Container parent = getParent();
                if (parent instanceof JViewport) {
                    pref.width = Math.max(pref.width, parent.getWidth());
                    pref.height = Math.max(pref.height, parent.getHeight());
                }
                return pref;
            }
        };
        scrollContent.setBackground(Color.WHITE);
        scrollContent.add(cardsContainer, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        // NAV BAR
        add(createBottomNavBar(), BorderLayout.SOUTH);
    }

    private JPanel createBottomNavBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setPreferredSize(new Dimension(400, 60));

        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.addActionListener(e -> {
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

        navBar.add(btnDashboard);
        navBar.add(btnLaporan);
        navBar.add(btnProfil);

        return navBar;
    }
}
package views;

import core.Pengaduan;
import core.Petugas;
import core.Session;
import core.Tanggapan;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TanggapanView extends JFrame {

    private Color primaryBlue = Color.decode("#1A38A1");

    public TanggapanView(Pengaduan pengaduan) {
        setTitle("Detail & Tanggapan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("Tanggapan Pengaduan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Text Labels for Details
        addPlainLabel(panel, "ID Pengaduan : " + pengaduan.getIdPengaduan());
        addPlainLabel(panel, "Kategori : " + pengaduan.getKategori());
        addPlainLabel(panel, "Tanggal Kejadian : " + (pengaduan.getTglKejadian() != null ? pengaduan.getTglKejadian().toString() : "-"));
        addPlainLabel(panel, "Lokasi Kejadian : " + pengaduan.getLokasi());

        // Isi Laporan field (read-only)
        JTextField isiLaporanField = new JTextField(pengaduan.getIsiLaporan());
        isiLaporanField.setEditable(false);
        isiLaporanField.setBackground(Color.WHITE);
        isiLaporanField.setFont(new Font("Arial", Font.PLAIN, 13));
        isiLaporanField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Ubah Status
        String[] statusList = {"Menunggu", "Diproses", "Selesai", "Ditolak"};
        JComboBox<String> statusCombo = new JComboBox<>(statusList);
        statusCombo.setSelectedItem(pengaduan.getStatus());
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        statusCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Tanggapan Baru field
        JTextField tanggapanField = new JTextField();
        tanggapanField.setFont(new Font("Arial", Font.PLAIN, 13));
        tanggapanField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        addLabelAndComponent(panel, "Isi Laporan :", isiLaporanField);
        addLabelAndComponent(panel, "Ubah Status :", statusCombo);
        addLabelAndComponent(panel, "Tanggapan Baru :", tanggapanField);

        panel.add(Box.createVerticalStrut(5));

        // Submit Button
        JButton submitButton = new JButton("Simpan Tanggapan");
        submitButton.setBackground(primaryBlue);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.setFocusPainted(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setOpaque(true);
        submitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.setBorder(BorderFactory.createEmptyBorder());

        submitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                submitButton.setBackground(new Color(20, 48, 120));
                submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                submitButton.setBackground(primaryBlue);
            }
        });
        panel.add(submitButton);
        panel.add(Box.createVerticalStrut(10));

        // Cancel Button
        JButton backButton = new JButton("Batal");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setBorder(BorderFactory.createLineBorder(primaryBlue, 1));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(new Color(245, 245, 245));
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(Color.WHITE);
            }
        });
        panel.add(backButton);

        // Put in scroll pane just in case
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Check if there is already a tanggapan
        Tanggapan t = Pengaduan.getTanggapanByPengaduan(pengaduan.getIdPengaduan());
        if (t != null) {
            tanggapanField.setText(t.getTanggapan());
        }

        // Action Listeners
        submitButton.addActionListener(e -> {
            String statusBaru = (String) statusCombo.getSelectedItem();
            String isiTanggapan = tanggapanField.getText();

            if (isiTanggapan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggapan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Petugas user = (Petugas) Session.getCurrentUser();
            Tanggapan tanggapanBaru = new Tanggapan(
                    0,
                    pengaduan.getIdPengaduan(),
                    new Date(System.currentTimeMillis()),
                    isiTanggapan,
                    user.getIdPetugas(),
                    null
            );

            boolean success = Pengaduan.beriTanggapan(tanggapanBaru, statusBaru);
            if (success) {
                JOptionPane.showMessageDialog(this, "Tanggapan berhasil disimpan dan status diubah!");
                new LaporanView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan tanggapan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new LaporanView().setVisible(true);
            dispose();
        });
    }

    private void addPlainLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
    }

    private void addLabelAndComponent(JPanel panel, String labelText, JComponent comp) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));

        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (comp instanceof JTextField) {
            comp.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        } else if (comp instanceof JComboBox) {
            comp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }

        panel.add(comp);
        panel.add(Box.createVerticalStrut(10));
    }
}

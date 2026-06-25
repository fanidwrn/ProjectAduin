package views;

import core.Masyarakat;
import core.Pengaduan;
import core.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormPengaduanView extends JFrame {

    private Color primaryBlue = Color.decode("#1A38A1");

    public FormPengaduanView() {
        setTitle("Form Pengaduan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryBlue);
        headerPanel.setPreferredSize(new Dimension(400, 70));
        JLabel titleLabel = new JLabel("Buat Pengaduan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        String[] kategoriList = {"Sampah", "Ketertiban Umum", "Fasilitas", "Pelayanan Publik", "Lainnya"};
        JComboBox<String> kategoriCombo = new JComboBox<>(kategoriList);
        kategoriCombo.setBackground(Color.WHITE);
        kategoriCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        kategoriCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JTextField tglField = new JTextField();
        tglField.setFont(new Font("Arial", Font.PLAIN, 13));
        tglField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JTextField lokasiField = new JTextField();
        lokasiField.setFont(new Font("Arial", Font.PLAIN, 13));
        lokasiField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JTextField isiField = new JTextField();
        isiField.setFont(new Font("Arial", Font.PLAIN, 13));
        isiField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        addLabelAndComponent(panel, "Kategori :", kategoriCombo);
        addLabelAndComponent(panel, "Tanggal Kejadian (yyyy-mm-dd) :", tglField);
        addLabelAndComponent(panel, "Lokasi Kejadian :", lokasiField);
        addLabelAndComponent(panel, "Isi Laporan :", isiField);

        panel.add(Box.createVerticalStrut(15));

        JButton submitButton = new JButton("Kirim Pengaduan");
        submitButton.setBackground(primaryBlue);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.setFocusPainted(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setOpaque(true);
        submitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
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
        panel.add(Box.createVerticalStrut(15));

        JButton backButton = new JButton("Batal");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
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

        add(panel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            try {
                String kategori = (String) kategoriCombo.getSelectedItem();
                Date tgl = Date.valueOf(tglField.getText());
                String lokasi = lokasiField.getText();
                String isi = isiField.getText();

                if (lokasi.isEmpty() || isi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lokasi dan Isi Laporan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Masyarakat user = (Masyarakat) Session.getCurrentUser();
                Pengaduan pengaduan = new Pengaduan(0, user.getNik(), kategori, tgl, isi, lokasi, "Menunggu", null);

                boolean success = pengaduan.simpan();
                if (success) {
                    JOptionPane.showMessageDialog(this, "Pengaduan berhasil dikirim!");
                    new DashboardView().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengirim pengaduan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });
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
        panel.add(Box.createVerticalStrut(15));
    }
}

package views;

import core.Masyarakat;
import core.Pengaduan;
import core.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class FormPengaduanView extends JFrame {

    public FormPengaduanView() {
        setTitle("Form Pengaduan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Kategori:"));
        String[] kategoriList = {"Kerusakan Jalan", "Sampah", "Ketertiban Umum", "Lampu Jalan", "Pelayanan Publik", "Lainnya"};
        JComboBox<String> kategoriCombo = new JComboBox<>(kategoriList);
        panel.add(kategoriCombo);

        panel.add(new JLabel("Tgl Kejadian (YYYY-MM-DD):"));
        JTextField tglField = new JTextField();
        panel.add(tglField);

        panel.add(new JLabel("Lokasi Kejadian:"));
        JTextField lokasiField = new JTextField();
        panel.add(lokasiField);

        panel.add(new JLabel("Isi Laporan:"));
        JTextArea isiArea = new JTextArea();
        isiArea.setLineWrap(true);
        panel.add(new JScrollPane(isiArea));

        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Kirim Pengaduan");
        JButton backButton = new JButton("Batal");
        actionPanel.add(submitButton);
        actionPanel.add(backButton);
        panel.add(actionPanel);

        add(panel);

        submitButton.addActionListener(e -> {
            try {
                String kategori = (String) kategoriCombo.getSelectedItem();
                Date tgl = Date.valueOf(tglField.getText());
                String lokasi = lokasiField.getText();
                String isi = isiArea.getText();

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
                JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });
    }
}

package views;

import core.Pengaduan;
import core.Petugas;
import core.Session;
import core.Tanggapan;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class TanggapanView extends JFrame {

    public TanggapanView(Pengaduan pengaduan) {
        setTitle("Detail & Tanggapan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel detailPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        detailPanel.add(new JLabel("ID Pengaduan: " + pengaduan.getIdPengaduan()));
        detailPanel.add(new JLabel("Kategori: " + pengaduan.getKategori()));
        detailPanel.add(new JLabel("Lokasi: " + pengaduan.getLokasi()));
        detailPanel.add(new JLabel("Isi Laporan:"));
        panel.add(detailPanel, BorderLayout.NORTH);

        JTextArea isiLaporanArea = new JTextArea(pengaduan.getIsiLaporan());
        isiLaporanArea.setEditable(false);
        isiLaporanArea.setLineWrap(true);
        panel.add(new JScrollPane(isiLaporanArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Ubah Status:"));
        String[] statusList = {"Menunggu", "Diproses", "Selesai", "Ditolak"};
        JComboBox<String> statusCombo = new JComboBox<>(statusList);
        statusCombo.setSelectedItem(pengaduan.getStatus());
        statusPanel.add(statusCombo);
        inputPanel.add(statusPanel, BorderLayout.NORTH);

        JPanel tanggapanPanel = new JPanel(new BorderLayout());
        tanggapanPanel.add(new JLabel("Tanggapan Baru:"), BorderLayout.NORTH);
        JTextArea tanggapanArea = new JTextArea(4, 20);
        tanggapanArea.setLineWrap(true);
        tanggapanPanel.add(new JScrollPane(tanggapanArea), BorderLayout.CENTER);
        inputPanel.add(tanggapanPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Simpan Tanggapan");
        JButton backButton = new JButton("Kembali");
        actionPanel.add(submitButton);
        actionPanel.add(backButton);
        inputPanel.add(actionPanel, BorderLayout.SOUTH);

        panel.add(inputPanel, BorderLayout.SOUTH);
        add(panel);

        Tanggapan t = Pengaduan.getTanggapanByPengaduan(pengaduan.getIdPengaduan());
        if (t != null) {
            tanggapanArea.setText(t.getTanggapan());
        }

        submitButton.addActionListener(e -> {
            String statusBaru = (String) statusCombo.getSelectedItem();
            String isiTanggapan = tanggapanArea.getText();

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
}

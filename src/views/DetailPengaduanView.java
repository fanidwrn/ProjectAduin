package views;

import core.Pengaduan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DetailPengaduanView extends JFrame {
    private Pengaduan pengaduan;

    public DetailPengaduanView(Pengaduan pengaduan) {
        this.pengaduan = pengaduan;

        setTitle("Detail Pengaduan - Adu.in");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#1E3A8A")); // Dark blue
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("Detail Pengaduan", SwingConstants.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        addLabelField(contentPanel, "ID Pengaduan : " + pengaduan.getIdPengaduan());
        addLabelField(contentPanel, "Status : " + pengaduan.getStatus());
        addLabelField(contentPanel, "Kategori : " + pengaduan.getKategori());
        addLabelField(contentPanel, "Tanggal Kejadian : " + (pengaduan.getTglKejadian() != null ? pengaduan.getTglKejadian().toString() : "-"));
        addLabelField(contentPanel, "Lokasi Kejadian : " + (pengaduan.getLokasi() != null ? pengaduan.getLokasi() : "-"));

        contentPanel.add(Box.createVerticalStrut(5));

        // Isi Laporan
        JLabel isiLabel = new JLabel("Isi Laporan :");
        isiLabel.setFont(new Font("", Font.PLAIN, 12));
        isiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(isiLabel);

        contentPanel.add(Box.createVerticalStrut(5));

        JTextArea isiArea = new JTextArea(pengaduan.getIsiLaporan());
        isiArea.setWrapStyleWord(true);
        isiArea.setLineWrap(true);
        isiArea.setEditable(false);
        isiArea.setFont(new Font("", Font.PLAIN, 12));
        isiArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane isiScroll = new JScrollPane(isiArea);
        isiScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        isiScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Strict height restriction to prevent it from pushing the button off screen
        isiScroll.setPreferredSize(new Dimension(340, 150));
        isiScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        contentPanel.add(isiScroll);

        contentPanel.add(Box.createVerticalGlue()); // Push button to bottom
        contentPanel.add(Box.createVerticalStrut(15));

        // Button Kembali
        JButton kembaliBtn = new JButton("Kembali");
        kembaliBtn.setBackground(Color.WHITE);
        kembaliBtn.setForeground(Color.BLACK);
        kembaliBtn.setFocusPainted(false);
        kembaliBtn.setFont(new Font("", Font.PLAIN, 12));
        kembaliBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#1E3A8A")),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        kembaliBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        kembaliBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        kembaliBtn.addActionListener(e -> {
            new LaporanView().setVisible(true);
            dispose();
        });

        contentPanel.add(kembaliBtn);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addLabelField(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("", Font.PLAIN, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
    }
}

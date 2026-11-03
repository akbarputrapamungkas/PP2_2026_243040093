package PP2_P09_243040093;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.swing.*;

public class FormPendaftaran {

    // Komponen Global untuk Navigasi CardLayout & Retensi Data
    private static CardLayout cardLayout;
    private static JPanel panelContainer;
    
    // Komponen Form
    private static JTextField txtNama, txtEmail, txtNoHP, txtUmur;
    private static JComboBox<String> cmbKategori;
    private static JRadioButton rbRegular, rbVIP;
    private static JCheckBox chkSetuju;
    private static JTextArea txtCatatan;
    
    // Label Ringkasan
    private static JLabel lblValNama, lblValEmail, lblValNoHP, lblValUmur, lblValKategori, lblValTipe, lblValCatatan;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormPendaftaran::buatDanTampilkanGUI);
    }

    private static void buatDanTampilkanGUI() {
        JFrame frame = new JFrame("Aplikasi Pendaftaran Kegiatan - Tugas 2");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Ditangani oleh WindowListener untuk konfirmasi keluar

        // Konfirmasi sebelum keluar via tombol close (X) window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prosesKeluar(frame);
            }
        });

        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        // Membuat Panel untuk Tiap Halaman
        JPanel halamanForm = buatHalamanForm(frame);
        JPanel halamanRingkasan = buatHalamanRingkasan();
        JPanel halamanBantuan = buatHalamanBantuan();

        panelContainer.add(halamanForm, "FORM");
        panelContainer.add(halamanRingkasan, "RINGKASAN");
        panelContainer.add(halamanBantuan, "BANTUAN");

        frame.setContentPane(panelContainer);
        frame.setMinimumSize(new Dimension(500, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // --- HALAMAN 1: FORMULIR PENDAFTARAN ---
    private static JPanel buatHalamanForm(JFrame frame) {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Panel Identitas (Ditambah Umur)
        JPanel panelIdentitas = new JPanel(new GridLayout(4, 2, 8, 8));
        panelIdentitas.setBorder(BorderFactory.createTitledBorder("Identitas Pendaftar"));

        txtNama = new JTextField(20);
        txtEmail = new JTextField(20);
        txtNoHP = new JTextField(20);
        txtUmur = new JTextField(20);

        panelIdentitas.add(new JLabel("Nama Lengkap:"));
        panelIdentitas.add(txtNama);
        panelIdentitas.add(new JLabel("Email:"));
        panelIdentitas.add(txtEmail);
        panelIdentitas.add(new JLabel("Nomor HP/WA:"));
        panelIdentitas.add(txtNoHP);
        panelIdentitas.add(new JLabel("Umur (15-100):"));
        panelIdentitas.add(txtUmur);

        // Panel Pilihan Kegiatan
        JPanel panelPilihan = new JPanel(new GridLayout(3, 2, 8, 8));
        panelPilihan.setBorder(BorderFactory.createTitledBorder("Pilihan Kegiatan"));

        String[] daftarKategori = {"-- Pilih Kategori --", "Mahasiswa", "Umum", "Pelajar", "Alumni"};
        cmbKategori = new JComboBox<>(daftarKategori);

        rbRegular = new JRadioButton("Reguler", true);
        rbVIP = new JRadioButton("VIP");
        ButtonGroup bgTipe = new ButtonGroup();
        bgTipe.add(rbRegular);
        bgTipe.add(rbVIP);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRadio.add(rbRegular);
        panelRadio.add(rbVIP);

        chkSetuju = new JCheckBox("Saya menyetujui syarat & ketentuan");

        panelPilihan.add(new JLabel("Kategori Peserta:"));
        panelPilihan.add(cmbKategori);
        panelPilihan.add(new JLabel("Tipe Kepesertaan:"));
        panelPilihan.add(panelRadio);
        panelPilihan.add(new JLabel("Konfirmasi:"));
        panelPilihan.add(chkSetuju);

        JPanel panelFormAtas = new JPanel(new BorderLayout(8, 8));
        panelFormAtas.add(panelIdentitas, BorderLayout.NORTH);
        panelFormAtas.add(panelPilihan, BorderLayout.CENTER);

        // Panel Catatan
        JPanel panelCatatan = new JPanel(new BorderLayout(5, 5));
        panelCatatan.setBorder(BorderFactory.createTitledBorder("Catatan / Alasan Mengikuti"));

        txtCatatan = new JTextArea(3, 20);
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        JScrollPane scrollCatatan = new JScrollPane(txtCatatan);
        panelCatatan.add(scrollCatatan, BorderLayout.CENTER);

        // Panel Tombol Navigasi & Aksi
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnBantuan = new JButton("Bantuan");
        JButton btnReset = new JButton("Reset");
        JButton btnDaftar = new JButton("Simpan & Lihat");
        JButton btnKeluar = new JButton("Keluar");

        panelTombol.add(btnBantuan);
        panelTombol.add(btnReset);
        panelTombol.add(btnDaftar);
        panelTombol.add(btnKeluar);

        // Aksi Tombol Bantuan (Navigasi ke Bantuan)
        btnBantuan.addActionListener(e -> cardLayout.show(panelContainer, "BANTUAN"));

        // Aksi Tombol Reset
        btnReset.addActionListener(e -> resetForm());

        // Aksi Tombol Keluar
        btnKeluar.addActionListener(e -> prosesKeluar(frame));

        // Aksi Tombol Daftar (Validasi Ketat & Simpan ke File & Navigasi ke Ringkasan)
        btnDaftar.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String email = txtEmail.getText().trim();
            String noHP = txtNoHP.getText().trim();
            String strUmur = txtUmur.getText().trim();
            String kategori = (String) cmbKategori.getSelectedItem();
            String tipe = rbRegular.isSelected() ? "Reguler" : "VIP";
            String catatan = txtCatatan.getText().trim();

            // 1. Validasi Nama Wajib Diisi
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nama lengkap wajib diisi!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
                return;
            }

            // 2. Validasi Email Berformat Wajar (menggunakan regex)
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (email.isEmpty() || !Pattern.matches(emailRegex, email)) {
                JOptionPane.showMessageDialog(frame, "Format email tidak valid (contoh: nama@email.com)!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                txtEmail.requestFocus();
                return;
            }

            // 3. Validasi Nomor HP
            if (noHP.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nomor HP/WA wajib diisi!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                txtNoHP.requestFocus();
                return;
            }

            // 4. Validasi Umur (Bilangan bulat 15-100)
            int umur = 0;
            try {
                umur = Integer.parseInt(strUmur);
                if (umur < 15 || umur > 100) {
                    JOptionPane.showMessageDialog(frame, "Umur harus berupa angka antara 15 sampai 100 tahun!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                    txtUmur.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Umur harus berupa angka bilangan bulat!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                txtUmur.requestFocus();
                return;
            }

            // 5. Validasi Pilihan Kegiatan (Kategori belum dipilih)
            if (cmbKategori.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(frame, "Silakan pilih kategori peserta terlebih dahulu!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                cmbKategori.requestFocus();
                return;
            }

            // 6. Validasi Persetujuan Syarat & Ketentuan
            if (!chkSetuju.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Anda harus menyetujui syarat & ketentuan!", "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
                chkSetuju.requestFocus();
                return;
            }

            // Menyimpan ke file teks lokal (append mode)
            try (PrintWriter out = new PrintWriter(new FileWriter("data_pendaftaran.txt", true))) {
                out.println("--- Data Pendaftaran ---");
                out.println("Nama    : " + nama);
                out.println("Email   : " + email);
                out.println("No HP   : " + noHP);
                out.println("Umur    : " + umur);
                out.println("Kategori: " + kategori);
                out.println("Tipe    : " + tipe);
                out.println("Catatan : " + catatan);
                out.println("------------------------");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal menyimpan ke berkas: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Set data ke halaman ringkasan
            lblValNama.setText(": " + nama);
            lblValEmail.setText(": " + email);
            lblValNoHP.setText(": " + noHP);
            lblValUmur.setText(": " + umur + " Tahun");
            lblValKategori.setText(": " + kategori);
            lblValTipe.setText(": " + tipe);
            lblValCatatan.setText(": " + (catatan.isEmpty() ? "-" : catatan));

            // Pindah ke halaman Ringkasan menggunakan CardLayout
            cardLayout.show(panelContainer, "RINGKASAN");
        });

        JPanel panelTengah = new JPanel(new BorderLayout(8, 8));
        panelTengah.add(panelFormAtas, BorderLayout.NORTH);
        panelTengah.add(panelCatatan, BorderLayout.CENTER);

        panelUtama.add(panelTengah, BorderLayout.CENTER);
        panelUtama.add(panelTombol, BorderLayout.SOUTH);

        return panelUtama;
    }

    // --- HALAMAN 2: RINGKASAN DATA ---
    private static JPanel buatHalamanRingkasan() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblJudul = new JLabel("Ringkasan Pendaftaran Berhasil", JLabel.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblJudul, BorderLayout.NORTH);

        JPanel panelGrid = new JPanel(new GridLayout(7, 2, 8, 8));
        panelGrid.setBorder(BorderFactory.createTitledBorder("Detail Data Anda"));

        lblValNama = new JLabel();
        lblValEmail = new JLabel();
        lblValNoHP = new JLabel();
        lblValUmur = new JLabel();
        lblValKategori = new JLabel();
        lblValTipe = new JLabel();
        lblValCatatan = new JLabel();

        panelGrid.add(new JLabel("Nama Lengkap")); panelGrid.add(lblValNama);
        panelGrid.add(new JLabel("Email")); panelGrid.add(lblValEmail);
        panelGrid.add(new JLabel("Nomor HP/WA")); panelGrid.add(lblValNoHP);
        panelGrid.add(new JLabel("Umur")); panelGrid.add(lblValUmur);
        panelGrid.add(new JLabel("Kategori Peserta")); panelGrid.add(lblValKategori);
        panelGrid.add(new JLabel("Tipe Kepesertaan")); panelGrid.add(lblValTipe);
        panelGrid.add(new JLabel("Catatan")); panelGrid.add(lblValCatatan);

        panel.add(panelGrid, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnKembali = new JButton("Kembali ke Form");
        
        // Aksi tombol Kembali (Data form tetap aman/tidak hilang total kecuali di-reset manual)
        btnKembali.addActionListener(e -> cardLayout.show(panelContainer, "FORM"));
        
        panelBawah.add(btnKembali);
        panel.add(panelBawah, BorderLayout.SOUTH);

        return panel;
    }

    // --- HALAMAN 3: BANTUAN ---
    private static JPanel buatHalamanBantuan() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblJudul = new JLabel("Pusat Bantuan Pendaftaran", JLabel.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblJudul, BorderLayout.NORTH);

        JTextArea txtBantuan = new JTextArea(
            "Panduan Pengisian Formulir:\n\n" +
            "1. Masukkan Nama Lengkap sesuai identitas resmi.\n" +
            "2. Masukkan Email aktif yang berformat benar (contoh: user@mail.com).\n" +
            "3. Masukkan Nomor HP/WhatsApp yang bisa dihubungi.\n" +
            "4. Masukkan Umur anda berupa angka bulat antara 15 hingga 100 tahun.\n" +
            "5. Pilih Kategori Peserta (Mahasiswa, Umum, Pelajar, atau Alumni).\n" +
            "6. Pilih Tipe Kepesertaan (Reguler atau VIP).\n" +
            "7. Centang persetujuan syarat & ketentuan sebelum menyimpan data.\n" +
            "8. Data yang disimpan akan tercatat secara lokal ke dalam berkas teks."
        );
        txtBantuan.setEditable(false);
        txtBantuan.setLineWrap(true);
        txtBantuan.setWrapStyleWord(true);
        panel.add(new JScrollPane(txtBantuan), BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnKembali = new JButton("Kembali ke Form");
        btnKembali.addActionListener(e -> cardLayout.show(panelContainer, "FORM"));
        panelBawah.add(btnKembali);
        
        panel.add(panelBawah, BorderLayout.SOUTH);

        return panel;
    }

    // Fungsi Reset Form
    private static void resetForm() {
        txtNama.setText("");
        txtEmail.setText("");
        txtNoHP.setText("");
        txtUmur.setText("");
        txtCatatan.setText("");
        chkSetuju.setSelected(false);
        rbRegular.setSelected(true);
        cmbKategori.setSelectedIndex(0);
        txtNama.requestFocus();
    }

    // Fungsi Konfirmasi Sebelum Keluar Aplikasi
    private static void prosesKeluar(JFrame frame) {
        int pilihan = JOptionPane.showConfirmDialog(
            frame, 
            "Apakah Anda yakin ingin keluar dari aplikasi?", 
            "Konfirmasi Keluar", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

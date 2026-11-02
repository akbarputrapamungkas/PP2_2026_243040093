package PP2_P08_243040093;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FormPendaftaran {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(FormPendaftaran::buatDanTampilkanGUI);
    }

    private static void buatDanTampilkanGUI() {
        JFrame frame = new JFrame("Formulir Pendaftaran Kegiatan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Panel Identitas
        JPanel panelIdentitas = new JPanel(new GridLayout(3, 2, 8, 8));
        panelIdentitas.setBorder(BorderFactory.createTitledBorder("Identitas Pendaftar"));

        JTextField txtNama = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtNoHP = new JTextField(20);

        panelIdentitas.add(new JLabel("Nama Lengkap:"));
        panelIdentitas.add(txtNama);
        panelIdentitas.add(new JLabel("Email:"));
        panelIdentitas.add(txtEmail);
        panelIdentitas.add(new JLabel("Nomor HP/WA:"));
        panelIdentitas.add(txtNoHP);

        // Panel Pilihan Kegiatan
        JPanel panelPilihan = new JPanel(new GridLayout(3, 2, 8, 8));
        panelPilihan.setBorder(BorderFactory.createTitledBorder("Pilihan Kegiatan"));

        String[] daftarKategori = {"Mahasiswa", "Umum", "Pelajar", "Alumni"};
        JComboBox<String> cmbKategori = new JComboBox<>(daftarKategori);

        JRadioButton rbRegular = new JRadioButton("Reguler", true);
        JRadioButton rbVIP = new JRadioButton("VIP");
        ButtonGroup bgTipe = new ButtonGroup();
        bgTipe.add(rbRegular);
        bgTipe.add(rbVIP);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRadio.add(rbRegular);
        panelRadio.add(rbVIP);

        JCheckBox chkSetuju = new JCheckBox("Saya menyetujui syarat & ketentuan");

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

        JTextArea txtCatatan = new JTextArea(4, 20);
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        JScrollPane scrollCatatan = new JScrollPane(txtCatatan);

        panelCatatan.add(scrollCatatan, BorderLayout.CENTER);

        // Panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnBatal = new JButton("Batal");
        JButton btnDaftar = new JButton("Daftar");

        panelTombol.add(btnBatal);
        panelTombol.add(btnDaftar);

        // Aksi Tombol Daftar (Menyimpan data ke berkas teks lokal di direktori proyek Eclipse)
        btnDaftar.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String email = txtEmail.getText().trim();
            String noHP = txtNoHP.getText().trim();
            String kategori = (String) cmbKategori.getSelectedItem();
            String tipe = rbRegular.isSelected() ? "Reguler" : "VIP";
            String catatan = txtCatatan.getText().trim();

            // Validasi sederhana
            if (nama.isEmpty() || email.isEmpty() || noHP.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nama, Email, dan Nomor HP wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!chkSetuju.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Anda harus menyetujui syarat & ketentuan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Menyimpan ke file text "data_pendaftaran.txt" (parameter true membuat data bertambah / append)
            try (PrintWriter out = new PrintWriter(new FileWriter("data_pendaftaran.txt", true))) {
                out.println("--- Data Pendaftaran ---");
                out.println("Nama    : " + nama);
                out.println("Email   : " + email);
                out.println("No HP   : " + noHP);
                out.println("Kategori: " + kategori);
                out.println("Tipe    : " + tipe);
                out.println("Catatan : " + catatan);
                out.println("------------------------");
                
                JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form setelah berhasil disimpan
                txtNama.setText("");
                txtEmail.setText("");
                txtNoHP.setText("");
                txtCatatan.setText("");
                chkSetuju.setSelected(false);
                rbRegular.setSelected(true);
                cmbKategori.setSelectedIndex(0);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aksi Tombol Batal
        btnBatal.addActionListener(e -> {
            txtNama.setText("");
            txtEmail.setText("");
            txtNoHP.setText("");
            txtCatatan.setText("");
            chkSetuju.setSelected(false);
            rbRegular.setSelected(true);
            cmbKategori.setSelectedIndex(0);
        });

        JPanel panelTengah = new JPanel(new BorderLayout(8, 8));
        panelTengah.add(panelFormAtas, BorderLayout.NORTH);
        panelTengah.add(panelCatatan, BorderLayout.CENTER);

        panelUtama.add(panelTengah, BorderLayout.CENTER);
        panelUtama.add(panelTombol, BorderLayout.SOUTH);

        frame.setContentPane(panelUtama);
        frame.setMinimumSize(new Dimension(450, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }public static void main(String[] args) {
        SwingUtilities.invokeLater(FormPendaftaran::buatDanTampilkanGUI);
    }

    private static void buatDanTampilkanGUI() {
        JFrame frame = new JFrame("Formulir Pendaftaran Kegiatan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Panel Identitas
        JPanel panelIdentitas = new JPanel(new GridLayout(3, 2, 8, 8));
        panelIdentitas.setBorder(BorderFactory.createTitledBorder("Identitas Pendaftar"));

        JTextField txtNama = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtNoHP = new JTextField(20);

        panelIdentitas.add(new JLabel("Nama Lengkap:"));
        panelIdentitas.add(txtNama);
        panelIdentitas.add(new JLabel("Email:"));
        panelIdentitas.add(txtEmail);
        panelIdentitas.add(new JLabel("Nomor HP/WA:"));
        panelIdentitas.add(txtNoHP);

        // Panel Pilihan Kegiatan
        JPanel panelPilihan = new JPanel(new GridLayout(3, 2, 8, 8));
        panelPilihan.setBorder(BorderFactory.createTitledBorder("Pilihan Kegiatan"));

        String[] daftarKategori = {"Mahasiswa", "Umum", "Pelajar", "Alumni"};
        JComboBox<String> cmbKategori = new JComboBox<>(daftarKategori);

        JRadioButton rbRegular = new JRadioButton("Reguler", true);
        JRadioButton rbVIP = new JRadioButton("VIP");
        ButtonGroup bgTipe = new ButtonGroup();
        bgTipe.add(rbRegular);
        bgTipe.add(rbVIP);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRadio.add(rbRegular);
        panelRadio.add(rbVIP);

        JCheckBox chkSetuju = new JCheckBox("Saya menyetujui syarat & ketentuan");

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

        JTextArea txtCatatan = new JTextArea(4, 20);
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        JScrollPane scrollCatatan = new JScrollPane(txtCatatan);

        panelCatatan.add(scrollCatatan, BorderLayout.CENTER);

        // Panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnBatal = new JButton("Batal");
        JButton btnDaftar = new JButton("Daftar");

        panelTombol.add(btnBatal);
        panelTombol.add(btnDaftar);

        // Aksi Tombol Daftar (Menyimpan data ke berkas teks lokal di direktori proyek Eclipse)
        btnDaftar.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String email = txtEmail.getText().trim();
            String noHP = txtNoHP.getText().trim();
            String kategori = (String) cmbKategori.getSelectedItem();
            String tipe = rbRegular.isSelected() ? "Reguler" : "VIP";
            String catatan = txtCatatan.getText().trim();

            // Validasi sederhana
            if (nama.isEmpty() || email.isEmpty() || noHP.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nama, Email, dan Nomor HP wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!chkSetuju.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Anda harus menyetujui syarat & ketentuan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Menyimpan ke file text "data_pendaftaran.txt" (parameter true membuat data bertambah / append)
            try (PrintWriter out = new PrintWriter(new FileWriter("data_pendaftaran.txt", true))) {
                out.println("--- Data Pendaftaran ---");
                out.println("Nama    : " + nama);
                out.println("Email   : " + email);
                out.println("No HP   : " + noHP);
                out.println("Kategori: " + kategori);
                out.println("Tipe    : " + tipe);
                out.println("Catatan : " + catatan);
                out.println("------------------------");
                
                JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form setelah berhasil disimpan
                txtNama.setText("");
                txtEmail.setText("");
                txtNoHP.setText("");
                txtCatatan.setText("");
                chkSetuju.setSelected(false);
                rbRegular.setSelected(true);
                cmbKategori.setSelectedIndex(0);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aksi Tombol Batal
        btnBatal.addActionListener(e -> {
            txtNama.setText("");
            txtEmail.setText("");
            txtNoHP.setText("");
            txtCatatan.setText("");
            chkSetuju.setSelected(false);
            rbRegular.setSelected(true);
            cmbKategori.setSelectedIndex(0);
        });

        JPanel panelTengah = new JPanel(new BorderLayout(8, 8));
        panelTengah.add(panelFormAtas, BorderLayout.NORTH);
        panelTengah.add(panelCatatan, BorderLayout.CENTER);

        panelUtama.add(panelTengah, BorderLayout.CENTER);
        panelUtama.add(panelTombol, BorderLayout.SOUTH);

        frame.setContentPane(panelUtama);
        frame.setMinimumSize(new Dimension(450, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
}

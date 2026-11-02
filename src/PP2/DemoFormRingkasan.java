package PP2;

import java.awt.BorderLayout; 
import java.awt.CardLayout; 
import java.awt.GridLayout; 
import javax.swing.BorderFactory; 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.JTextArea; 
import javax.swing.JTextField; 
import javax.swing.SwingUtilities; 
 
public class DemoFormRingkasan { 
    private static final String FORM = "FORM"; 
    private static final String RINGKASAN = "RINGKASAN"; 
    private static final int BATAS_STOK = 100_000; 
 
    private final JFrame frame = new JFrame("Data Barang"); 
    private final CardLayout navigasi = new CardLayout(); 
    private final JPanel kartu = new JPanel(navigasi); 
    private final JTextField namaField = new JTextField(24); 
    private final JTextField stokField = new JTextField(24); 
    private final JLabel pesanLabel = new JLabel(" "); 
    private final JTextArea ringkasanArea = new JTextArea(7, 42); 
 
    private DemoFormRingkasan() { 
        kartu.add(buatPanelForm(), FORM); 
        kartu.add(buatPanelRingkasan(), RINGKASAN); 
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setContentPane(kartu); 
        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        navigasi.show(kartu, FORM); 
    } 
 
    private JPanel buatPanelForm() { 
        JPanel fieldPanel = new JPanel(new GridLayout(2, 2, 8, 8)); 
        fieldPanel.add(new JLabel("Nama barang:")); 
        fieldPanel.add(namaField); 
        fieldPanel.add(new JLabel("Stok awal:")); 
        fieldPanel.add(stokField); 
 
        JButton lihatButton = new JButton("Lihat ringkasan"); 
        lihatButton.addActionListener(event -> tampilkanRingkasan()); 
 
        JPanel bawahPanel = new JPanel(new BorderLayout(8, 8)); 
        bawahPanel.add(pesanLabel, BorderLayout.CENTER); 
        bawahPanel.add(lihatButton, BorderLayout.SOUTH); 
 
        JPanel panel = new JPanel(new BorderLayout(10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); 
        panel.add(fieldPanel, BorderLayout.NORTH); 
        panel.add(bawahPanel, BorderLayout.SOUTH); 
        return panel; 
    } 
 
    private JPanel buatPanelRingkasan() { 
        ringkasanArea.setEditable(false); 
        ringkasanArea.setLineWrap(true); 
        ringkasanArea.setWrapStyleWord(true); 
 
        JButton kembaliButton = new JButton("Kembali ke form"); 
        kembaliButton.addActionListener(event -> { 
            navigasi.show(kartu, FORM); 
            namaField.requestFocusInWindow(); 
        }); 
 
        JPanel panel = new JPanel(new BorderLayout(10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); 
        panel.add(new JLabel("Ringkasan data"), BorderLayout.NORTH); 
        panel.add(new JScrollPane(ringkasanArea), BorderLayout.CENTER); 
        panel.add(kembaliButton, BorderLayout.SOUTH); 
        return panel; 
    } 
 
    private void tampilkanRingkasan() { 
        // 1. Baca seluruh masukan sebelum memperbarui hasil. 
        String nama = namaField.getText().trim(); 
        String teksStok = stokField.getText().trim(); 
 
        // 2. Validasi field kosong, konversi, dan rentang. 
        if (nama.isEmpty()) { 
            tampilkanKesalahan( 
                    "Nama barang wajib diisi. Masukkan nama barang.", 
                    namaField); 
            return; 
        } 
 
        if (teksStok.isEmpty()) { 
            tampilkanKesalahan( 
                    "Stok wajib diisi. Masukkan bilangan bulat 0-100000.", 
                    stokField); 
            return; 
        } 
 
        final int stok; 
        try { 
            stok = Integer.parseInt(teksStok); 
        } catch (NumberFormatException e) { 
            tampilkanKesalahan( 
                    "Stok harus bilangan bulat 0-100000. Contoh: 25.", 
                    stokField); 
            return; 
        } 
 
        if (stok < 0 || stok > BATAS_STOK) { 
            tampilkanKesalahan( 
                    "Stok harus antara 0 dan 100000. Perbaiki nilainya.", 
                    stokField); 
            return; 
        } 
 
        // 3. Bentuk hasil hanya setelah semua masukan sah. 
        String ringkasan = "Nama barang: " + nama 
                + "\nStok awal: " + stok 
                + "\n\nRingkasan hanya berada di memori." 
                + "\nBelum ada penyimpanan ke berkas atau basis data."; 
 
        // 4. Perbarui hasil dan navigasi sebagai tanggapan keberhasilan. 
        ringkasanArea.setText(ringkasan); 
        ringkasanArea.setCaretPosition(0); 
        pesanLabel.setText(" "); 
        navigasi.show(kartu, RINGKASAN); 
    } 
 
    private void tampilkanKesalahan(String pesan, JTextField field) { 
        pesanLabel.setText(pesan); 
        field.requestFocusInWindow(); 
        field.selectAll(); 
    } 
 
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> { 
            DemoFormRingkasan aplikasi = new DemoFormRingkasan(); 
aplikasi.frame.setVisible(true); 
}); 
} 
}

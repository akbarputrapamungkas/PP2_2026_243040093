package PP2_P09_243040093;

import java.awt.*;
import javax.swing.*;
import javax.swing.*;
/**
 * @author wdgus
 */
public class main {
    static void tampil() {
        JFrame f = new JFrame("Form inventori");
        CardLayout layout = new CardLayout();
        JPanel kartu = new JPanel(layout);
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField nama = new JTextField(16), stok = new JTextField();
        JButton tinjau = new JButton("Tinjau");
        
        form.add(new JLabel("Nama")); form.add(nama);
        form.add(new JLabel("Stok")); form.add(stok);
        form.add(new JLabel("Belum disimpan")); form.add(tinjau);
        
        JPanel ringkas = new JPanel(new BorderLayout());
        JLabel hasil = new JLabel();
        JButton kembali = new JButton("Kembali");
        
        ringkas.add(hasil); 
        ringkas.add(kembali, BorderLayout.SOUTH);
        kartu.add(form, "form"); 
        kartu.add(ringkas, "ringkas");

        tinjau.addActionListener(e -> {
            try {
                int nilai = Integer.parseInt(stok.getText().trim());
                if (nama.getText().isBlank() || nilai < 0)
                    throw new IllegalArgumentException();
                hasil.setText(nama.getText().trim() + ": " + nilai);
                layout.show(kartu, "ringkas");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(f,
                    "Nama wajib diisi; stok bilangan bulat >= 0");
            }
        });
        
        kembali.addActionListener(e -> layout.show(kartu, "form"));
        
        f.add(kartu); 
        f.pack(); 
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(main::tampil);
    }
}

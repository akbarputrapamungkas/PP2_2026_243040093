package PP2;

import java.awt.BorderLayout; 
import java.awt.GridLayout; 
import javax.swing.BorderFactory; 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JTextField; 
import javax.swing.SwingUtilities;

public class FormBarang {

	 public static void main(String[] args) { 
	        SwingUtilities.invokeLater(FormBarang::buatDanTampilkanGUI); 
	    } 
	 
	    private static void buatDanTampilkanGUI() { 
	        JFrame frame = new JFrame("Form Barang"); 
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	 
	        JTextField namaField = new JTextField(20); 
	        JTextField stokField = new JTextField(20); 
	        JLabel statusLabel = new JLabel("Isi nama dan stok awal."); 
	        JButton simpanButton = new JButton("Simpan"); 
	 
	        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8)); 
	        formPanel.add(new JLabel("Nama barang:")); 
	        formPanel.add(namaField); 
	        formPanel.add(new JLabel("Stok awal:")); 
	        formPanel.add(stokField); 
	 
	        JPanel tindakanPanel = new JPanel(new BorderLayout(8, 0)); 
	        tindakanPanel.add(statusLabel, BorderLayout.CENTER); 
	        tindakanPanel.add(simpanButton, BorderLayout.EAST); 
	 
	        JPanel utamaPanel = new JPanel(new BorderLayout(10, 10)); 
	        utamaPanel.setBorder( 
	                BorderFactory.createEmptyBorder(12, 12, 12, 12)); 
	        utamaPanel.add(formPanel, BorderLayout.CENTER); 
	        utamaPanel.add(tindakanPanel, BorderLayout.SOUTH); 
	 
	        simpanButton.addActionListener(event -> { 
	            String nama = namaField.getText().trim(); 
	            String teksStok = stokField.getText().trim(); 
	 
	            if (nama.isEmpty()) { 
	                statusLabel.setText("Nama wajib diisi."); 
	                namaField.requestFocusInWindow(); 
	                return; 
	            } 
	 
	            try { 
	                int stok = Integer.parseInt(teksStok); 
	 
	                if (stok < 0) { 
	                    statusLabel.setText("Stok tidak boleh negatif."); 
	                    return; 
	                } 
	 
	                // Pada aplikasi lengkap, panggil service di sini. 
	                statusLabel.setText("Data valid. Stok: " + stok); 
	            } catch (NumberFormatException e) { 
	                statusLabel.setText("Stok harus bilangan int."); 
	                stokField.requestFocusInWindow(); 
	            } 
	        }); 
	 
	        frame.setContentPane(utamaPanel); 
	        frame.pack(); 
	        frame.setLocationRelativeTo(null); 
	        frame.setVisible(true); 
	    }
	
}

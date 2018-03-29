package Stat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Adsbihm {

	private JFrame frame;
	private JTextField txtUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Adsbihm window = new Adsbihm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Adsbihm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(321, 12, 117, 25);
		panel.add(btnStop);
		
		JLabel lblTotdata = new JLabel("Totdata");
		lblTotdata.setBounds(339, 143, 70, 15);
		panel.add(lblTotdata);
		
		txtUrl = new JTextField();
		txtUrl.setText("Url");
		txtUrl.setBounds(12, 0, 114, 19);
		panel.add(txtUrl);
		txtUrl.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 54, 282, 200);
		panel.add(textPane);
	}
}

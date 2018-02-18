package adsb;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class frame implements ActionListener{

	private JFrame frame;
	private JTextPane textPane;
	private JTextPane textPane_1 ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame window = new frame();
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
	public frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton Recuperer = new JButton("Recuperer");
		Recuperer.setBounds(204, 43, 117, 25);
		frame.getContentPane().add(Recuperer);
		Recuperer.addActionListener(this);
		
		
		textPane = new JTextPane();
		textPane.setBounds(12, 43, 127, 25);
		frame.getContentPane().add(textPane);
		
		JLabel lblAdresseDonneeBrut = new JLabel("Adresse donnee brut");
		lblAdresseDonneeBrut.setBounds(12, 24, 256, 25);
		frame.getContentPane().add(lblAdresseDonneeBrut);
		
		textPane_1 = new JTextPane();
		textPane_1.setBounds(31, 107, 407, 139);
		frame.getContentPane().add(textPane_1);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{Recuperer, textPane}));
	}






	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		
		
		
	}
}

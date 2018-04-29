import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class REQUETE4D {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField longitude_lo;
	private JTextField longitude_hi;
	private JTextField latitude_lo;
	private JTextField latitude_hi;
	private JTextField altitude_lo;
	private JTextField altitude_hi;
	private JTextField Date_lo;
	private JTextField Date_hi;

	
	
	//private OSKY_impl Get_adsb;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					REQUETE4D window = new REQUETE4D();
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
	public REQUETE4D() {
	//	super();
		initialize();
		//Get_adsb=new OSKY_impl(this);
		//controleur=new Controleur(this, Get_adsb);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEnvoyer = new JButton("Envoyer");
				
				
		btnEnvoyer.setBounds(294, 201, 117, 25);
		frame.getContentPane().add(btnEnvoyer);
		
		longitude_lo = new JTextField("longitude_lo");
		longitude_lo.setBounds(114, 12, 114, 19);
		frame.getContentPane().add(longitude_lo);
		longitude_lo.setColumns(10);
		
		longitude_hi = new JTextField("longitude_hi");
		longitude_hi.setBounds(280, 12, 114, 19);
		frame.getContentPane().add(longitude_hi);
		longitude_hi.setColumns(10);
		
		latitude_lo = new JTextField();
		latitude_lo.setBounds(114, 49, 114, 19);
		frame.getContentPane().add(latitude_lo);
		latitude_lo.setColumns(10);
		
		latitude_hi = new JTextField();
		latitude_hi.setBounds(280, 49, 114, 19);
		frame.getContentPane().add(latitude_hi);
		latitude_hi.setColumns(10);
		
		altitude_lo = new JTextField();
		altitude_lo.setBounds(114, 94, 114, 19);
		frame.getContentPane().add(altitude_lo);
		altitude_lo.setColumns(10);
		
		altitude_hi = new JTextField();
		altitude_hi.setBounds(280, 94, 114, 19);
		frame.getContentPane().add(altitude_hi);
		altitude_hi.setColumns(10);
		
		Date_lo = new JTextField();
		Date_lo.setBounds(114, 140, 114, 19);
		frame.getContentPane().add(Date_lo);
		Date_lo.setColumns(10);
		
		Date_hi = new JTextField();
		Date_hi.setBounds(280, 140, 114, 19);
		frame.getContentPane().add(Date_hi);
		Date_hi.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setBounds(12, 14, 86, 15);
		frame.getContentPane().add(lblLongitude);
		
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(12, 51, 70, 15);
		frame.getContentPane().add(lblLatitude);
		
		JLabel lblAltitude = new JLabel("Altitude");
		lblAltitude.setBounds(12, 96, 70, 15);
		frame.getContentPane().add(lblAltitude);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(12, 142, 70, 15);
		frame.getContentPane().add(lblDate);
		
		JTextArea txtrApercu = new JTextArea();
		txtrApercu.setText("apercu");
		txtrApercu.setBounds(12, 269, 426, 304);
		frame.getContentPane().add(txtrApercu);
	}
}

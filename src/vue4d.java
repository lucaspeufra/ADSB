import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class vue4d extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JTextField longitude_lo;
	private JTextField longitude_hi;
	private JTextField latitude_lo;
	private JTextField latitude_hi;
	
	private JTextField altitude_lo;
	private JTextField altitude_hi;
	private JTextField Date_lo;
	private JTextField Date_hi;

	private JTextField txtFichcsv;

	private JTextArea txtrApercu;
	
	
	
	private Controleur controleur;
	
	
	
	private OSKY_impl Get_adsb;


	public vue4d() {
		super("PEUFRADSB");
		
		this.setPreferredSize(new Dimension(450,600));
		Get_adsb=new OSKY_impl(this);
		controleur=new Controleur( Get_adsb);
		initialize();
		this.pack();
		this.setVisible(true);
	}
	
	private void initialize() {
		//this.setBounds(100, 100, 1300, 1600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.setActionCommand("4D");
		btnEnvoyer.addActionListener(controleur);
		
				
		btnEnvoyer.setBounds(294, 232, 117, 25);
		this.getContentPane().add(btnEnvoyer);
		
		

		longitude_lo = new JTextField("longitude_lo");
		longitude_lo.setBounds(114, 12, 114, 19);
		this.getContentPane().add(longitude_lo);
		longitude_lo.setColumns(10);
		
		longitude_hi = new JTextField("longitude_hi");
		longitude_hi.setBounds(280, 12, 114, 19);
		this.getContentPane().add(longitude_hi);
		longitude_hi.setColumns(10);
		
		latitude_lo = new JTextField();
		latitude_lo.setBounds(114, 49, 114, 19);
		this.getContentPane().add(latitude_lo);
		latitude_lo.setColumns(10);
		
		latitude_hi = new JTextField();
		latitude_hi.setBounds(280, 49, 114, 19);
		this.getContentPane().add(latitude_hi);
		latitude_hi.setColumns(10);
		
		altitude_lo = new JTextField();
		altitude_lo.setBounds(114, 94, 114, 19);
		this.getContentPane().add(altitude_lo);
		altitude_lo.setColumns(10);
		
		altitude_hi = new JTextField();
		altitude_hi.setBounds(280, 94, 114, 19);
		this.getContentPane().add(altitude_hi);
		altitude_hi.setColumns(10);
		
		Date_lo = new JTextField();
		Date_lo.setBounds(114, 196, 114, 19);
		this.getContentPane().add(Date_lo);
		Date_lo.setColumns(10);
		
		Date_hi = new JTextField();
		Date_hi.setBounds(280, 196, 114, 19);
		this.getContentPane().add(Date_hi);
		Date_hi.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setBounds(12, 14, 86, 15);
		this.getContentPane().add(lblLongitude);
		
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(12, 51, 70, 15);
		this.getContentPane().add(lblLatitude);
		
		JLabel lblAltitude = new JLabel("Altitude");
		lblAltitude.setBounds(12, 96, 70, 15);
		this.getContentPane().add(lblAltitude);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(12, 198, 70, 15);
		this.getContentPane().add(lblDate);
		
		txtrApercu = new JTextArea();
		txtrApercu.setText("apercu");
		txtrApercu.setBounds(12, 269, 426, 304);
		this.getContentPane().add(txtrApercu);
		
		JCheckBox baro_altitude = new JCheckBox("Baro altitude");
		baro_altitude.setBounds(28, 141, 129, 23);
		this.getContentPane().add(baro_altitude);
		
		JCheckBox geo_altitude = new JCheckBox("Geo altitude");
		geo_altitude.setBounds(190, 141, 129, 23);
		this.getContentPane().add(geo_altitude);
		
		JLabel lblNomFichier = new JLabel("Nom fichier");
		lblNomFichier.setBounds(12, 237, 96, 15);
		this.getContentPane().add(lblNomFichier);
		
		txtFichcsv = new JTextField();
		txtFichcsv.setText("testrequ4d.csv");
		txtFichcsv.setBounds(114, 235, 114, 19);
		this.getContentPane().add(txtFichcsv);
		txtFichcsv.setColumns(10);

	}

	public JTextArea getTxtrApercu() {
		return txtrApercu;
	}

	public JTextField getLongitude_lo() {
		return longitude_lo;
	}

	public void setLongitude_lo(JTextField longitude_lo) {
		this.longitude_lo = longitude_lo;
	}

	public JTextField getLongitude_hi() {
		return longitude_hi;
	}

	public void setLongitude_hi(JTextField longitude_hi) {
		this.longitude_hi = longitude_hi;
	}

	public JTextField getLatitude_lo() {
		return latitude_lo;
	}

	public void setLatitude_lo(JTextField latitude_lo) {
		this.latitude_lo = latitude_lo;
	}

	public JTextField getLatitude_hi() {
		return latitude_hi;
	}

	public void setLatitude_hi(JTextField latitude_hi) {
		this.latitude_hi = latitude_hi;
	}

	public JTextField getAltitude_lo() {
		return altitude_lo;
	}

	public void setAltitude_lo(JTextField altitude_lo) {
		this.altitude_lo = altitude_lo;
	}

	public JTextField getAltitude_hi() {
		return altitude_hi;
	}

	public void setAltitude_hi(JTextField altitude_hi) {
		this.altitude_hi = altitude_hi;
	}

	public JTextField getDate_lo() {
		return Date_lo;
	}

	public void setDate_lo(JTextField date_lo) {
		Date_lo = date_lo;
	}

	public JTextField getDate_hi() {
		return Date_hi;
	}

	public void setDate_hi(JTextField date_hi) {
		Date_hi = date_hi;
	}

	public JTextField getTxtFichcsv() {
		return txtFichcsv;
	}

	public void setTxtFichcsv(JTextField txtFichcsv) {
		this.txtFichcsv = txtFichcsv;
	}



	


}

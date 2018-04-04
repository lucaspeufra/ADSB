package adsb;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class frame implements ActionListener{

	private JFrame frame;
	private JTextPane textPane;
	private JTextArea textArea;
	private ArrayList<DataStat> ListeData = new ArrayList<DataStat>();

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
		//	ListeData=new ArrayList<Data>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1207, 854);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton Recuperer = new JButton("Recuperer");
		Recuperer.setBounds(201, 43, 117, 25);
		frame.getContentPane().add(Recuperer);
		Recuperer.addActionListener(this);


		textPane = new JTextPane();
		textPane.setBounds(12, 43, 127, 25);
		frame.getContentPane().add(textPane);

		JLabel lblAdresseDonneeBrut = new JLabel("Adresse donnee brut");
		lblAdresseDonneeBrut.setBounds(12, 24, 256, 25);
		frame.getContentPane().add(lblAdresseDonneeBrut);
		textArea = new JTextArea(5, 20);
		textArea.setLocation(65, 108);
		textArea.setSize(1130, 707);

		frame.getContentPane().add(textArea);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{Recuperer, textPane}));
	}






	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//URL url=null;
		//this.testIt();
		RecupHTTP osky=new RecupHTTP(textArea,ListeData, "https://opensky-network.org/api/states/all");
		 TimerTask timerTask = osky;//new RecupHTTP(textArea,ListeData, "https://opensky-network.org/api/states/all");
		 osky.ecrireFichier("suivi.txt", "time;total;exploitable",true);/// initialisation du fichier de suivi stat
			
		 	        // running timer task as daemon thread
		 	        Timer timer = new Timer(true);
		 	        timer.schedule(timerTask, 0, 10 * 1000);
		 	       
		 	    //    System.out.println("TimerTask begins! :" + new Date());
		 	        // cancel after sometime
		 	        try {
		 	            Thread.sleep(20000);
		 	        } catch (InterruptedException e) {
		 	            e.printStackTrace();
		 	        }
		 	       // timer.cancel();
		 	      //  System.out.println("TimerTask cancelled! :" + new Date());
		 	        try {
		 	            Thread.sleep(30000);
		 	        } catch (InterruptedException e) {
		 	            e.printStackTrace();
		 	        }
		

	}

	}	


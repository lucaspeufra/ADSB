package adsb;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
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
	private ArrayList<Data> ListeData = new ArrayList<Data>();
	private static int cpt=0;
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
		this.testIt();

	}

	private void testIt(){

		String https_url = "https://opensky-network.org/api/states/all";
		//	String https_url = "https://www.google.com/";

		URL url;
		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			//dumpl all cert info
			//			print_https_cert(con);

			//dump all the content
			print_content(con);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*	private void print_https_cert(HttpsURLConnection con){

		if(con!=null){

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}

		}

	}*/

	/*delimiteurs
	 * abc…    Letters
123…    Digits
\d      Any Digit
\D      Any Non-digit character
.       Any Character
\.      Period
[abc]   Only a, b, or c
[^abc]  Not a, b, nor c
[a-z]   Characters a to z
[0-9]   Numbers 0 to 9
\w      Any Alphanumeric character
\W      Any Non-alphanumeric character
{m}     m Repetitions
{m,n}   m to n Repetitions
	 *       Zero or more repetitions
+       One or more repetitions
?       Optional character
\s      Any Whitespace
\S      Any Non-whitespace character
^…$     Starts and ends
(…)     Capture Group
(a(bc)) Capture Sub-group
(.*)    Capture all
(ab|cd) Matches ab or cd	
	 */


	private void print_content(HttpsURLConnection con){
		if(con!=null){

			try {

				System.out.println("****** Content of the URL ********\n");
				BufferedReader br =	new BufferedReader(	new InputStreamReader(con.getInputStream()));
				String input;
				Scanner scan1;

				//Pattern delimiter= new 

				while ((input = br.readLine()) != null){
					/*	scan2=new Scanner(input);
					scan2.useDelimiter(":");
					scan2.next();
					scan2.next();
					scan2.reset();
					input =scan2.next();*/
					scan1=new Scanner(input);
					scan1.useDelimiter("],");
					System.out.println(scan1.next());
					while(scan1.hasNext())	{
						//	System.out.println(cpt+"--"+scan1.next() );
						//	this.textPane_1.setText(cpt+"--"+scan1.next()+"\n");

						String ligne =scan1.next();
						//	this.textArea.append(cpt+"--"+ligne+"\n");
						String [] data=ligne.split(",");
						//	this.textArea.append(cpt+"-x-");
						/*		for (String str : data)
					{
						this.textArea.append(str+"-x-");
					}
					this.textArea.append("\n");*/

						//pour tst
						Data adsb=new Data(data[0],0);
						if (!data[3].equals("null"))
						{
							adsb.setTime_position(Integer.parseInt(data[3]));
						}
						cpt++;
						int i=0;
						int aut=1;
						for (i=0;i<ListeData.size();i++)
							{if (ListeData.get(i).compareTo(adsb)==0)
							{
							i=ListeData.size();
							aut=0;
							}
							}
						if (aut==1)
							{
							ListeData.add(adsb);
							}
						//	this.textArea.append(adsb +"\n");


					}
					System.out.println(cpt );
					System.out.println(ListeData.size() );
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}	


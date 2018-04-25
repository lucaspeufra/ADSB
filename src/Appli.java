import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Appli extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Color noir = new Color(0,0,0);
	public Color orange = new Color(255,69,0);
	public Color vert = new Color(0,205,0);
	
	
	//private ControleurMenu controleurMenu;
	private Controleur controleur;
	
	
	private String adresseSource;
	private String adresseBDD;
	
	
	private JButton start;
	private JButton stop;
	
	
	private JLabel charger;
	private JLabel connexion;
	private JLabel analyse;
	private JLabel requete;
	private JLabel attente;

	
	private JTextPane suivi;
	private JScrollPane scroller;


	
	private Traitement traitement;


	public Appli() {
		super("PEUFRADSB");
		
		this.setPreferredSize(new Dimension(1100,200));
		traitement=new Traitement( this);
		
		controleur=new Controleur(this, traitement);
		
		
		Container contenu= this.getContentPane();
		contenu.setLayout(new GridLayout(1,2));
		contenu.add(panelG());
		contenu.add(panelD());
		
		creerMenu();

		this.setAdresseSource("https://opensky-network.org/api/states/all");/// on met une adresse en memoire par defaut
		
		this.pack();
		this.setVisible(true);
	}



	private void creerMenu() {
		// TODO Auto-generated method stub
		JMenuBar jmb=new JMenuBar();
		this.setJMenuBar(jmb);
		JMenu jmenu=new JMenu("Charger");
		jmb.add(jmenu);

		JMenuItem chargersource= new JMenuItem("Source");
		chargersource.setActionCommand("CHARGERS");
		jmenu.add(chargersource);
		
		JMenuItem chargerbdd= new JMenuItem("BDD");
		chargerbdd.setActionCommand("CHARGERB");
		jmenu.add(chargerbdd);

		JMenuItem quitter=new JMenuItem("Quitter");
		quitter.setActionCommand("QUITTER");
		jmenu.addSeparator();
		jmenu.add(quitter);

		chargersource.addActionListener(controleur);
		chargerbdd.addActionListener(controleur);
		quitter.addActionListener(controleur);
	}




	private Component panelG() {
		// TODO Auto-generated method stub
		JPanel jp1=new JPanel();
		jp1.setLayout(new GridLayout(6,1));

		
		
		JPanel jpboutons=new JPanel();
		jpboutons.setLayout(new GridLayout(1,2));
		
		this.start=new JButton("Start");
		//this.start.addActionListener(new ControleurBoutonStart(timer));
		this.start.addActionListener(controleur);
		start.setActionCommand("START");
		
		this.stop=new JButton("Stop");
	//	this.stop.addActionListener(new ControleurBoutonStop(timer));
		this.stop.addActionListener(controleur);
		stop.setActionCommand("STOP");
		
		jpboutons.add(this.start);
		jpboutons.add(this.stop);
		jp1.add(jpboutons);
		
		this.charger=new JLabel("Charger Source et BDD");
		this.charger.setForeground(orange);
		jp1.add(this.charger);
		
		this.connexion=new JLabel("Connexion au flux");
		jp1.add(this.connexion);
		
		this.analyse=new JLabel("Analyse des données");
		jp1.add(this.analyse);
		
		this.requete=new JLabel("Envoie de la requête SQL");
		jp1.add(this.requete);
		
		this.attente=new JLabel("Attente d'une nouvelle connexion");
		jp1.add(this.attente);
		
		
		return jp1;
	}

	
	
	private Component panelD() {
		// TODO Auto-generated method stub
		JPanel jp1=new JPanel();
		this.suivi=new JTextPane();
		suivi.setText("Visualisation des statistiques");
		this.scroller=new JScrollPane(suivi,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scroller.setPreferredSize(new Dimension(530,140));
		jp1.add(this.scroller);
		return jp1;
	}




	public String getAdresseSource() {
		return adresseSource;
	}

	public void setAdresseSource(String adresseSource) {
		this.adresseSource = adresseSource;
	}

	public String getAdresseBDD() {
		return adresseBDD;
	}

	public void setAdresseBDD(String adresseBDD) {
		this.adresseBDD = adresseBDD;
	}

	public JLabel getCharger() {
		return charger;
	}

	public void setCharger(JLabel charger) {
		this.charger = charger;
	}

	public JLabel getConnexion() {
		return connexion;
	}

	public void setConnexion(JLabel connexion) {
		this.connexion = connexion;
	}

	public JLabel getAnalyse() {
		return analyse;
	}

	public void setAnalyse(JLabel analyse) {
		this.analyse = analyse;
	}

	public JLabel getRequete() {
		return requete;
	}

	public void setRequete(JLabel requete) {
		this.requete = requete;
	}

	public JLabel getAttente() {
		return attente;
	}

	public void setAttente(JLabel attente) {
		this.attente = attente;
	}


	public JScrollPane getScroller() {
		return scroller;
	}

	public void setScroller(JScrollPane scroller) {
		this.scroller = scroller;
	}

	public JTextPane getSuivi() {
		return suivi;
	}

	public void setSuivi(JTextPane suivi) {
		this.suivi = suivi;
	}


	


}

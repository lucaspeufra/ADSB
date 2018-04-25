import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Controleur implements ActionListener {
	//private MonTimer timer;
	private Appli apt;	
	private Traitement traitement;
	
	public Controleur(Appli apt, Traitement traitement) {
		super();
		this.apt = apt;
		this.traitement = traitement;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getActionCommand()) {
		
		case "START" : 
		traitement.start();

		break;

		case "STOP" :  
		traitement.stop();
		break;


		case "QUITTER" : System.exit(0); 
		break;

		case "CHARGERS" : // menu charger
		{

			String inputValue = JOptionPane.showInputDialog("Donnez l'adresse de la source :","https://opensky-network.org/api/states/all");
			apt.setAdresseSource(inputValue);
			//if (!apt.getAdresseBDD().isEmpty()) apt.getCharger().setForeground(apt.vert);

		}
		break;

		case "CHARGERB" :
		{
			String inputValue = JOptionPane.showInputDialog("Donnez l'adresse de la BDD :");
			apt.setAdresseBDD(inputValue);
			if (!apt.getAdresseSource().isEmpty()) apt.getCharger().setForeground(apt.vert);
		}
		break;

		}
	}


}

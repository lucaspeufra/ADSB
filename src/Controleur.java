import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JOptionPane;


/**
 * Cette classe est la partie "controleur" du modèle MVC. Elle gère les
 * actions des deux fenêtres.
 * 
 * @author lucas
 * 
 *
 */

public class Controleur extends Timer implements ActionListener {
	
	/**
	 * L'instance de la classe "vue" Appli
	 */
	private Appli apt;	
	/**
	 * L'instance de la classe qui récupère les données ADSB
	 */
	private GET_ADSB traitement;

	/**
	 * Constructeur pour l'application de traitement.
	 * @param apt
	 * @param traitement
	 */
	public Controleur(Appli apt, GET_ADSB traitement) {
		super();
		this.apt = apt;
		this.traitement = traitement;
	}

	/**
	 * Constructeur pour l'application de requêtes.
	 * @param traitement
	 */
	public Controleur(GET_ADSB traitement) {
		super();
		this.traitement = traitement;
	}


	/**
	 * La méthode qui gère les changements d'état sur les boutons de menu
	 * ainsi que sur les boutons start, stop et 4d
	 * Chaque commande est associée à une méthode de la classe GET_ADSB 
	 * qui mettra à jour la vue
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getActionCommand()) {
		
		case "START" : 
		traitement.start();

		break;

		case "STOP" :  
		traitement.stop();
		break;


		case "QUITTER" :
			System.exit(0); 
		break;

		case "4D" :
			traitement.requete4d();
		break;

		
		case "CHARGERS" : // menu charger
		{

			String inputValue = JOptionPane.showInputDialog("Donnez l'adresse de la source :","https://opensky-network.org/api/states/all");
			apt.setAdresseSource(inputValue);
			
		}
		break;

		case "CHARGERB" :
		{
			String inputValue = JOptionPane.showInputDialog("Donnez l'adresse de la BDD :");
			apt.setAdresseBDD(inputValue);
		}
		break;

		}
	}


}

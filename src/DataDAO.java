

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe abstraite qui contient les méthodes de connection/déconnection 
 * à la base de donnée et qui déclare des méthodes abstraites qui contiendront 
 * les requêtes d'insertion, suppression, etc adaptées à chaque table.
 * @author lucas
 *
 */
public abstract class DataDAO {
	
	/**
	 * Méthode qui clôt la connection avec la bdd.
	 * @param connexion
	 */
	public void fermerConnect(Connection connexion) {

		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connexion != null)
				try {

					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException ignore) {
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
				}
		}

	}
	
	/**
	 * Méthode qui se connecte à la BDD via JDBC, avec url, user et password.
	 * @return La connection à la base de donnée.
	 */
	public Connection connect() {
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/ADSB?autoReconnect=true&useSSL=false";
		String utilisateur = "root";
		String motDePasse = "Dreda2010*";
		Connection connexion = null;
		// chargement du driver
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection(url, utilisateur,motDePasse);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			/* Gérer les éventuelles erreurs ici. */
		} 
		return connexion;
	}

	public abstract int create(ArrayList<DataFull> Tamponsql);

	public abstract ArrayList<DataFull> findAll();

	public abstract DataFull find(String icao);

	public abstract void update(DataFull p);

	public abstract void delete(DataFull p);
		


}

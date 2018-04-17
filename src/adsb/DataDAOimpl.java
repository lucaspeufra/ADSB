package adsb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class DataDAOimpl extends DataDAO  {

	@Override
	public void create(DataFull p) {
		// TODO Auto-generated method stub
		String sql;
		Connection connexion = connect();
		java.sql.Statement stmt;
	//	String idPersonne = p.getIdPersonne();
	//	String nom = p.getNom();
	//	String prenom = p.getPrenom();
		try {
			stmt = connexion.createStatement();
//			sql = "INSERT INTO Personne (IdPersonne, nom, prenom) VALUES ('"
//					+ idPersonne + "','" + nom + "','" + prenom + "')";
			sql = p.requete_creer();
			stmt.executeUpdate(sql);// On execute la requete avec update
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
	}
	
	public void create(ArrayList<DataFull> Tamponsql) {
		// TODO Auto-generated method stub
		String sql="";
		Connection connexion = connect();
		java.sql.Statement stmt;
		DataFull p =new DataFull(sql, sql, sql, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, sql, false, 0);
	//	String idPersonne = p.getIdPersonne();
	//	String nom = p.getNom();
	//	String prenom = p.getPrenom();
		try {
			stmt = connexion.createStatement();
//			sql = "INSERT INTO Personne (IdPersonne, nom, prenom) VALUES ('"
//					+ idPersonne + "','" + nom + "','" + prenom + "')";
				sql = p.requete_creer_list(Tamponsql);
				stmt.executeUpdate(sql);// On execute la requete avec update
			
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
	}
	

	@Override
	public ArrayList<DataFull> findAll() {
		ArrayList<DataFull> listePersonne = new ArrayList<DataFull>();
		String sql;
		Connection connexion = connect();
		java.sql.Statement stmt;
		ResultSet rs;
		try {
			stmt = connexion.createStatement();
			sql = "SELECT * FROM Personne";
			rs = stmt.executeQuery(sql);
			DataFull personne;
			while (rs.next()) {
				// Retrieve by column name
				String idPersonne = rs.getString("idPersonne");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				personne = new DataFull(idPersonne, nom, prenom, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, prenom, false, 0);
				listePersonne.add(personne);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
		return listePersonne;
	}

	@Override
	public DataFull find(String idPersonne) {
		DataFull personne = null;
		String sql;
		Connection connexion = connect();
		java.sql.Statement stmt;
		ResultSet rs;
		try {
			stmt = connexion.createStatement();
			sql = "SELECT * FROM Personne WHERE idPersonne = '"+ idPersonne +"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				personne = new DataFull(idPersonne, nom, prenom, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, prenom, false, 0);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
		return personne;
	}

	@Override
	public void update(DataFull p) {
	//	String sql;
		Connection connexion = connect();
		java.sql.Statement stmt;
		//String idPersonne = p.
		//String nom = p.getNom();
		//String prenom = p.getPrenom();
		try {
			stmt = connexion.createStatement();
//			sql = " UPDATE Personne SET nom='" + nom + "', prenom='" + prenom
//					+ "' WHERE IdPersonne= '" + idPersonne+ "'";
		//	stmt.executeUpdate(sql);// On execute la requete avec update
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
	}

	@Override
	public void delete(DataFull p) {

		// TODO Auto-generated method stub
	//	String sql;
		Connection connexion = connect();
		java.sql.Statement stmt;
	//	String idPersonne = p.getIdPersonne();
	//	String nom = p.getNom();
	//	String prenom = p.getPrenom();
		try {
			stmt = connexion.createStatement();
//			sql =  "DELETE FROM Personne WHERE Personne.IdPersonne = '" + idPersonne+"'";
			//stmt.executeUpdate(sql);// On execute la requete avec update
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
	}

}

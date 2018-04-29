

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
		try {
			stmt = connexion.createStatement();
			sql = this.requete_creer(p);
			int toto=stmt.executeUpdate(sql);// On execute la requete avec update
			System.out.println("-"+toto+"--");
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
		try {
				if(Tamponsql.size()>0)  ///;on ecrit que si il y a qq chose dans liste
				{
					stmt = connexion.createStatement();
				sql = this.requete_creer_list(Tamponsql);
				stmt.executeUpdate(sql);// On execute la requete avec update
				stmt.close();
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(sql);
			
			e.printStackTrace();
		}
		fermerConnect(connexion);
	}
	
	public String request(String longi_lo,String longi_hi,String lati_lo,String lati_hi,String alti_lo,String alti_hi,String date_lo,String date_hi,boolean type_alti) 
	{
		String out;
		if(type_alti) /// cas de la geoaltitude
		{
		out="SELECT * FROM `adsb` WHERE `longitude` BETWEEN"+longi_lo+"AND"+longi_hi+"AND "+
		"`latitude` BETWEEN "+lati_lo+"AND"+lati_hi+"AND"+
		"`geo_altitude`BETWEEN "+alti_lo+"AND"+alti_hi+"AND"+
		"`time_position` BETWEEN "+date_lo+"AND"+date_hi;
		}
		else
		{

			out="SELECT * FROM `adsb` WHERE `longitude` BETWEEN"+longi_lo+"AND"+longi_hi+"AND "+
			"`latitude` BETWEEN "+lati_lo+"AND"+lati_hi+"AND"+
			"`baro_altitude`BETWEEN "+alti_lo+"AND"+alti_hi+"AND"+
			"`time_position` BETWEEN "+date_lo+"AND"+date_hi;
			
		}
		
		return out;
		
		
		
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
	
	private String requete_creer(DataFull data) {
		String requete ="INSERT IGNORE INTO `adsb` (`icao24`, `callsign`, `origin_country`, `time_position`, `last_contact`, `longitude`, `latitude`, `geo_altitude`, "
				+ "`on_ground`, `velocity`, `heading`, `vertical_rate`, `baro_altitude`, `squawk`, `spi`, `position_source`) VALUES "
				+ "('"+data.getIcao24()+"', '"+data.getCallsign()+"', '"+data.getOrigin_country()+"', '"+data.getTime_position()+"', '"
				+data.getLast_contact()+"', '"+data.getLongitude()+"', '"+data.getLatitude()+"', '"+data.getGeo_altitude()+"', '"+data.isOn_ground()+"', '"+
				data.getVelocity()+"', '"+data.getHeading()+"', '"+data.getVertical_rate()+"', '"+data.getBaro_altitude()+"', '"+data.getSquawk()+"', '"+data.isSpi()+
				"', '"+data.getPosition_source()+"')";
		requete=requete.replace("'"+Integer.MAX_VALUE+"'","NULL");   /// gestion de la probleme de la chaine Null
		Float flo=(float)Integer.MAX_VALUE;
		requete=requete.replace("'"+flo+"'","NULL");				/// cas particulier du float qui ecrit des exposant dans la requete
		return requete;
	}
	
	private String requete_creer_list(ArrayList<DataFull> Tamponsql) {
		String requete="INSERT IGNORE INTO `adsb` (`icao24`, `callsign`, `origin_country`, `time_position`, `last_contact`, `longitude`, "
				+ "`latitude`, `geo_altitude`, `on_ground`, `velocity`, `heading`, `vertical_rate`, `baro_altitude`, `squawk`, `spi`, `position_source`) VALUES ";
		int i;
		
		for (i=0;i < Tamponsql.size();i++)
		{
		requete=requete+
				"('"+
				Tamponsql.get(i).getIcao24()+"','"+
				Tamponsql.get(i).getCallsign()+	"','"+
				Tamponsql.get(i).getOrigin_country()+"','"+
				Tamponsql.get(i).getTime_position()+"','"+
				Tamponsql.get(i).getLast_contact()+"','"+
				Tamponsql.get(i).getLongitude()+"','"+
				Tamponsql.get(i).getLatitude()+"','"+
				Tamponsql.get(i).getGeo_altitude()+"','"+
				Tamponsql.get(i).isOn_ground()+"','"+
				Tamponsql.get(i).getVelocity()+"','"+
				Tamponsql.get(i).getHeading()+"','"+
				Tamponsql.get(i).getVertical_rate()+"','"+
				Tamponsql.get(i).getBaro_altitude()+"','"+
				Tamponsql.get(i).getSquawk()+"','"+
				Tamponsql.get(i).isSpi()+"','"+
				Tamponsql.get(i).getPosition_source()+
				"')";
				if(i==Tamponsql.size()-1)
				{
					requete=requete+ ";";
				}
				else
					requete=requete+',';
		}
		requete=requete.replace("'"+Integer.MAX_VALUE+"'","NULL");   /// gestion de la probleme de la chaine Null
		Float flo=(float)Integer.MAX_VALUE;
		requete=requete.replace("'"+flo+"'","NULL");				/// cas particulier du float qui ecrit des exposant dans la requete
		return requete;
	}
	
	
	

}

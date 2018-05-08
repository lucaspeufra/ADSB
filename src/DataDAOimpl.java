

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * Classe qui hérite de la classe DataDAO et qui définit ses méthodes 
 * abstraites pour notre table de données.
 * 
 * @author lucas
 *
 */
public class DataDAOimpl extends DataDAO  {
	
	

	/**
	 * Méthode qui envoie la requête d'insertion de la liste de données
	 * dans la table.
	 * @param Tamponsql La liste de données à stocker.
	 * @return Le nombre de données redondantes trouvées par la BDD 
	 * pour vérifier qu'il est à 0 et que notre algorithme fonctionne.
	 */
	public int create(ArrayList<DataFull> Tamponsql) {
		// TODO Auto-generated method stub
		String sql="";
		Connection connexion = connect();
		java.sql.Statement stmt;
		int ret=0;
		try {
			if(Tamponsql.size()>0)  ///;on ecrit que si il y a qq chose dans liste
			{
				stmt = connexion.createStatement();
				sql = this.requete_creer_list(Tamponsql);
				ret=stmt.executeUpdate(sql);// On execute la requete avec update
			//	System.out.println("--"+(Tamponsql.size()-ret)+"--\n");
				stmt.close();
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(sql);

			e.printStackTrace();
		}
		fermerConnect(connexion);
		return Tamponsql.size()-ret;
		
	}

	
	/**
	 * Méthode qui crée la requête 4D demandée par l'utilisateur.
	 * @param longi_lo
	 * @param longi_hi
	 * @param lati_lo
	 * @param lati_hi
	 * @param alti_lo
	 * @param alti_hi
	 * @param date_lo
	 * @param date_hi
	 * @param type_alti
	 * @return La requête sous forme de String.
	 */
	public String request(String longi_lo,String longi_hi,String lati_lo,String lati_hi,String alti_lo,String alti_hi,String date_lo,String date_hi,boolean type_alti) 
	{
		String out;
		if(type_alti) /// cas de la geoaltitude
		{
			out="SELECT * FROM `adsb` WHERE `longitude` BETWEEN "+longi_lo+" AND "+longi_hi+" AND "+
					"`latitude` BETWEEN "+lati_lo+" AND "+lati_hi+" AND "+
					"`geo_altitude` BETWEEN "+alti_lo+" AND "+alti_hi+" AND "+
					"`time_position` BETWEEN "+date_lo+" AND "+date_hi;
		}
		else
		{

			out="SELECT * FROM `adsb` WHERE `longitude` BETWEEN "+longi_lo+" AND "+longi_hi+" AND "+
					"`latitude` BETWEEN "+lati_lo+" AND "+lati_hi+" AND "+
					"`baro_altitude` BETWEEN "+alti_lo+" AND "+alti_hi+" AND "+
					"`time_position` BETWEEN "+date_lo+" AND "+date_hi;

		}

		return out;



	}



	
	/**
	 * Méthode qui envoie la requête 4D et qui retourne la réponse.
	 * @param sql La requête SQL.
	 * @return La liste des données qui correspondent à la requête.
	 */
	public ArrayList<DataFull> findsql(String sql) {
		ArrayList<DataFull> listeData = new ArrayList<DataFull>();

		Connection connexion = connect();
		java.sql.Statement stmt;
		ResultSet rs;
		try {
			stmt = connexion.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String[] data= new String[16];

				data[0] = rs.getString("icao24");
				data[1] = rs.getString("callsign");
				data[2] = rs.getString("origin_country");

				data[3] = rs.getString("time_position");
				data[4] = rs.getString("last_contact");
				data[5] = rs.getString("longitude");

				data[6] = rs.getString("latitude");
				data[7] = rs.getString("geo_altitude");
				data[8] = rs.getString("on_ground");

				data[9] = rs.getString("velocity");
				data[10] = rs.getString("heading");
				data[11] = rs.getString("vertical_rate");

				data[12] = rs.getString("baro_altitude");
				data[13] = rs.getString("squawk");
				data[14] = rs.getString("spi");
				data[15] = rs.getString("position_source");
				int i=0;
				for(i=0;i<data.length;i++)  /// pour enlveler le pb de parse avec la chaine null
				{
					//System.out.println(data[i]);
					if(data[i]==null)
					{data[i]=new String(""+Integer.MAX_VALUE);}    ///// creation d'une valeur caracteristique du champ null Integer.MAX_VALUE pour pouvoir retouver le champ null

				}


				DataFull adsb=new DataFull(data[0],data[1],data[2],Integer.parseInt(data[3]),
						Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]),
						Float.parseFloat(data[7]),Boolean.valueOf(data[8]),Float.parseFloat(data[9]),
						Float.parseFloat(data[10]),Float.parseFloat(data[11]),Float.parseFloat(data[12]),
						data[13],Boolean.valueOf(data[14]),Integer.parseInt(data[15]));
				//	System.out.println(adsb);
				listeData.add(adsb);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnect(connexion);
		return listeData;
	}



	@Override
	public ArrayList<DataFull> findAll() {
		return null;
	}

	@Override
	public DataFull find(String icao) {
		return null;
	}

	@Override
	public void update(DataFull p) {
	}

	@Override
	public void delete(DataFull p) {
	}

	/**
	 * Méthode qui crée la requête pour envoyer une ligne à la BDD.
	 * @param data
	 * @return La requête pour l'insertion de la donnée dans la table
	 */
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

	/**
	 * Méthode qui crée la requête pour envoyer plusieurs lignes à la BDD 
	 * en une fois.
	 * @param Tamponsql
	 * @return La requête pour l'insertion de la liste de données 
	 * dans la table
	 */
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

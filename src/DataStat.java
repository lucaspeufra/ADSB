/**
 * Cette classe sert à l'analyse des données (redondance) et au comptage.
 * Elle hérite de la classe DataFull
 *
 * @author lucas
 *
 */

public class DataStat extends DataFull{
	/**
	 * booléen qui associé au champ "on_ground" permet de compter les vols
	 */
	protected boolean aAjouter;
	/**
	 * Durée de vie de la donnée ; elle est décrémentée à chaque fois 
	 * que la donnée du tampon est utilisée pour l'analyse, à condition 
	 * qu'elle ne soit pas redondante par rapport au nouveau flux entrant ; 
	 * elle sert à déterminer le temps au bout duquel le vol a peu de chances 
	 * de réapparaitre et peut donc être supprimé du tampon.
	 */
	protected double TTL = 30; /// temps d'expiration avant d etre flusher de la table

	public DataStat(String icao24, String callsign, String origin_country, int time_position, int last_contact,
			float longitude, float latitude, float geo_altitude, boolean on_ground, float velocity, float heading,
			float vertical_rate, float baro_altitude, String squawk, boolean spi, int position_source, boolean aAjouter) {
		
		super(icao24, callsign, origin_country, time_position, last_contact, longitude, latitude,geo_altitude, on_ground,velocity, heading, vertical_rate,
				baro_altitude, squawk, spi,  position_source);
		this.aAjouter = aAjouter;
		
	}
	public boolean isaAjouter() {
		return aAjouter;
	}

	public void setaAjouter(boolean aAjouter) {
		this.aAjouter = aAjouter;
	}

	public double getTTL() {
		return TTL;
	}
	public void setTTL(double tTL) {
		TTL = tTL;
	}

	

}

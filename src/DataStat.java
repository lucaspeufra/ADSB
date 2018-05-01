

public class DataStat extends DataFull{
	protected boolean aAjouter;
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

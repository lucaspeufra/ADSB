

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


/*

	@Override
	public int compareTo(DataStat arg0) {
		// TODO Auto-generated method stub
		// correction 1 :
		if (this.getIcao24().equals(arg0.getIcao24())&&(this.getLast_contact()==arg0.getLast_contact())) 
			return 0;
		else 
		{
			if (this.getIcao24().compareTo(arg0.getIcao24())==0)
			{if (this.getLast_contact()<arg0.getLast_contact())
			{return -1;}
			else
			{return 1;}
			}

			if (this.getIcao24().compareTo(arg0.getIcao24())==-1)
			{return -1;}

			else
			{return 1;}
		}
		// autre correction possible :
		/*
		return Double.compare(this.getPoids(), arg0.getPoids());

		 






	}*/




	public double getTTL() {
		return TTL;
	}




	public void setTTL(double tTL) {
		TTL = tTL;
	}




	public int getLast_contact() {
		return last_contact;
	}




	public void setLast_contact(int last_contact) {
		this.last_contact = last_contact;
	}
	
	
	

}

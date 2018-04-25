

public class DataStat implements Comparable<DataStat> {
	protected String icao24;
	protected int last_contact;
	protected boolean on_ground;
	protected boolean aAjouter;
	protected double TTL = 30; /// temps d'expiration avant d etre flusher de la table
	

	
	
	
	



	public boolean isOn_ground() {
		return on_ground;
	}




	public void setOn_ground(boolean on_ground) {
		this.on_ground = on_ground;
	}




	public String getIcao24() {
		return icao24;
	}




	public DataStat(String icao24, int last_contact, boolean on_ground, boolean aAjouter) {
		super();
		this.icao24 = icao24;
		this.last_contact = last_contact;
		this.on_ground = on_ground;
		this.aAjouter = aAjouter;
		
	}




	public void setIcao24(String icao24) {
		this.icao24 = icao24;
	}






	public boolean isaAjouter() {
		return aAjouter;
	}




	public void setaAjouter(boolean aAjouter) {
		this.aAjouter = aAjouter;
	}




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

		 */






	}




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

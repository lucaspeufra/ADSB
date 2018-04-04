package adsb;

public class DataStat implements Comparable<DataStat> {
	private String icao24;
	private int time_position;
	private boolean auSol;
	private boolean aAjouter;

	
	
	
	public DataStat(String icao24, int time_position, boolean auSol, boolean aAjouter) {
		super();
		this.icao24 = icao24;
		this.time_position = time_position;
		this.auSol = auSol;
		this.aAjouter = aAjouter;
	}




	@Override
	public String toString() {
		return "DataStat [icao24=" + icao24 + ", time_position=" + time_position + ", auSol=" + auSol + ", aAjouter="
				+ aAjouter + "]";
	}




	public String getIcao24() {
		return icao24;
	}




	public void setIcao24(String icao24) {
		this.icao24 = icao24;
	}




	public int getTime_position() {
		return time_position;
	}




	public void setTime_position(int time_position) {
		this.time_position = time_position;
	}




	public boolean isAuSol() {
		return auSol;
	}




	public void setAuSol(boolean auSol) {
		this.auSol = auSol;
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
		if (this.getIcao24().equals(arg0.getIcao24())&&(this.getTime_position()==arg0.getTime_position())) 
			return 0;
		else 
		{
			if (this.getIcao24().compareTo(arg0.getIcao24())==0)
			{if (this.getTime_position()<arg0.getTime_position())
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
	
	
	

}

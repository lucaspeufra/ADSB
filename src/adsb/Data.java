package adsb;

public class Data implements Comparable<Data> {
	private String icao24;
	private int time_position;

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


	public Data(String icao241, int time_position1) {
		super();
		this.icao24 = icao241;
		this.time_position = time_position1;
	}
	@Override
	public String toString() {
		return "Data [icao24=" + icao24 + ", time_position=" + time_position + "]";
	}
	@Override
	public int compareTo(Data arg0) {
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

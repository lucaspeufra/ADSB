package adsb;

public class testbdd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataDAOimpl ADSBbdd= new DataDAOimpl();
		DataFull adsbstore=new DataFull(null, null, null, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, null, false, 0);
		ADSBbdd.create(adsbstore);
		
	}

}

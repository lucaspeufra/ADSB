package adsb;

public class testbdd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataDAOimpl ADSBbdd= new DataDAOimpl();
		DataFull adsbstore=new DataFull(null, null, null, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, null, false, 0);
		ADSBbdd.create(adsbstore);
		String toto="tata ]]} tata";
		System.out.println(toto);
			toto=	toto.replace(']', ' ');
		System.out.println(toto);
		toto=toto.replace("tata", "t");
		System.out.println(toto);
		
		String tr="true";
		String fl="false";
		Boolean tr1=	Boolean.valueOf(tr);
		Boolean fl1=	Boolean.valueOf(fl);
		
		System.out.println(tr  +   tr1);
		System.out.println(fl  +   fl1);
		
	}

}

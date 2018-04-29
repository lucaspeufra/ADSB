import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class OSKY_impl extends GET_ADSB{
	

	public OSKY_impl(Appli apt) {
		super(apt);
		// TODO Auto-generated constructor stub
	}


	public OSKY_impl(vue4d apt) {
		super(apt);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected ArrayList<DataStat> parse_input(String input) {
		// TODO Auto-generated method stub
		Scanner scan1;
		
		ArrayList<DataStat> input_liste= new ArrayList<DataStat>();
		input=input.replace("[","");
		input=input.replace("}","");
		input=input.replace('"', ' ');
		input=input.replace("'", "-");/// tentative d'elimination du pb de requete sql

		input=input.replace(" ","");

		scan1=new Scanner(input);
		scan1.useDelimiter(":");
		scan1.next();/// elimination du premier champs inutile
		scan1.next();
		scan1.reset();
		scan1.useDelimiter("],");
		
		apt.getConnexion().setForeground(new Color(0,205,0));


		while(scan1.hasNext())	{

			cpt++;


			String ligne =scan1.next();

			if (ligne.contains(":"))
				ligne=ligne.replace(":", " "); /// traitement du caracter : de debut


			if (ligne.contains("]"))
				ligne=ligne.replace("]", ""); /// traitement du caracter  de fin




			String [] data=ligne.split(",");

			int i=0;
			for(i=0;i<data.length;i++)  /// pour enlveler le pb de parse avec la chaine null
			{

				if(data[i].contains("null"))
				{data[i]=new String(""+Integer.MAX_VALUE);}    ///// creation d'une valeur caracteristique du champ null Integer.MAX_VALUE pour pouvoir retouver le champ null

			}


			DataStat adsb=new DataStat(data[0],data[1],data[2],Integer.parseInt(data[3]),
					Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]),
					Float.parseFloat(data[7]),Boolean.valueOf(data[8]),Float.parseFloat(data[9]),
					Float.parseFloat(data[10]),Float.parseFloat(data[11]),Float.parseFloat(data[13]),
					data[14],Boolean.valueOf(data[15]),Integer.parseInt(data[16]),false);
			input_liste.add(adsb);

		}
		apt.getAnalyse().setForeground(apt.vert);
		apt.getRequete().setForeground(apt.orange);
		frequence++;
		return input_liste;

		
		
	}

	

}

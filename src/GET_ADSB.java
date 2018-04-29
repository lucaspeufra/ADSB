import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public abstract  class GET_ADSB extends Timer{

	protected TimerTask task;					//Objet de Tache periodique 

	protected ArrayList<DataStat> ListeData ;	//Memoire du logiciel permettand de faire les stats (vol..etc) et le filtrage des donnees redondantes
	protected static DataDAOimpl ADSBbdd;     // reference a l'object de communication a la base de donnee (Modele singleton )


	protected long cpt=0;				//Ce compteur compte le nombre de donnees total recupere par l'objet
	protected long compteurvalide=0;	//Ce compteur compte le nombre de donnne non redondantes 
	protected long compteurvol=0;		//Ce compteur compte le nombre de vols

	protected int frequence=30;				//variable de comptage du temps à la seconde

	public long flux_periode=10;			//ici on regle la periode a laquelle on se connecte au flux de donnees en seconde

	protected int init=0;						//Cette variable permet de savoir si c'est la premier connexion au flux

	protected String nomfic="suivi.txt";		//Ici on precise le nom du fichier du suivi csv pour les stats
	protected int fich_periode=10;			//ici on regle la periode a laquelle on va ecrire les stats dans le fichier ci dessus en seconde



	protected Appli apt;						// Classe de la Vu concerne




	public GET_ADSB(Appli apt) {
		super();
		
		ListeData = new ArrayList<DataStat>();;
		this.apt=apt;
		if (ADSBbdd == null)			// singleton PATTERN
			ADSBbdd= new DataDAOimpl();		// on instancie l'object que si il ne l'est pas deja 
		
		
	}

	
	public void start()
	{
	        if ( task==null ) {
				task = new TimerTask(){		
					public void run(){

						
						// Ecriture dans la base de donnees
						ADSBbdd.create(analyseDonnee(parse_input(recuperationDonnees(connexionFlux()))));
						apt.getRequete().setForeground(apt.vert);

						write_stat();
						verfierExpiration();
						System.out.println(""+task.scheduledExecutionTime());
						

					}
				};
				super.scheduleAtFixedRate(task,new Date(),flux_periode*1000) ;}
	}

	public void stop() {
		if ( task!=null ) {
			task.cancel();
			task=null;
		}
	}





	public HttpsURLConnection connexionFlux(){


		apt.getConnexion().setForeground(apt.noir);
		apt.getAnalyse().setForeground(apt.noir);
		apt.getRequete().setForeground(apt.noir);
		apt.getAttente().setForeground(apt.noir);
		apt.getConnexion().setForeground(apt.orange);
		URL url;
		
		try {
			url=new URL(apt.getAdresseSource());;

			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			return con;
					

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		

	}

	private  void verfierExpiration()
	{
		int i,j;
		i=ListeData.size();
		j=0;
		while(j<i)
		{
			ListeData.get(j).setTTL(ListeData.get(j).getTTL()-1);
			if(ListeData.get(j).getTTL()==0)
			{
				ListeData.remove(j);
			}
			i=ListeData.size();
			j++;
		}

	}


	private String recuperationDonnees(HttpsURLConnection con){
		if(con!=null){
			try {
				String input;
				apt.getConnexion().setForeground(apt.vert);
				apt.getAnalyse().setForeground(apt.orange);

				BufferedReader br =	new BufferedReader(	new InputStreamReader(con.getInputStream()));

				input=br.readLine();
				br.close();
				if (input!= null)
					return input;
				return " ";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	protected abstract ArrayList<DataStat> parse_input (String input);
	/*{
		Scanner scan1=new Scanner(input);
		//scan1=new Scanner(input);

		ArrayList<DataStat> input_liste= new ArrayList<DataStat>();
		input=input.replace("[","");
		input=input.replace("}","");
		input=input.replace('"', ' ');
		input=input.replace("'", "-");/// tentative d'elimination du pb de requete sql

		input=input.replace(" ","");

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
		init=1;	
		frequence++;
		return input_liste;
	}

*/
	private ArrayList<DataFull> analyseDonnee(ArrayList<DataStat> data) {

		ArrayList<DataFull> Tamponsql =new ArrayList<DataFull>();

		int taille_input=data.size();
		int ptr_data=0;
		for (ptr_data=0;ptr_data < taille_input;ptr_data++)
		{
			int j=0;
			int n=ListeData.size();

			if (init==0) {

				if (data.get(ptr_data).on_ground==false) {

					data.get(ptr_data).setaAjouter(false);
					compteurvol++;

				}

				else data.get(ptr_data).setaAjouter(true);

				ListeData.add(data.get(ptr_data));
				compteurvalide++;
				Tamponsql.add((DataFull)data.get(ptr_data));

			}

			else {

				while (j<n)

				{

					if (ListeData.get(j).getIcao24().equals(data.get(ptr_data).getIcao24()))
					{

						if (ListeData.get(j).getLast_contact()!=data.get(ptr_data).getLast_contact()) {


							if (ListeData.get(j).isaAjouter()==true && data.get(ptr_data).isOn_ground() ==false) {
								data.get(ptr_data).setaAjouter(false);
								compteurvol++;
							}

							else {
								if (ListeData.get(j).isaAjouter()==false && data.get(ptr_data).isOn_ground()==true) {
									data.get(ptr_data).setaAjouter(true);
								}
							}


							compteurvalide++;
							Tamponsql.add((DataFull)data.get(ptr_data));
							ListeData.add(data.get(ptr_data));
							ListeData.remove(j);
						}

						j=n-1;

					}
					else {

						if (j==n-1) {

							if (data.get(ptr_data).isOn_ground()==false) {
								data.get(ptr_data).setaAjouter(false);
								compteurvol++;
							}
							else data.get(ptr_data).setaAjouter(true);
							compteurvalide++;
							Tamponsql.add((DataFull)data.get(ptr_data));
							ListeData.add(data.get(ptr_data));

						}
					}

					j++;
					n=ListeData.size();

				}
			}
		}

		return Tamponsql;
	}









	
	private void write_stat()
	{
		System.out.println("dqhshdk");
	if (frequence>=fich_periode)/// on vient ici toute les 30 * 10 s soit 300 s = 5 min
	{
		affichage();
		frequence=0;
		this.ecrireFichier(nomfic, ""+new Date()+";"+cpt+";"+compteurvalide+";"+compteurvol,false);
	}

	apt.getAttente().setForeground(apt.vert);
	}

private void affichage() {

	System.out.println("time:" + new Date());
	System.out.println("total="+cpt );
	System.out.println("retenu="+ compteurvalide );
	System.out.println("vols="+compteurvol);

	apt.getSuivi().setText(apt.getSuivi().getText() + "\n" +  new Date()
			+ " | " + "total=" + cpt + " | " +"retenu="+ compteurvalide +
			" | "+ " vols="+compteurvol+
			" | "+ " memoire="+ListeData.size()
			);
	apt.getScroller().revalidate();

}

public  void lireFichier(String nom)
{
	System.out.println("lire dans le fichier");
	BufferedReader fe = null;
	try{

		fe = new BufferedReader(new FileReader(nom));

		String tampon = fe.readLine();
		while(tampon != null){
			System.out.println(tampon);
			tampon = fe.readLine();
		}

	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	finally{
		try {
			if(fe != null)
			{
				fe.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public  void ecrireFichier(String nom,String donnee,boolean init){
	System.out.println("ecriture dans un fichier");
	BufferedWriter fs = null;
	try{

		fs = new BufferedWriter(new FileWriter(nom,!init)); // true pour ajouter 
		fs.write(donnee, 0, donnee.length());
		fs.newLine();
		return;

	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	finally{
		try {
			if(fs != null)
			{
				fs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




public ArrayList<DataStat> getListeData() {
	return ListeData;
}

public void setListeData(ArrayList<DataStat> listeData) {
	ListeData = listeData;
}




}




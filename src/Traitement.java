

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



public class Traitement extends Timer{
	
	private TimerTask task;
	

	private ArrayList<DataFull> Tamponsql ;
	private ArrayList<DataStat> ListeData ;
	private DataDAOimpl ADSBbdd;
	
	
	private static long cpt=0;
	private static long compteurvalide=0;
	private static long compteurvol=0;
	
	private int frequence=30;

	private int init=0;
	
	private String nomfic="suivi.txt";
	
	private Appli apt;

	
	
	
	public Traitement(Appli apt) {
		super();
		ListeData = new ArrayList<DataStat>();;
		this.apt=apt;
		ADSBbdd= new DataDAOimpl();
	}


	public void start() {
		if ( task==null ) {
			task = new TimerTask(){		
				public void run(){
					
					connexionFlux();
				}
			};
			scheduleAtFixedRate(task,0,10000);
		}
	}
 
	
	public void stop() {
		if ( task!=null ) {
			task.cancel();
			task=null;
		}
	}

	
	
	

	public void connexionFlux(){


		apt.getConnexion().setForeground(apt.noir);
		apt.getAnalyse().setForeground(apt.noir);
		apt.getRequete().setForeground(apt.noir);
		apt.getAttente().setForeground(apt.noir);
		apt.getConnexion().setForeground(apt.orange);
		URL url;
		
		try {
				
			url = new URL(apt.getAdresseSource());
			
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			recuperationDonnees(con);
			
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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


	private void recuperationDonnees(HttpsURLConnection con){
		if(con!=null){
			try {
				apt.getConnexion().setForeground(apt.vert);
				apt.getAnalyse().setForeground(apt.orange);
				
				BufferedReader br =	new BufferedReader(	new InputStreamReader(con.getInputStream()));
				String input;
				Scanner scan1;
				Tamponsql =new ArrayList<DataFull>();


				while ((input = br.readLine()) != null){
					
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
						
						analyseDonnee(data);

					}
					apt.getAnalyse().setForeground(apt.vert);
					apt.getRequete().setForeground(apt.orange);
					// Ecriture dans la base de donnees
					ADSBbdd.create(Tamponsql);
					apt.getRequete().setForeground(apt.vert);
					
					init=1;	

					frequence++;
					
					verfierExpiration();
					if (frequence>=1)/// on vient ici toute les 30 * 10 s soit 300 s = 5 min
					{
						affichage();
						frequence=0;
						this.ecrireFichier(nomfic, ""+new Date()+";"+cpt+";"+compteurvalide+";"+compteurvol,false);
					}
					
					apt.getAttente().setForeground(apt.vert);

				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	
	private void analyseDonnee(String[] data) {

		DataStat adsb=new DataStat(data[0],0,Boolean.valueOf(data[8]),false);

		int i=0;

		for(i=0;i<data.length;i++)  /// pour enlveler le pb de parse avec la chaine null
		{

			if(data[i].contains("null"))
			{data[i]=new String(""+Integer.MAX_VALUE);}    ///// creation d'une valeur caracteristique du champ null Integer.MAX_VALUE pour pouvoir retouver le champ null

		}


		DataFull adsbstore=new DataFull(data[0],data[1],data[2],Integer.parseInt(data[3]),
				Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]),
				Float.parseFloat(data[7]),Boolean.valueOf(data[8]),Float.parseFloat(data[9]),
				Float.parseFloat(data[10]),Float.parseFloat(data[11]),Float.parseFloat(data[13]),
				data[14],Boolean.valueOf(data[15]),Integer.parseInt(data[16]));

		adsb.setLast_contact(adsbstore.getLast_contact());/// tentative d'indentification de donnee non redondante par le last contact



		int j=0;
		int n=ListeData.size();

		if (init==0) {

			if (adsb.on_ground==false) {

				adsb.setaAjouter(false);
				compteurvol++;

			}

			else adsb.setaAjouter(true);

			ListeData.add(adsb);
			compteurvalide++;
			Tamponsql.add(adsbstore);

		}

		else {

			while (j<n)

			{

				if (ListeData.get(j).getIcao24().equals(adsb.getIcao24()))
				{

					if (ListeData.get(j).getLast_contact()!=adsb.getLast_contact()) {


						if (ListeData.get(j).isaAjouter()==true && adsb.isOn_ground() ==false) {
							adsb.setaAjouter(false);
							compteurvol++;
						}

						else {
							if (ListeData.get(j).isaAjouter()==false && adsb.isOn_ground()==true) {
								adsb.setaAjouter(true);
							}
						}


						compteurvalide++;
						Tamponsql.add(adsbstore);
						ListeData.add(adsb);
						ListeData.remove(j);
					}

					j=n-1;

				}
				else {

					if (j==n-1) {

						if (adsb.isOn_ground()==false) {
							adsb.setaAjouter(false);
							compteurvol++;
						}
						else adsb.setaAjouter(true);
						compteurvalide++;
						Tamponsql.add(adsbstore);
						ListeData.add(adsb);

					}
				}

				j++;
				n=ListeData.size();

			}
		}

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
			//	fs.append(donnee);
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

	public ArrayList<DataFull> getTamponsql() {
		return Tamponsql;
	}

	public void setTamponsql(ArrayList<DataFull> tamponsql) {
		Tamponsql = tamponsql;
	}



	public ArrayList<DataStat> getListeData() {
		return ListeData;
	}

	public void setListeData(ArrayList<DataStat> listeData) {
		ListeData = listeData;
	}

	public static long getCpt() {
		return cpt;
	}

	public static void setCpt(long cpt) {
		Traitement.cpt = cpt;
	}




}

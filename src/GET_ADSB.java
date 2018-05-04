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
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public   class GET_ADSB extends Timer{

	protected TimerTask task;					//Objet de Tache periodique 

	protected ArrayList<DataStat> ListeData ;	//Memoire du logiciel permettand de faire les stats (vol..etc) et le filtrage des donnees redondantes
	protected static DataDAOimpl ADSBbdd;     // reference a l'object de communication a la base de donnee (Modele singleton )


	protected long cpt=0;				//Ce compteur compte le nombre de donnees total recupere par l'objet
	protected long compteurvalide=0;	//Ce compteur compte le nombre de donnne non redondantes 
	protected long compteurvol=0;		//Ce compteur compte le nombre de vols

	protected int frequence=30;				//variable de comptage du temps à la seconde

	public long flux_periode=10;			//ici on regle la periode a laquelle on se connecte au flux de donnees en seconde


	protected String nomfic="suivi.csv";		//Ici on precise le nom du fichier du suivi csv pour les stats
	protected int fich_periode=1;			//ici on regle la periode a laquelle on va ecrire les stats dans le fichier ci dessus en seconde

	protected double retbdd=0;

	protected Appli apt;						// Classe de la Vu concerne
	protected vue4d vue4;						// Classe de la Vu concerne
	protected IParsingStrategy parsestrategy;



	public GET_ADSB(Appli apt,IParsingStrategy parsestrategy) {
		super();

		ListeData = new ArrayList<DataStat>();;
		this.apt=apt;
		this.parsestrategy=parsestrategy;
		if (ADSBbdd == null)			// singleton PATTERN
			ADSBbdd= new DataDAOimpl();		// on instancie l'object que si il ne l'est pas deja 
		ecrireFichier(this.nomfic, "time;total;exploitable;vols;memoire;retbdd",true);/// initialisation du fichier de suivi stat


	}
	public GET_ADSB(Appli apt) {
		super();

		ListeData = new ArrayList<DataStat>();;
		this.apt=apt;
		if (ADSBbdd == null)			// singleton PATTERN
			ADSBbdd= new DataDAOimpl();		// on instancie l'object que si il ne l'est pas deja 
		ecrireFichier(this.nomfic, "time;total;exploitable;vols;memoire;retbdd",true);/// initialisation du fichier de suivi stat


	}
	public GET_ADSB(vue4d apt) {
		super();

		this.vue4=apt;
		if (ADSBbdd == null)			// singleton PATTERN
			ADSBbdd= new DataDAOimpl();		// on instancie l'object que si il ne l'est pas deja 


	}


	public void start()
	{
		if ( task==null ) {
			task = new TimerTask(){		
				public void run(){

					frequence++;
					
					apt.getStart().setForeground(apt.noir);
					apt.getStop().setForeground(apt.orange);

					apt.getConnexion().setForeground(apt.noir);
					apt.getRecuperation().setForeground(apt.noir);
					apt.getParse().setForeground(apt.noir);
					apt.getAnalyse().setForeground(apt.noir);
					apt.getRequete().setForeground(apt.noir);
					apt.getSavestat().setForeground(apt.noir);
					apt.getVerifierexp().setForeground(apt.noir);
					apt.getAttente().setForeground(apt.noir);

					apt.getConnexion().setForeground(apt.orange);
					HttpsURLConnection con=connexionFlux();
					apt.getConnexion().setForeground(apt.vert);

					apt.getRecuperation().setForeground(apt.orange);
					String input=recuperationDonnees(con);
					apt.getRecuperation().setForeground(apt.vert);

					apt.getParse().setForeground(apt.orange);
					//ArrayList<DataStat> input_parse=parse_input(input);
					ArrayList<DataStat> input_parse=parsestrategy.parse_input(input);
					apt.getParse().setForeground(apt.vert);
					
					cpt=cpt+input_parse.size();

					apt.getAnalyse().setForeground(apt.orange);
					ArrayList<DataFull> tampon_sql;
					if(ListeData.size()==0)
						tampon_sql=	initialision_analyse(input_parse);
					else	
					{
						comptagevol(input_parse);
						tampon_sql=analyseDonnee(input_parse);
					}
					apt.getAnalyse().setForeground(apt.vert);

					apt.getRequete().setForeground(apt.orange);
					retbdd=retbdd+ADSBbdd.create(tampon_sql);
					apt.getRequete().setForeground(apt.vert);

					apt.getSavestat().setForeground(apt.orange);
					write_stat();
					apt.getSavestat().setForeground(apt.vert);

					apt.getVerifierexp().setForeground(apt.orange);
					verfierExpiration();
					apt.getVerifierexp().setForeground(apt.vert);



					apt.getAttente().setForeground(apt.vert);
					

				}
			};
			super.scheduleAtFixedRate(task,new Date(),flux_periode*1000) ;}
	}

	public void stop() {
		if ( task!=null ) {
			task.cancel();
			task=null;

			apt.getStart().setForeground(apt.vert);
			apt.getStop().setForeground(apt.noir);

		}
	}





	public HttpsURLConnection connexionFlux(){


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

	//protected abstract ArrayList<DataStat> parse_input (String input);


	private ArrayList<DataFull> initialision_analyse(ArrayList<DataStat> data)

	{
		ArrayList<DataFull> Tamponsql =new ArrayList<DataFull>();

		int taille_input=data.size();
		int ptr_data=0;
		for (ptr_data=0;ptr_data < taille_input;ptr_data++)
		{

			if (data.get(ptr_data).on_ground==false) {

				data.get(ptr_data).setaAjouter(false);
				compteurvol++;

			}

			else data.get(ptr_data).setaAjouter(true);

			ListeData.add(data.get(ptr_data));
			compteurvalide++;
			Tamponsql.add((DataFull)data.get(ptr_data));
		}

		return Tamponsql;
	}


	private void	comptagevol(ArrayList<DataStat> data)
	{
		int taille_input=data.size();
		int ptr_data=0;
		for (ptr_data=0;ptr_data < taille_input;ptr_data++)
		{
			int j=0;
			int n=ListeData.size();
			while (j<n)

			{
				if (ListeData.get(j).getIcao24().equals(data.get(ptr_data).getIcao24()))
				{

					if (ListeData.get(j).getLast_contact()!=data.get(ptr_data).getLast_contact())
					{
						if (ListeData.get(j).isaAjouter()==true && data.get(ptr_data).isOn_ground() ==false) 
						{
							data.get(ptr_data).setaAjouter(false);
							compteurvol++;
						}

						else if (ListeData.get(j).isaAjouter()==false && data.get(ptr_data).isOn_ground()==true) 
						{
							data.get(ptr_data).setaAjouter(true);

						}
					}

					j=n-1;

				}
				else if (j==n-1)
				{

					if (data.get(ptr_data).isOn_ground()==false)
					{
						data.get(ptr_data).setaAjouter(false);
						compteurvol++;
					}
					else data.get(ptr_data).setaAjouter(true);
				}

				j++;
				n=ListeData.size();
			}
		}


	}



	private ArrayList<DataFull> analyseDonnee(ArrayList<DataStat> data) {

		ArrayList<DataFull> Tamponsql =new ArrayList<DataFull>();

		int taille_input=data.size();
		int ptr_data=0;
		for (ptr_data=0;ptr_data < taille_input;ptr_data++)
		{
			int j=0;
			int n=ListeData.size();
			while (j<n)

			{
				
				if (ListeData.get(j).getIcao24().equals(data.get(ptr_data).getIcao24()))
				{
					ListeData.get(j).setTTL(ListeData.get(j).getTTL()+1);
					

					if (ListeData.get(j).getLast_contact()!=data.get(ptr_data).getLast_contact())
					{
						compteurvalide++;
						Tamponsql.add((DataFull)data.get(ptr_data));
						ListeData.add(data.get(ptr_data));
						ListeData.remove(j);
					}
					j=n-1;
				}
				else if (j==n-1)
				{

					compteurvalide++;
					Tamponsql.add((DataFull)data.get(ptr_data));
					ListeData.add(data.get(ptr_data));
				}
				
				j++;
				n=ListeData.size();
			}
		}
		return Tamponsql;
	}






	private void write_stat()
	{
		if (frequence>=fich_periode)/// on vient ici toute les 30 * 10 s soit 300 s = 5 min
		{
			affichage();
			frequence=0;
			this.ecrireFichier(nomfic, ""+new Date()+";"+cpt+";"+compteurvalide+";"+compteurvol+";"+ListeData.size()+";"+retbdd,false);
		}

	}

	private void affichage() {

		System.out.println("time:" + new Date());
		System.out.println("total="+cpt );
		System.out.println("retenu="+ compteurvalide );
		System.out.println("vols="+compteurvol);

		apt.getSuivi().setText(apt.getSuivi().getText() + "\n" +  new Date()
		+ "|" + "total=" + cpt + "|" +"retenu="+ compteurvalide +
		"|"+ "vols="+compteurvol+
		"|"+ "memoire="+ListeData.size()+
		"|"+ "retbdd="+retbdd
		
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


	public void requete4d()
	{
		String requetesql;
		requetesql=ADSBbdd.request(vue4.getLongitude_lo().getText(), vue4.getLongitude_hi().getText(), vue4.getLatitude_lo().getText(), vue4.getLatitude_hi().getText(),
				vue4.getAltitude_lo().getText(),vue4.getAltitude_hi().getText(),
				vue4.getDate_lo().getText(),vue4.getDate_hi().getText(), vue4.getGeo_altitude().isSelected());
		vue4.getTxtrApercu().setText("");
		vue4.getTxtrApercu().append(requetesql);
		
		ArrayList<DataFull> donnee=ADSBbdd.findsql(requetesql+"\n");
		
		
		if (donnee.size()!=0)
		{
			String tampon=donnee.get(0).headCsv();
			for (int i = 0; i < donnee.size(); i++)
			{
				DataFull ligne = donnee.get(i);
				tampon=tampon+ligne.toCsv();
			}
		ecrireFichier(vue4.getTxtFichcsv().getText(), tampon,true);///Ecriture dans fichier avec creation
		vue4.getTxtrApercu().append("\nnombre de ligne trouve="+donnee.size() +"\n"+"\n");
		vue4.getTxtrApercu().append(tampon);
		
		
		}
		



	}

	public ArrayList<DataStat> getListeData() {
		return ListeData;
	}

	public void setListeData(ArrayList<DataStat> listeData) {
		ListeData = listeData;
	}




}




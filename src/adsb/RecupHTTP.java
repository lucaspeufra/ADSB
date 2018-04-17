package adsb;

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
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JTextArea;

public class RecupHTTP extends TimerTask  {

	private ArrayList<DataFull> Tamponsql ;
	public RecupHTTP(JTextArea textArea, ArrayList<DataStat> listeData, String https_url) {
		super();
		ListeData = listeData;
		this.https_url = https_url;
		}

	private ArrayList<DataStat> ListeData = new ArrayList<DataStat>();
	private static double cpt=0;
	private static double compteurvalide=0;
	private static double compteurvol=0;
	private int frequence=30;
	private String https_url;
	private int init=0;
	private String nomfic="suivi.txt";
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("Start time:" + new Date());
		testIt();
		//	System.out.println("End time:" + new Date());



	}

	private void testIt(){

		// https_url = "https://opensky-network.org/api/states/all";
		//	String https_url = "https://www.google.com/";

		URL url;
		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			//dumpl all cert info
			//			print_https_cert(con);

			//dump all the content
			print_content(con);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}





	public RecupHTTP(ArrayList<DataStat> listeData1, String https_url1) {
		super();
		ListeData = listeData1;
		this.https_url = https_url1;
		}

	public ArrayList<DataStat> getListeData() {
		return ListeData;
	}

	public void setListeData(ArrayList<DataStat> listeData) {
		ListeData = listeData;
	}

	public static double getCpt() {
		return cpt;
	}

	public static void setCpt(double cpt) {
		RecupHTTP.cpt = cpt;
	}

	public String getHttps_url() {
		return https_url;
	}

	public void setHttps_url(String https_url) {
		this.https_url = https_url;
	}

	private void print_content(HttpsURLConnection con){
		if(con!=null){
			try { 			 
				BufferedReader br =	new BufferedReader(	new InputStreamReader(con.getInputStream()));
				String input;
				Scanner scan1;
				DataDAOimpl ADSBbdd= new DataDAOimpl();
				Tamponsql =new ArrayList<DataFull>();

				//Pattern delimiter= new 

				while ((input = br.readLine()) != null){
					/*	scan2=new Scanner(input);
+					scan2.useDelimiter(":");
+					scan2.next();
+					scan2.next();
+					scan2.reset();
+					input =scan2.next();*/
					//input=input.replace(']',' ');
					input=input.replace("[","");
					input=input.replace("}","");
					input=input.replace('"', ' ');
					input=input.replace("'", "-");/// tentative d'elimination du pb de requete sql
						
					input=input.replace(" ","");
					
					scan1=new Scanner(input);
					//System.out.println(input);
					scan1.useDelimiter(":");
					scan1.next();/// elimination du premier champs inutile
					scan1.next();
					scan1.reset();
					scan1.useDelimiter("],");
					
					//System.out.println(scan1.next());
					//scan1.next();  debug
					while(scan1.hasNext())	{
						
						//	System.out.println(cpt+"--"+scan1.next() );
						//	this.textPane_1.setText(cpt+"--"+scan1.next()+"\n");
						//	this.textArea.append(cpt+"--"+scan1.next()+"\n");
						cpt++;

						//	System.out.println(cpt+"--"+scan1.next() );
						//	this.textPane_1.setText(cpt+"--"+scan1.next()+"\n");

						String ligne =scan1.next();
					
						if (ligne.contains(":"))
							ligne=ligne.replace(":", " "); /// traitement du caracter : de debut
							
						
						if (ligne.contains("]"))
						ligne=ligne.replace("]", ""); /// traitement du caracter  de fin
						
					
						
						//	this.textArea.append(cpt+"--"+ligne+"\n");
						String [] data=ligne.split(",");
						//	this.textArea.append(cpt+"-x-");
						/*		for (String str : data)
					{
						this.textArea.append(str+"-x-");
					}
					this.textArea.append("\n");*/

						//pour tst
					
						DataStat adsb=new DataStat(data[0],0,Boolean.valueOf(data[8]),false);
						int i=0;
						
						for(i=0;i<data.length;i++)  /// pour enlveler le pb de parse avec la chaine null
						{
							if(data[i].contains("null"))
							{data[i]=new String(""+Integer.MAX_VALUE);}    ///// creation d'une valeur caracteristique du champ null Integer.MAX_VALUE pour pouvoir retouver le champ null
							//System.out.println(data[i]);
						}
						
						
						
						DataFull adsbstore=new DataFull(data[0],data[1],data[2],Integer.parseInt(data[3]),
								Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]),
								Float.parseFloat(data[7]),Boolean.valueOf(data[8]),Float.parseFloat(data[9]),
								Float.parseFloat(data[10]),Float.parseFloat(data[11]),Float.parseFloat(data[13]),
								data[14],Boolean.valueOf(data[15]),Integer.parseInt(data[16]));
						
						
						
	/*				a	if (!data[3].equals("null"))
						{
							adsb.setTime_position(Integer.parseInt(data[3]));
						}*/

						//						int i=0;
						//						int aut=1;
						//						for (i=0;i<ListeData.size();i++) {
						//							if (ListeData.get(i).compareTo(adsb)==0)
						//							{
						//								i=ListeData.size();
						//								aut=0;
						//							}
						//						}
						//						if (aut==1)
						//						{
						//							ListeData.add(adsb);
						//						}
						//	this.textArea.append(adsb +"\n");


						int j=0;
						int n=ListeData.size();
						if (init==0) {
							if (adsb.isAuSol()==false) {
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

									if (ListeData.get(j).getTime_position()!=adsb.getTime_position()) {
										
										
										if (ListeData.get(j).isaAjouter()==true && adsb.isAuSol() ==false) {
											adsb.setaAjouter(false);
											compteurvol++;
											System.out.println(adsb.getIcao24());
										}
										
										else {
											if (ListeData.get(j).isaAjouter()==false && adsb.isAuSol()==true) {
												adsb.setaAjouter(true);
											}
										}
										
										
										compteurvalide++;
										Tamponsql.add(adsbstore);
										ListeData.add(adsb);
										ListeData.remove(j);
									}
									
									

//									supprimer.add(j);
									j=n-1;

								}
								else {
									
									if (j==n-1) {

										if (adsb.isAuSol()==false) {
											adsb.setaAjouter(false);
											compteurvol++;
											}
										else adsb.setaAjouter(true);
										compteurvalide++;
										Tamponsql.add(adsbstore);
										ListeData.add(adsb);
									}
								}
								//System.out.println(adsb.getIcao24() + " "+j);

								//System.out.println(ListeData.get(j) +" " +j );
								j++;

							}
						}

					}
					// Ecriture dans la base de donnees
					ADSBbdd.create(Tamponsql);

					init=1;	
//					int i;
//
//					for (i=0;i<supprimer.size();i++) {
//						ListeData.remove((int)supprimer.get(i));
//					}

					frequence++;
					if (frequence>=30)/// on vient ici toute les 30 * 10 s soit 300 s = 5 min
					{
						System.out.println("time:" + new Date());
						System.out.println("total="+cpt );
						System.out.println("retenu="+ compteurvalide );
						System.out.println("vols="+compteurvol);
						frequence=0;
						this.ecrireFichier(nomfic, ""+new Date()+";"+cpt+";"+compteurvalide+";"+compteurvol,false);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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


	
	


}

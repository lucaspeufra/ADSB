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

	private JTextArea textArea;
	public RecupHTTP(JTextArea textArea, ArrayList<DataStat> listeData, String https_url) {
		super();
		this.textArea = textArea;
		ListeData = listeData;
		this.https_url = https_url;
		this.textArea.append("Time;donnee totale;donnee non redondante");
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


				//Pattern delimiter= new 

				while ((input = br.readLine()) != null){
					/*	scan2=new Scanner(input);
+					scan2.useDelimiter(":");
+					scan2.next();
+					scan2.next();
+					scan2.reset();
+					input =scan2.next();*/
					scan1=new Scanner(input);

					scan1.useDelimiter("],");
					//System.out.println(scan1.next());
					scan1.next();
					while(scan1.hasNext())	{
						//	System.out.println(cpt+"--"+scan1.next() );
						//	this.textPane_1.setText(cpt+"--"+scan1.next()+"\n");
						//	this.textArea.append(cpt+"--"+scan1.next()+"\n");
						cpt++;

						//	System.out.println(cpt+"--"+scan1.next() );
						//	this.textPane_1.setText(cpt+"--"+scan1.next()+"\n");

						String ligne =scan1.next();
						//	this.textArea.append(cpt+"--"+ligne+"\n");
						String [] data=ligne.split(",");
						//	this.textArea.append(cpt+"-x-");
						/*		for (String str : data)
					{
						this.textArea.append(str+"-x-");
					}
					this.textArea.append("\n");*/

						//pour tst
						String nl="0";
						DataStat adsb=new DataStat(data[0],0,Boolean.getBoolean(data[8]),false);
						int i=0;
						
						for(i=0;i<data.length;i++)  /// pour enlveler le pb de parse avec la chaine null
						{
							if(data[i].contains("null"))
							{data[i]=new String("0");}
							//System.out.println(data[i]);
						}
						
						
						
						DataFull adsbstore=new DataFull(data[0],data[1],data[2],Integer.parseInt(data[3]),
								Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]),
								Float.parseFloat(data[7]),Boolean.getBoolean(data[8]),Float.parseFloat(data[9]),
								Float.parseFloat(data[10]),Float.parseFloat(data[11]),Float.parseFloat(data[13]),
								data[14],Boolean.getBoolean(data[15]),Integer.parseInt(data[16]));
						ADSBbdd.create(adsbstore);
						
						
	/*					if (!data[3].equals("null"))
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
										ListeData.add(adsb);
									}
								}
								//System.out.println(adsb.getIcao24() + " "+j);

								//System.out.println(ListeData.get(j) +" " +j );
								j++;

							}
						}

					}


					init=1;	
//					int i;
//
//					for (i=0;i<supprimer.size();i++) {
//						ListeData.remove((int)supprimer.get(i));
//					}

					frequence++;
					if (frequence>=1)
					{
						System.out.println("time:" + new Date());
						System.out.println("total="+cpt );
						System.out.println("retenu="+compteurvalide );
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


	
	


}

package com.abreuretto.busaopaulista;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.abreuretto.buddysearchnearby.BuddySearchNearBy;
import com.abreuretto.buddysearchnearby.Datum;


import com.abreuretto.buddysearchnearby.PegaDadosSearchByListener;
import com.abreuretto.buddysearchnearby.PesqBuddySearchBy;
import com.abreuretto.busaopaulista.R;
import com.abreuretto.busmoni.BusMoni;
import com.abreuretto.busmoni.BusMoniSPTrans;
import com.abreuretto.busmoni.BusMoniSPTransListener;
import com.abreuretto.busmoni.L;
import com.abreuretto.googleplaces.ListaGoogle;




public class MostraLinhaActivity extends Activity implements BusMoniSPTransListener, PegaDadosSearchByListener {
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	  SharedPreferences shared_preferences;
	  SharedPreferences.Editor shared_preferences_editor;
  String latx = null;
  String lonx = null;
  String parada = null;
  String nome = null;
  String sentidox = null;
  String sentidoy = null;
  int sentido = 0;
  String latparada = null;
  String lonparada = null;
  
  ConnectionDetector cd;
	 private ProgressDialog dialog;
	 AlertDialogManager alert = new AlertDialogManager();

  GlobalClass gc = null;
	
  ArrayList<AppMapa> lista = new ArrayList<AppMapa>();

	

  
  
  
  // Constants
	
    public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
	public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
	public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above

	public final static String api_pass = "&apiCredentials-v0=7B94AF287D4B9DFB8738E83F05EF0624F739210ED2053AF75F52C7B12945EF5815DB63B481787B2BC1E703D33151022F13B8BC86E429BB9C410844EC773DFC8C81D7BAA4A7C74734BD0ECE2BF8CEDAA0DE4E369847CC1445F2F8ACEBDBE1498928DCE0992D3C06B721816EA9C4A1E479F587547E1E2C61F9791C85CCD162108B1035B1651DA3542535881A09C48AE034784B75BA05965CA2ECBD65D7DA4EBDAF99EB7BFFE94DE7C5AAB9E1687D1BD2046464E7FDCA06FA0373F08396E05D18C042414FEF811064FCD318922A231F8AE8";
	
	

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_linha);
		 
		
		dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.linhasparada));
		
        shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
	    latx = shared_preferences.getString("lat", "");
	    lonx = shared_preferences.getString("lon", "");
	    
	    Bundle extras = getIntent().getExtras();
	    parada = extras.getString("parada");
	    nome = extras.getString("nome");
	    sentidox = extras.getString("sentido");
	    latparada = extras.getString("latparada");  
	    lonparada = extras.getString("lonparada");
	    
	  
	    
		
	    cd = new ConnectionDetector(getApplicationContext());
		
	    
	       
	    
	    
	    
		
		
		
		  expListView = (ExpandableListView) findViewById(R.id.lvExp);
		  
		  
		  
		  
	        expListView.setOnGroupClickListener(new OnGroupClickListener() 
	        {
	 
	            @Override
	            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) 
	            {
	                // Toast.makeText(getApplicationContext(),
	                // "Group Clicked " + listDataHeader.get(groupPosition),
	                // Toast.LENGTH_SHORT).show();
	                return false;
	            }
	        });
	 
	        // Listview Group expanded listener
	        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
	 
	            @Override
	            public void onGroupExpand(int groupPosition) {
	             
	            	/*
	            	Toast.makeText(getApplicationContext(),
	                        listDataHeader.get(groupPosition) + " Expanded",
	                        Toast.LENGTH_SHORT).show();
	                        */
	            }
	        });
	 
	        // Listview Group collasped listener
	        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
	 
	            @Override
	            public void onGroupCollapse(int groupPosition) {
	            	
	            	/*
	                Toast.makeText(getApplicationContext(),
	                        listDataHeader.get(groupPosition) + " Collapsed",
	                        Toast.LENGTH_SHORT).show();
	                        */
	 
	            }
	        });
	 
	        // Listview on child click listener
	        expListView.setOnChildClickListener(new OnChildClickListener() {
	 
	            @Override
	            public boolean onChildClick(ExpandableListView parent, View v,
	                    int groupPosition, int childPosition, long id) {
	                // TODO Auto-generated method stub
	                
	            	
	            	
	            	String linhaw = listDataHeader.get(groupPosition);
	            	String carrow = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
	            	
	            	
	            	int achou = linhaw.indexOf(" | ");
	            	int achoucarro = carrow.indexOf("previsão");
	            	
	            	if (achoucarro >= 0) 
	            		
	            	{
	            	
	            	
	            	
	            	String linha = linhaw.substring(achou+3, achou+10);
	            	String carro = carrow.substring(0, achoucarro-1);
	            	
	          
    		        
    		        Intent nextScreen = new Intent(MostraLinhaActivity.this, MapaMonitoraActivity.class);
    		        nextScreen.putExtra("parada", "" + parada);
     		        nextScreen.putExtra("linha", "" + linha);
     		        nextScreen.putExtra("carro", "" + carro );
     		        nextScreen.putExtra("nomeparada", "" + nome);
     		        nextScreen.putExtra("latparada", "" + latparada );
     		        nextScreen.putExtra("lonparada", "" + lonparada );
     		        nextScreen.putExtra("letreiro", "" + linhaw );
    		        
    		        startActivityForResult(nextScreen, 0);
    		        
    		        
	            	}
    		        
    		        
	            	
	            	
	            	
	            	
	                return false;
	            }
	        });
	        
	        
	        
	        
	        
	        
	
	       

	        
	        
	        PegaFS();   
	       
	        if (!cd.isConnectingToInternet()) 
	        {
		           AlertDialog.Builder builder = new AlertDialog.Builder(this);
		           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
		               .setMessage(this.getResources().getString(R.string.interneterror))
		               .setCancelable(false)
		               .setIcon(R.drawable.ic_launcher)
		               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   MostraLinhaActivity.this.finish();
		                   }
		               });
		             builder.create().show(); 
		             dialog.dismiss(); 
		             finish();
		           return;
		       }    
	        
	    }
	 
	     
	    private void prepareListData() {
	    	
	    	  TextView titleText = (TextView)findViewById(R.id.tnome);
	    	  String monta = nome.replace("[1]", "");
	    	  String montax = monta.replace("[0]", "");
	    	  
		      titleText.setText(montax);
		      
		      int textocolor1 = getResources().getColor(R.color.onibus1);
		      int textocolor2 = getResources().getColor(R.color.onibus2);

		      
		      ImageView ima = (ImageView) findViewById(R.id.imauser);
		      
		      if (sentido == 1) {
		      ima.setImageResource(R.drawable.ponto1);
		      titleText.setTextColor(textocolor1);
		      }  
		      else
		      {
		    	  ima.setImageResource(R.drawable.ponto2);
		    	  titleText.setTextColor(textocolor2);
		      }

		      if (listDataHeader.size() == 0)
    		  {
    		   Toast.makeText(MostraLinhaActivity.this, "Sem informações nas proximidades para esta parada. Tente mais tarde! ", Toast.LENGTH_LONG).show();
    		   dialog.dismiss(); 
    		   return;
    		   
    		  }
		      
		      for(int j=0; j < listDataHeader.size(); j++) 
	    	  {
		    	
		    	  try
		    	  {
		    	 String respo =   listDataChild.get(listDataHeader.get(j)).get(0);
		    	 
		    	 
		    			    	 
		    	 if (respo.equals(null))
		    	 {
		    		 listDataHeader.remove(j);
		    		 if (listDataHeader.size() == 0)
		    		  {
		    		   Toast.makeText(MostraLinhaActivity.this, "Sem informações  nas proximidades para esta parada. Tente mais tarde! ", Toast.LENGTH_LONG).show();
		    		   
		    		  }
		    	 }
		    	  
		    	  }
		    	  catch (Exception e)
		    	  {
		    		  listDataHeader.remove(j);
		    		  
		    		  if (listDataHeader.size() == 0)
		    		  {
		    		   Toast.makeText(MostraLinhaActivity.this, "Sem informações nas proximidades para esta parada. Tente mais tarde! ", Toast.LENGTH_LONG).show();
		    		  
		    		  }
		    	  }
		    	  
		    	  dialog.dismiss(); 
		    	 
		    	  
	    	  }
		      
		      
		      
		      
		      
	    	listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, sentido);
	    	expListView.setAdapter(listAdapter); 
	    	
	    	for (int i = 0; i < listAdapter.getGroupCount(); i++)
	    	{
	    		
	    		String pega =  listAdapter.getChild(i, 0).toString();
	    		if (pega.indexOf("Linha sem monitoramento") == -1)
	    		expListView.expandGroup(i);
	    	}
	    	
	   	 if(dialog != null)  dialog.dismiss();
	       
	    	
	    }	    
	    
	    
	    
	    
	    
	    
	    
	    protected void PegaParada(String parada)
		{		

		
			
			
			
		   //	dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoparadaslinha));
			
			/*
			 String monta = linhaw.replaceAll(" ", "%20");	
			 String url = "http://api.olhovivo.sptrans.com.br/v0/Parada/BuscarParadasPorLinha?codigoLinha="+monta;    
			 PesqParadaSpTrans task3 = new PesqParadaSpTrans(this);
			 task3.execute(url);
			
			*/
			
			
			  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_SearchData";        // API Call
			  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
			  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
			  finalURLStr = finalURLStr + "&SearchDistance=40075000"; // Search distance (int)
			  finalURLStr = finalURLStr + "&Latitude=-1";                        // Location latitude
			  finalURLStr = finalURLStr + "&Longitude=-1";                      // Location longitude
			  finalURLStr = finalURLStr + "&RecordLimit=" + "5500";        // Max number of records (int)
			  finalURLStr = finalURLStr + "&MetaKeySearch="+encodeValue("rota_"+parada+"%") ;          //  
			  finalURLStr = finalURLStr + "&MetaValueSearch=";      //
			  finalURLStr = finalURLStr + "&TimeFilter=-1" ;            // Time in minutes since last update
			  finalURLStr = finalURLStr + "&MetaValueMin=";         // Minimum numeric value
			  finalURLStr = finalURLStr + "&MetaValueMax=";         // Maximum numeric value
			  finalURLStr = finalURLStr + "&SearchAsFloat=" + 0;                          // Search as numeric value
			  finalURLStr = finalURLStr + "&SortResultsDirection=";   // Sort direction (1=ascending)
			  finalURLStr = finalURLStr + "&DisableCache=" + "true";                      // Cache use flag (true or empty)
			  String url = finalURLStr.toString();		
			  PesqBuddySearchBy  pontotask = new PesqBuddySearchBy(this);
			  pontotask.execute(url,latx,lonx);
			  
			 
			 
		}	

	    
	    
	    public void onPegaDadosSearchByComplete(List<BuddySearchNearBy> paradabuddy)
		 {
			
			
			 
			 if (paradabuddy.equals(null))
       	 {
       		 
				  if(dialog != null)  dialog.dismiss();
       		 return;
       		 
       	 }
			 
			 
			 
			 
       	 for(int j=0; j< paradabuddy.size(); j++) 
      	      {
       		 BuddySearchNearBy paradas =  paradabuddy.get(j);
       		 
       		 List<Datum> listano =   paradas.getData();
       		List<String> top251 = new ArrayList<String>();
	    	
       		
       		 for(int i=0; i< listano.size(); i++) 
         	      {
       			 
       			 Datum no = listano.get(i);
       			 
       			ListaGoogle buddy = new ListaGoogle();
       			buddy.setDistancia(Double.parseDouble(no.getDistanceInMeters()));
       			buddy.setLat(no.getMetaLatitude());
       			buddy.setLon(no.getMetaLongitude());
       			String monta = no.getMetaKey().toString();
       			int tama = monta.length();
       			String cod = monta.replace("rota_", "");
       			buddy.setCodigoparada(parada);       			
       			String monta2 = no.getMetaValue().toString();
       			
       			String codlinha = monta2.substring(0,7);
     			String sentido  = monta.substring(tama,tama);
     		    String[] items = monta2.split("@");
                String nome =  items[0].trim();
                String ende = items[1].trim();
                int achou =  ende.indexOf("$$");
                String endex = ende.substring(0,achou-1);
     			 
     			String headernome = (endex+" | "+codlinha);  		  		    
   		  		
     			 listDataHeader.add(headernome);
     			 
     			 top251.clear();
		  			
  			     listDataChild.put(headernome, new ArrayList());
    	         top251.add("Linha sem monitoramento");
	  			 listDataChild.get(headernome).add("Linha sem monitoramento");
    				 
    				 
    				 
    				 
		    	  
    	
     			 
     			 
   		  	
 	        	 
       		    buddy.setNomeparada(nome);
       		    buddy.setEndereco(ende);
       		  // 	listabuddy.add(buddy); 
       			 
       					 
         	      }
       		 
       		 
       		 
      	      
			 
       	 
       	 
		  	 
      	 
  		 
  		 
  		 
  	  }
  	
  	  Collections.sort(listDataHeader);
  	
       	 
       	 
       	 
       	 
       	 
			 
       	 if(dialog != null)  dialog.dismiss();
       //	 VerificaListas();
       	 
       	 
       	 
       	 
       	prepareListData();
			 
			 
		 }
		 
		 public void onPegaDadosSearchByFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
		 
	
	    
	    
	    
	    
	    public void onBusMoniSPTransComplete(List<BusMoni> busmoni)
		 {
	    	
	    	
	    	String codlinha = null;
	    	int sentido  = 0;
	    	String letreiro0 = null;
	    	String letreiro1 = null;
	    	listDataHeader = new ArrayList<String>();
	    	listDataChild = new HashMap<String, List<String>>();
	    	List<String> top250 = new ArrayList<String>();
	    	String headernome = null;
	    	boolean passou = false;
	    	
	    	
	    	 for(int j=0; j < busmoni.size(); j++) 
	    	  {
	    		 
	    		 
	    		 BusMoni listano = busmoni.get(j);	    		 
	    		 com.abreuretto.busmoni.P  Pno =  listano.getP();
	    		 
	    		 List<L>  Listalinhas;
	    		 
	    		 try
	    		 {
	    		 Listalinhas =  Pno.getL();
	    		 }
	    		 catch (Exception e)
	    		 {
	    			 passou = true;
	    			 break;
	    		 }
	    		 
	    		 
	    		 for(int i=0; i < Listalinhas.size(); i++) 
		    	  {
		    	
	    			 com.abreuretto.busmoni.L linhas = Listalinhas.get(i);
	    			 
	    			 codlinha = linhas.getC().toString();
	    			 sentido  = linhas.getSl();
	    			 letreiro0 = linhas.getLt0().toString();
	    			 letreiro1 = linhas.getLt1().toString();
	    			 
	    			 
	    			 if (sentido == 1) 
			  		    {
			  		    	headernome = (letreiro0+" | "+codlinha);	
			  		    }  
			  		    else				  		    	
			  		    {
			  		    	headernome = (letreiro1+" | "+codlinha);
			  		    
			  			} 
			  	
	    			 listDataHeader.add(headernome);
			  			  
	    			 
	    			 
	    			 List<com.abreuretto.busmoni.V>  ListaVeiculos =   linhas.getVs();
	    			 
	    			 
		  			 top250.clear();
			  			
	  			     listDataChild.put(headernome, new ArrayList());
	    			 
	    			 for(int k=0; k < ListaVeiculos.size(); k++) 
			    	  {
	    				 
	    				 com.abreuretto.busmoni.V veiculos = ListaVeiculos.get(k);
	    				 
		  				  boolean defi = veiculos.getA();
		  				  String cadeira = null;
		  				  if (defi==true) {
		  					cadeira = "SIM";  
		  				  }
		  				  else
		  				  {
		  					cadeira = "NÃO";  
		  				  }
		  				  String carro = veiculos.getP().toString();
		  				  String tempo = veiculos.getT().toString();  
		  				 
		  				  
		  				  SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                          Date currentDate = new Date();
		  				   String agora  = formatter.format(currentDate.getTime());
		  				   String depois = agora.substring(0,10)+" "+tempo+":00";
		  				   
		  				   
		  				   Date dateagora = null;
						try {
							dateagora = (Date)formatter.parse(agora);
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		  				   Date datedepois = null;
						try {
							datedepois = (Date)formatter.parse(depois);
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		  				   long diff = (datedepois.getTime() - dateagora.getTime())/1000;
		  					  
		  				   
		  				  
		  						  top250.add("Carro: "+carro+" previsão: "+tempo+" Cadeirante: "+cadeira);
		  				  
		  						  listDataChild.get(headernome).add("Carro: "+carro+" previsão: "+tempo+" Cadeirante: "+cadeira);
	    				 
	    				 
	    				 
	    				 
			    	  }
	    			 
	    			 
	    			 
		    	  }	 
	    		 
	    		 
	    		 
	    		 
	    	  }
	    	
	    	  Collections.sort(listDataHeader);
	    	  
	    	  PegaParada(parada);
	    	  
	    	  
	    	  
	    	  
	    	
	    	
		 }
	    
	    public void onBusMoniSPTransFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
	    
	
	    protected void PegaFS()
		{
			
		 String finalURLStr = "http://api.olhovivo.sptrans.com.br/v0/Previsao/Parada?codigoParada="+parada;       // API Call
		 String url = finalURLStr.toString();
		 BusMoniSPTrans task3 = new BusMoniSPTrans(this);
		 task3.execute(url,latx,lonx);
		}
	    
	    
	    
	    

		public String encodeValue(String rawValue) {
			  String result = null;      
			  try {
			    result = URLEncoder.encode(rawValue, "UTF-8") 
			                        .replaceAll("\\%28", "(")
			                        .replaceAll("\\%29", ")")
			                        .replaceAll("\\+", "%20")
			                        .replaceAll("\\%27", "'")
			                        .replaceAll("\\%21", "!")
			                        .replaceAll("\\%7E", "~")
			                        .replaceAll("\\%2A", "*");
			  } catch (UnsupportedEncodingException e) {
			    // Handle the error
			  }
			  return result;
			}  

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_linha, menu);
		return true;
	}

}

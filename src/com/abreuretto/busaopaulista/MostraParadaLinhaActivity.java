package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.abreuretto.buddysearchnearby.BuddySearchNearBy;
import com.abreuretto.buddysearchnearby.Datum;
import com.abreuretto.buddysearchnearby.PegaDadosSearchByListener;
import com.abreuretto.buddysearchnearby.PesqBuddySearchBy;
import com.abreuretto.busaopaulista.R;
import com.abreuretto.googleplaces.ListaGoogle;
import com.abreuretto.googleplaces.PegaBusStation;
import com.abreuretto.pesqlinha.PesqLinhaSPTrans;
import com.abreuretto.pesqparada.PesqParada;
import com.abreuretto.pesqparada.PesqParadaSPTransListener;
import com.abreuretto.pesqparada.PesqParadaSpTrans;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MostraParadaLinhaActivity extends ListActivity implements PegaDadosSearchByListener, PesqParadaSPTransListener

{

		SharedPreferences shared_preferences;
		SharedPreferences.Editor shared_preferences_editor;
		ConnectionDetector cd;
		ProgressDialog dialog;
		AlertDialogManager alert = new AlertDialogManager();
		GoogleMap googleMap;
		//Button novo;
	    
		String latx = null;
	    String lonx = null;
	    String latxflip = null;
	    String lonxflip = null;
	    String msg = null;
	    String letreiro1linha = null;
	    String letreiro2linha = null;
	    String sentidoparada = null;    
	    String paradacodw = null;
	    String paradacod = null;
	    String paradanome = null;
	    String codparada = null;
	    String distanciaw = "500";
	    String linha_clicada = null;
	    String cod_linha = null; 
	    int total = 0;
	    
	    PegaSomenteParada pontoparada3;
		 
	    int conta;
		int registros = 0;
		int contaparada = 0;
	    int sentido  = 0;
		
	    boolean pontolinha = false;
		boolean flip = true;
		boolean terminou = false;
		boolean semonibus_w = false;
		boolean selecionado_w = false;
		double lathx = 0; 
		double lonhy =  0;
		    	 
		ArrayList<EventInfo> listanomapa = new ArrayList<EventInfo>();
	    ArrayList<EventInfo> listaponto = new ArrayList<EventInfo>();
	    ArrayList<EventInfo> listaponto2 = new ArrayList<EventInfo>(); 
	    ArrayList<EventInfo> listapontoQBG = new ArrayList<EventInfo>();
	    
	    
	    
	    HashMap <Marker, EventInfo> ListaObj = new HashMap <Marker, EventInfo>();
    	List<String> listDataHeader;
		HashMap<String, List<String>> listDataChild;
		
	    
	    private HashMap<Marker, EventInfo> eventMarkerMap;
	    private Handler handler = new Handler();
	    
	    
	  //  PegaPontoTask     pontotask;
	  //  PegaPontoParada   pontoparada;
		
	    
	    public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
		public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
		public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
	    
		 
		  
		PesqParadaSpTrans task3; 
		  

		  private Menu theMenu = null;
  
		  
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		
		{
			
			
			super.onCreate(savedInstanceState);
			//setContentView(R.layout.activity_mostra_parada_linha);	
		    
			cd = new ConnectionDetector(getApplicationContext());
				
			
			
			
			       if (!cd.isConnectingToInternet()) 
			       {
			           AlertDialog.Builder builder = new AlertDialog.Builder(this);
			           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
			               .setMessage(this.getResources().getString(R.string.interneterror))
			               .setCancelable(false)
			               .setIcon(R.drawable.ic_launcher)
			               .setPositiveButton("Ok", new DialogInterface.OnClickListener() 
			               {
			                   public void onClick(DialogInterface dialog, int id) 
			                   {
			                	   MostraParadaLinhaActivity.this.finish();
			                   }
			               });
			             builder.create().show();  
			             
			           return;
			
		      	}
			
			
			
			
			
			
			
			
			
			
			
			Inicia();
			
		    
			}	    
		
		  @Override
		  public boolean onCreateOptionsMenu(Menu menu) {
		    theMenu = menu;
		    // reference to /res/menu/mainmenu.xml
		    new MenuInflater(getApplication()).inflate(R.menu.mostra_parada_linha, menu);
		    return (super.onCreateOptionsMenu(menu));
		  }

		  @Override
		  public boolean onOptionsItemSelected(MenuItem item) {
		  
		    // code to handle the menu item tap
		    if (item.getItemId() == R.id.action_mapa) {
		      Toast.makeText(this, "Got your tap", Toast.LENGTH_LONG).show();
		      return true;
		    }

		  // this is how you handle the home button tap (not really needed for this example)
		    if (item.getItemId() == R.id.action_about) {
		      Toast.makeText(this, "Got menu item tap", Toast.LENGTH_LONG).show();
		      return true;
		    }

		    return (super.onOptionsItemSelected(item));
		  }
	    
		
		public void pegaAbout()
		{
	        Intent intent = new Intent(this, SobreActivity.class);
	        startActivity(intent);
		}	

		
		public void PegaLinha(String linha)
		{
			
		}

		
		
		
		
		
		public void onPesqParadaSPTransComplete(List<PesqParada> dataparada)
		 {
			
			
			 
			 if (dataparada.equals(null))
      	 {
      		 
				  if(dialog != null)  dialog.dismiss();
      		 return;
      		 
      	 }
			 
			 
			 
			 
      	 for(int j=0; j< dataparada.size(); j++) 
     	      {
      		 PesqParada paradas =  dataparada.get(j);
      		 
      		 	 
      			EventInfo buddy = new EventInfo();
      			buddy.setaLat(paradas.getLatitude());
      			buddy.setaLon(paradas.getLongitude());
      			String codlinha = linha_clicada;
      			String sentido  = "1";
      			String parada   = paradas.getCodigoParada();
      			String seq      = "1";
            	  String nome      =  paradas.getNome();
                  String ende      =  paradas.getEndereco();
              	buddy.setParadacod(parada);
      			buddy.setLinha(codlinha);      			
      		    buddy.setPonto(nome);
      		    buddy.setLocalizacao(ende);
      		    
      		    
               listaponto.add(buddy);
      			 
      					 
        	      
      		 
      		 
      		 
     	      }
		
      	 
      	 
      	 if (dataparada.size() == 0)
      	 {
      		if(dialog != null)  dialog.dismiss();  
      		Toast.makeText(this, "Sem paradas monitoradas para esta linha segundo SPTRANS.", Toast.LENGTH_LONG).show();
      		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
      		
      		
      		finish();
      		 return;
      		
      	 }
      	 
      	 
      	 /*
      	 if(dialog != null)  dialog.dismiss(); 
			 
		 AdapterParadasLinha adapter = new AdapterParadasLinha(MostraParadaLinhaActivity.this, listaponto);
         adapter.notifyDataSetChanged();         
         setListAdapter(adapter);
			*/
			
			
			
		 }
		
		 		
         public void onPesqParadaSPTransFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
		
		
		
		
		
		public void onPegaDadosSearchByComplete(List<BuddySearchNearBy> paradabuddy)
		 {
			
			listapontoQBG.clear();
			 
			 if (paradabuddy.equals(null))
       	 {
       		 
				  if(dialog != null)  dialog.dismiss();
       		 return;
       		 
       	 }
			 
			 
			 
			 
       	 for(int j=0; j< paradabuddy.size(); j++) 
      	      {
       		 BuddySearchNearBy paradas =  paradabuddy.get(j);
       		 
       		 List<Datum> listano =   paradas.getData();
       		 
       		 
       		 if (listano.size() == 0)
       		 {
       			if(dialog != null)  dialog.dismiss();  
          		Toast.makeText(this, "Sem paradas monitoradas para esta linha segundo SPTRANS.", Toast.LENGTH_LONG).show();
       			 finish();
       			 break;
       		 }
       		 
       		
       		 for(int i=0; i< listano.size(); i++) 
         	      {
       			 
       		    Datum no = listano.get(i);
        		LatLng newLatLng = new LatLng(Double.parseDouble(no.getMetaLatitude()), Double.parseDouble(no.getMetaLongitude()));
       			EventInfo buddy = new EventInfo();
       			buddy.setaLat(Double.parseDouble(no.getMetaLatitude()));
       			buddy.setaLon(Double.parseDouble(no.getMetaLongitude()));
       		    buddy.setLatLong(newLatLng);
       			String monta = no.getMetaKey().toString();
       			String montax = monta.replace("rota_", "");
       			int achou = montax.indexOf("_seq");
       			String montay = montax.substring(0,achou);
       			String seqw = monta.replace("rota_"+montay+"_seq_", "");
       			achou = seqw.indexOf("_");
       			String seq = seqw.substring(0, achou);
       			achou = monta.indexOf("_linha_");
       			
       			String metadesc = no.getMetaValue().toString();
       			int achou3 = metadesc.indexOf("@");
       			achou = metadesc.indexOf("$$");
       			int achou2 = metadesc.indexOf("{}");
       			int tama = metadesc.length();
       			String nome = metadesc.substring(achou+3, achou2-1);
       			String ende = metadesc.substring(achou2+3, tama);
       			String endex = ende.replace("Null", "");
       			String letre = metadesc.substring(achou3+2,achou-1);
       			String linha = metadesc.substring(0,7);
       			buddy.setLinha(linha);
       			buddy.setLetreiro(letre);       			
       		   	Location locationA = new Location("point A");
		        locationA.setLatitude(Double.parseDouble(latx));
		        locationA.setLongitude(Double.parseDouble(lonx));
		        Location locationB = new Location("point B");
		        locationB.setLatitude(buddy.getaLat());
		        locationB.setLongitude(buddy.getaLon());
		        float distance = locationA.distanceTo(locationB);   
		        buddy.setDistyou(distance);					  		   
		  	    String codlinha = linha_clicada;
       			String sentido  = codlinha.substring(8,9);
       			String parada   = montay;
       	       	buddy.setParadacod(parada);
       			buddy.setLinha(codlinha);       			
       		    buddy.setLocalizacao(endex);
       		    buddy.setId(montay);
       		    buddy.setPonto(nome);
       		    buddy.setParadacod(montay);
       		    buddy.setTipo(3);
       		    buddy.setSentidolinha(Integer.parseInt(sentido));
  	        	buddy.setSentido(Integer.parseInt(sentido));
  	        	if (buddy.getSentidolinha() == 0)
  	        	{
  	        		buddy.setLetreiro1(letre);
  	        	}  else
  	        	{
  	        		buddy.setLetreiro2(letre);
  	        	}
  	        	buddy.setParada(1);
       		    
                listapontoQBG.add(buddy);
       			 
       					 
         	      }
       		 
       		 
       		 
      	      }
			 
     
       	 
       	 if (listapontoQBG.size() == 0)
      	 {
      		if(dialog != null)  dialog.dismiss();  
      		Toast.makeText(this, "Sem paradas monitoradas para esta linha segundo SPTRANS.", Toast.LENGTH_LONG).show();
      		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
      		
      		
      		finish();
      		 return;
      		
      	 }
       	 
       	 
       		
   		 if(dialog != null)  dialog.dismiss(); 
   		 AdapterParadasLinha adapter = new AdapterParadasLinha(MostraParadaLinhaActivity.this, listapontoQBG);
            adapter.notifyDataSetChanged();         
            setListAdapter(adapter);  
           	
            
            Gson lista = new Gson();
	        String listagem = lista.toJson(listapontoQBG.toArray()).toString();
	        shared_preferences_editor = shared_preferences.edit();
		    shared_preferences_editor.putString("listaparadas", listagem);
	        shared_preferences_editor.commit();
	        
	    	
           	
        
       	 
      /*       
       	 listaponto.clear();
       	 for(int i=0; i< listapontoQBG.size(); i++) 
	      {
       		 
       		 
       		 EventInfo buddy = listapontoQBG.get(i);       		
         	 String monta = buddy.getParadacod();	
			 
         	 //PegaSomenteParada(monta);         	 
         	 //String url = "http://api.olhovivo.sptrans.com.br/v0//Parada/Buscar?termosBusca="+monta;   
			 //task3 = new PesqParadaSpTrans(this);
			 //task3.execute(url);
       		 
	      }
       	 
       	 
       	 
       	*/ 
       	 
       	 
       	 
       	 
       	
			 
		 }
		 
		 public void onPegaDadosSearchByFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
		 
		
		
		
		
		
		
		
		
		
		
		public void Inicia()
		{		
			
			PegaShared();
			PegaBundle();
		
		    
			listDataHeader = new ArrayList<String>();
	  	    listDataChild = new HashMap<String, List<String>>();
	    //    novo = ((Button) findViewById(R.id.B_itinerario));
	   //     IniciaMapa();
	   //     PegaPontoLinha(cod_linha);	
	        
	        PegaPontoLinha(linha_clicada);	
	        
	        
	        
	        setTitle("Paradas da linha: "+linha_clicada);
	  	
		}
		
		public void PegaShared()
		{
			
			shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
		    latx 			=   shared_preferences.getString("lat", "");
		    lonx 			=   shared_preferences.getString("lon", ""); 
		    paradacod     	=   shared_preferences.getString("paradacod", "");
		    selecionado_w 	=   shared_preferences.getBoolean("selecionado_w", false);
		    semonibus_w 	=   shared_preferences.getBoolean("semonibus_w", false);
		}
		

		public void PegaBundle()
		{
			
			    Bundle extras = getIntent().getExtras();
			    linha_clicada = extras.getString("linha");
			    cod_linha = extras.getString("codlinha");
			    
		
		}
		
		
		protected void PegaPontoLinha(String linhaw)
		{		

		
			
			
			
		   	dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoparadaslinha));
			listaponto.clear();
			pontolinha = true;
			
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
			  finalURLStr = finalURLStr + "&MetaKeySearch="+encodeValue("%_linha_"+linhaw+"%") ;          //  
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
		
		
		protected void PegaSomenteParada(String codparada)
		{		
	//		dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoopçoes));
			
			// if(dialog != null)  dialog.dismiss();
			pontolinha = false;
			  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_Get";       // API Call
			  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
			  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
			  finalURLStr = finalURLStr + "&SocketMetaKey=STOP_"+encodeValue(codparada) ;          //  
			 String url = finalURLStr.toString();
			 PegaSomenteParada pontoparada3 = new PegaSomenteParada(MostraParadaLinhaActivity.this);
			 pontoparada3.execute(url,latx,lonx);
		}	
    
    	private class PegaSomenteParada extends AsyncTask<String, Integer, String> {
		     
			   public PegaSomenteParada(MostraParadaLinhaActivity mostraParadaLinhaActivity) {
				// TODO Auto-generated constructor stub
			}

			protected String doInBackground(String... params) {
				  
			        
			        String urlF = params[0];
			        String result5 = null;
				      latx =  params[1];
				      lonx =  params[2];
				        
				        try {
				            HttpClient clientF = new DefaultHttpClient();
				            HttpGet httpgetF = new HttpGet(urlF);
				            HttpResponse responseF = clientF.execute(httpgetF);
				            HttpEntity entityF = responseF.getEntity();
				            if(entityF == null) {
				                msg = "No response from server";
				                result5 = null;
				            }
				            InputStream isF = entityF.getContent();
				            String convF = streamToString(isF);
				           	result5 = convF;
				        }
				        catch(IOException e){
				            msg = "No Network Connection";
				            result5 = null;
				        }
				        return result5;    
			     }

			     
			
			
			protected void onProgressUpdate(Integer... progress) {
			         //setProgressPercent(progress[0]);
			     }

			
			
			
			
			
			
			
			
			protected void onPostExecute(String sJson) 
			{
		    	 
				
				total = total +1;
				
			    
				   JSONObject jsonF  = null;
		    	   JSONObject jArray = null;
		    	   JSONArray  jfotos = null;
		    	   JSONObject jsonR  = null;
		    	   JSONArray resultArray = null;
		    	   int tipo = 0;
		    	   String id = null;
		    	
		    	   
		    	
		    
		    	   
		    	if(sJson == null) {
		    		 System.err.println("erro 10");
		    		// dialog.dismiss();
		    		return;
		        }        
		         
		        
		    	String four = null;
		    
		    	try
		        {   
		        four =(String) sJson.toString();
		  		jArray = new JSONObject(four);
		  		resultArray = jArray.getJSONArray("data");
		    	  } catch (JSONException e) {
		    		 // dialog.dismiss();
		              System.err.println("erro 2");
		              return;
		        } 
	    		 
	    		 
	    		
		    	if (resultArray.length() != 0) 
			      {
			    	  for(int j=0; j< resultArray.length(); j++) 
			    	  {
			  			 try
			  			 {
			  		    
					  		  JSONObject det = resultArray.getJSONObject(j);			 
					  		  
					  		  String linha  = null;
					  		  String tester = null;
					  		  int sentidolinha = 0;
					  		  int seq = 0;
					  		  
					  		
					  		  linha = det.getString("metaKey");
					    	  id = linha.replace("STOP_", "");
					    	  
					    	  EventInfo appM = new EventInfo();  

			        			String monta2 = det.getString("metaValue").toString();
			        			
			        			if (monta2.indexOf("[1]") >= 0) appM.setTipo(1);
			        			if (monta2.indexOf("[2]") >= 0) appM.setTipo(2);
			        			if (monta2.indexOf("[3]") >= 0) appM.setTipo(3);
			        			
			        			String monta3 = monta2.replace("[1]", "");
			        			String monta4 = monta3.replace("[2]","");
			        			String monta5 = monta4.replace("[3]","");
			        			
			  	        	    String[] items = monta5.split("@");
			                    String nome =  items[0].trim();
			                    String ende = items[1].trim();
					    	  
					    	    appM.setLinha(linha_clicada);     
					    	  
					    	  
					  		  
					  		  Double latm = det.getDouble("metaLatitude");
					  		  Double lonm = det.getDouble("metaLongitude");
					  		
			                  appM.setSentidolinha(1);
				              appM.setSeqparada(seq);
					            LatLng latLng = new LatLng(latm , lonm);					  		 	
					  		 	appM.setaLat(latm);
					  	        appM.setaLon(lonm);
					  	        appM.setLocalizacao(ende);
					  	        appM.setId(id);
					  	        appM.setPonto(nome);
					  	        appM.setLatLong(latLng);        
					  	        appM.setParadacod(id);
					  	        appM.setSentido(1);
					  	        appM.setSentidolinha(1);
				  	        	listaponto.add(appM);						  	        	
				  	    
			  	        
			  	        
			  			  } 
			  			  catch (Exception ex) 
			  			  {
			  				 System.err.println("erro 3");
			  			//	dialog.dismiss();
			  			  }
			  		
			    	  
			    	  
			    	  
				 } // for
		     

			      
			      
			      
			    
			   
	
		     }// if
		    	//dialog.dismiss();
		    	
		    	
		    	
		    	/*
		    	if (listaponto2.size() == 2)
		    	{
		    		    EventInfo origem  = listaponto2.get(0);
		    		    EventInfo destino = listaponto2.get(1);
		    	        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						Uri.parse("http://maps.google.com/maps?mode=transit&saddr="+origem.getaLat()+","+origem.getaLon()+"&daddr="+destino.getaLat()+","+destino.getaLon()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.addCategory(Intent.CATEGORY_LAUNCHER );     
						intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
						startActivity(intent);
		    	}
		    	
		    	*/
		    	
		    	
		    	 if (total >= listapontoQBG.size()) {
		            	
		    		 if(dialog != null)  dialog.dismiss(); 
		    		 AdapterParadasLinha adapter = new AdapterParadasLinha(MostraParadaLinhaActivity.this, listaponto);
		             adapter.notifyDataSetChanged();         
		             setListAdapter(adapter);  
		            	
		            	
		            }

		}
		
           
    
    	}

		
		
		
		public void IniciaMapa()
		{
			/*
			
			    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		        googleMap = mapFragment.getMap();
		        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		        googleMap.setTrafficEnabled(true);
		        
		        googleMap.setOnInfoWindowClickListener(
		                        new OnInfoWindowClickListener(){
		                            public void onInfoWindowClick(Marker marker) {
		                            	EventInfo eventInfo = ListaObj.get(marker);
				        		    	String cod = eventInfo.getPonto();
				        		    	String parada = eventInfo.getParadacod();
				        		    	String sentido = Integer.toString(eventInfo.getSentido());
				        		    	String latparada = Double.toString(eventInfo.getaLat());
				        		    	String lonparada = Double.toString(eventInfo.getaLon());
				        		    	Intent nextScreen = new Intent(MostraParadaLinhaActivity.this, MostraLinhaActivity.class);
				        		        nextScreen.putExtra("parada", "" + parada);
				        		        nextScreen.putExtra("nome", "" + cod);
				        		        nextScreen.putExtra("sentido", "" + sentido );
				        		        nextScreen.putExtra("latparada", "" + latparada );
				        		        nextScreen.putExtra("lonparada", "" + lonparada );
				        		        startActivityForResult(nextScreen, 0);
				        		        shared_preferences_editor = shared_preferences.edit();
				        			    shared_preferences_editor.putString("paradacod", parada);
				        			    shared_preferences_editor.commit();
				        			    shared_preferences_editor = shared_preferences.edit();
				        			    shared_preferences_editor.putBoolean("selecionado_w", true);
				        			    shared_preferences_editor.commit();			
				        			    shared_preferences_editor = shared_preferences.edit();
				        			    shared_preferences_editor.putBoolean("semonibus_w", false);
				        			    shared_preferences_editor.commit();
						                 }
		            });
		        
		        
		    
		        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		        	 
		        	private final View contents = getLayoutInflater().inflate(R.layout.mapainfo, null);
		            private final View contentsponto = getLayoutInflater().inflate(R.layout.mapainfopara, null);
		          
		            @Override
		            public View getInfoWindow(Marker arg0) {
		                return null;
		            }
		 
		            @Override
		            public View getInfoContents(Marker marker) {

		             EventInfo eventInfo = ListaObj.get(marker);

		             int sentido = eventInfo.getSentidolinha();
		             int cor = 0;
		             if (sentido == 1)
			         	{
			         		cor = getResources().getColor(R.color.onibus1);
			         	}  else
			         	{
			         		cor = getResources().getColor(R.color.onibus2);
			         	}
		             if (sentido == -1)
			             {
			            	 cor = getResources().getColor(R.color.abanormal);
			             }
		             
		             
		               int parada = eventInfo.getParada();            
		                 
		                
		               if (parada > 0) 
		               {
		            	    String ponto = eventInfo.getPonto();
		            	    String nome = null;
		            	    String ende = null;
		            	    String[] respo = PegaGeocoder(eventInfo.getaLat(), eventInfo.getaLon());
		            	    ende = respo[0];
		            	    
		            	    
		            	   
				               if (eventInfo.getSentidolinha() == 1)
				               {
				               nome = letreiro1linha+" | "+letreiro2linha;
				               } else
				               {
				               nome = letreiro2linha+" | "+letreiro1linha;
				               }
				               
				               
				               eventInfo.setPonto(ende+" @ "+ende);
				               atualiza(eventInfo);
				               ListaObj.remove(marker);
				               ListaObj.put(marker, eventInfo);
				               
				           
		            	   TextView txtTitulo = ((TextView) contentsponto.findViewById(R.id.txtTitulo));
		            	   TextView txtPonto = ((TextView) contentsponto.findViewById(R.id.txtPonto));
		            	   LinearLayout ratingCntr = ((LinearLayout) contentsponto.findViewById(R.id.ratingCntr));
		            	   txtTitulo.setTextColor(cor);
		            	   txtPonto.setTextColor(getResources().getColor(R.color.textoende));
		            	   NumberFormat nf = NumberFormat.getNumberInstance();
		                   ratingCntr.removeAllViewsInLayout();
		                   ImageView iv = new ImageView(getBaseContext());
		                   TextView  tv = new TextView(getBaseContext());
		                	ImageView cad = new ImageView(getBaseContext());
		                	iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_eu));
		                	cad.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_cadeira));
		                	tv.setText(" Distância: "+nf.format(eventInfo.getDistyou())+"m       ");
		                	tv.setTextSize(12);                	
		                	tv.setTextColor(cor);
		                	ratingCntr.addView(iv);	
		                	ratingCntr.addView(tv);
		                    txtTitulo.setText(nome);
		                    txtPonto.setText(ende);
		      
		                    return contentsponto;
		            	   
		               }
		                
		               else
		            	   
		            {
		            	   
		            	   
		           	   String ponto = eventInfo.getPonto();
		           	    String nome = null;
		           	    String ende = null;
		           	    String[] items = ponto.split("@");
		                      nome =  items[0].trim();
		                      ende = items[1].trim();
		               TextView txtLetreiro = ((TextView) contents.findViewById(R.id.txtTitulo));
		               TextView txtCarro = ((TextView) contents.findViewById(R.id.txtCarro));
		               TextView txtPonto = ((TextView) contents.findViewById(R.id.txtPonto));
		               TextView txtPrevi = ((TextView) contents.findViewById(R.id.txtPrevisao));
		               txtCarro.setText(eventInfo.getVeiculo());
		               txtPonto.setText(nome);
		               txtPrevi.setText(eventInfo.getPrevisao());
		               txtLetreiro.setText(eventInfo.getLetreiro());
		                return contents;

		        }
		               }
		        });

		        

		        
		        
		        PublicaMapa();
		        
		        
			*/
			
		}
		
		
		
		
		public String[] PegaGeocoder(double latp, double lonp)
		{
			  Geocoder geocoder;
              List<Address> addresses;
              String[] respo = new String[2];
              geocoder = new Geocoder(MostraParadaLinhaActivity.this, Locale.getDefault());
              try
              {
              addresses = geocoder.getFromLocation(latp, lonp, 1);
              respo[1] = addresses.get(0).getLocality();
              respo[0] = addresses.get(0).getAddressLine(0);
           	  return respo;
              }
              catch (Exception e)
              {
            	return null;  
              }
			
		}
		
		
		
		public void atualiza(EventInfo oni)
		   {
			   String id = oni.getId();  
			   for(int h=0; h < listaponto.size() ; h++) 
		    	  {
					EventInfo objeven = listaponto.get(h);				
					String chave = objeven.getId();
						if (chave.equals(id)) 
						{
							listaponto.set(h, oni);
						}
					
		   }
		   }
		
		
		
		
			
	/*	
		
	public void MontaParada()
	{
		
		 terminou = false;
		 contaparada = 0;
		 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));				 
		 for(int j=0; j< listaponto.size(); j++) 
		  {
			 String id = listaponto.get(j).getParadacod();		 
			 PegaPontoParadaUrl("http://200.189.189.54/InternetServices/Previsao?codigoParada="+id); 
		  }
		 dialog.dismiss();
		
		 
		 
		 
		 	
	}
		
		*/
		
	public void MontaParada2()
	{
		 LatLng latLng = null;
		
			
		 
		 if  (listaponto.size() == 0) {
			 Toast.makeText(this, "Localização sem paradas em um raio de 500m. Selecione outra!", Toast.LENGTH_LONG).show();
			 return;
			 
		 }
	
		 
		 
		 
		 
		  AdapterParadasLinha adapter = new AdapterParadasLinha(MostraParadaLinhaActivity.this, listaponto);
          adapter.notifyDataSetChanged();         
          setListAdapter(adapter);  
          dialog.dismiss();
		 
		 
          return;
		 
		 
		 
		 
		 
		 
		 
		 
		 /*
		 
		
		 for(int j=0; j< listaponto.size(); j++) 
		  {
			 EventInfo appM3 = listaponto.get(j);
			 String id = listaponto.get(j).getParadacod();
			 montamapaponto(appM3); 
			 latLng = appM3.getLatLong();
		  }
		
		 
		 
		 
		 
		if (pontolinha == true)
		{
		 
		 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
	    googleMap.animateCamera(CameraUpdateFactory.zoomIn());
	    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
		}  else
		{
			
		}
		
		
		*/
		
		
		
		
		
	}



		

		
		/*
		
		public void PublicaMapa()
		{
			 LatLng latLng = null;
			 latLng = new LatLng(Double.parseDouble(latx) , Double.parseDouble(lonx));
		        googleMap.addMarker(new MarkerOptions()
		        .position(latLng)
		        .title("Você")
		        .snippet("Aqui")
		        .icon(BitmapDescriptorFactory.fromResource(R.drawable.eu)));
		        googleMap.getUiSettings().setCompassEnabled(true);
		        googleMap.getUiSettings().setZoomControlsEnabled(true);
		        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
		        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		        Circle circle = googleMap.addCircle(new CircleOptions()
		        .center(latLng)
		        .radius(600)
		        .strokeColor(Color.GRAY)
		        .strokeWidth(2)
		        .fillColor(0x40cccccc));
		}
		
		
		    
	   public void montamapaponto(EventInfo ob)
	   {
		   
		   LatLng latLng = null;
		   latLng = new LatLng(ob.getaLat() , ob.getaLon());	   
		   String dista = Double.toString(ob.getDistyou());	   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(ob.getPonto());
		   markerOptions.snippet(dista);
		   
		   if (ob.getSentidolinha() == 1)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));
		   
		   if (ob.getSentidolinha() == 2)
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto2));
		   
		   if (ob.getSentidolinha() == -1)
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto3));
		   
		   
		   if (selecionado_w == true) 
		   {
			   if (paradacod.equals(ob.getParadacod()))
			   {
				   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.selecionado));
			   }
		    }
		   
		   if (semonibus_w == true) 
		   {
			  
			   if (paradacod.equals(ob.getParadacod()))
			   {
				   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto3));
			   }
		    }
		   
		   
		   
		   String marcatem = ob.getPonto();
		   String vemarca  = marcatem.substring(0, 4);
		   if (vemarca.equals("[0] ")) {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto3));
		   }
		   
		   
		   Marker marker = googleMap.addMarker(markerOptions);   
		   ListaObj.put(marker, ob);
		  // marker.showInfoWindow();
	   }
	   
	   


	   
	   
	   
	   
	   public void montamapaonibus_vai(EventInfo oni)
	   {
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistyou());	   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
		   Marker marker = googleMap.addMarker(markerOptions);   
		   ListaObj.put(marker, oni);
		
	  
	   }
	   
	   
	   public void montamapaonibus_vem(EventInfo oni)
	   {
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistyou());	   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.busvem));
		   Marker marker = googleMap.addMarker(markerOptions);   
		   ListaObj.put(marker, oni);
		   
	   }	
		
		*/
	   
	   
	   
	   
	   
		
		
		

		


				  
				// URL encoding replaces unsafe characters with valid ASCII characters
				// as defined in http://www.ietf.org/rfc/rfc2396.txt
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

				
				
				
				
				
				
				
				
				
				
				
			/*	
				
				
				protected void PegaPonto()
				{	
					
					//novo.setVisibility(View.INVISIBLE);
					dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
					listaponto.clear();
					//pontolinha = false;
					  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_SearchNearby";       // API Call
					  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
					  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
					  finalURLStr = finalURLStr + "&SearchDistance=" + distanciaw; // Search distance (int)
					  finalURLStr = finalURLStr + "&Latitude=" + latx;                        // Location latitude
					  finalURLStr = finalURLStr + "&Longitude=" + lonx;                      // Location longitude
					  finalURLStr = finalURLStr + "&RecordLimit=" + "5500";        // Max number of records (int)
					  finalURLStr = finalURLStr + "&MetaKeySearch="+encodeValue("%Parada_%") ;          //  
					  finalURLStr = finalURLStr + "&MetaValueSearch=";      //
					  finalURLStr = finalURLStr + "&TimeFilter=-1" ;            // Time in minutes since last update
					  finalURLStr = finalURLStr + "&MetaValueMin=";         // Minimum numeric value
					  finalURLStr = finalURLStr + "&MetaValueMax=";         // Maximum numeric value
					  finalURLStr = finalURLStr + "&SearchAsFloat=" + 1;                          // Search as numeric value
					  finalURLStr = finalURLStr + "&SortResultsDirection=";   // Sort direction (1=ascending)
					  finalURLStr = finalURLStr + "&DisableCache=" + "true";                      // Cache use flag (true or empty)
					 String url = finalURLStr.toString();
					 pontotask = new PegaPontoTask(this);
					 pontotask.execute(url,latx,lonx);
				}	
				
				protected void PegaPontoParadaUrl(String url)
				{				
					// dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));				 
					 pontoparada = new PegaPontoParada(this);
					 pontoparada.execute(url,latx,lonx);
				}	
				
				
			
				private class PegaPontoTask extends AsyncTask<String, Integer, ArrayList<String>> {
				     
					   public PegaPontoTask(MostraParadaLinhaActivity mostraParadaLinhaActivity) {
						// TODO Auto-generated constructor stub
					}

					protected ArrayList<String> doInBackground(String... params) {
						  
					        
					        String urlF = params[0];
					        String urlG = params[1];
					        
					        
					        String result1 = null;
						      
						      latx =  params[1];
						      lonx =  params[2];
						   
						      ArrayList<String> result = new ArrayList<String>(2);  
						       
						        result.add(" ");
						        result.add(" ");
						   
						        
						        
						        
						        if (pontolinha == true)
						        {
						        try {
						            HttpClient client = new DefaultHttpClient();
						            HttpGet httpget = new HttpGet(urlG);
						            HttpResponse response = client.execute(httpget);
						            HttpEntity entity = response.getEntity();
						        
						            if(entity == null) {
						            	System.err.println("erro 30");
						                result.set(1," ");
						               // return null;        
						            }
						            InputStream is = entity.getContent();
						           // return 
						            String conv = streamToString(is);
						           	result.set(1,conv);
						        }
						        catch(IOException e){
						        	System.err.println("erro 31");
						            result.set(1," ");
						        }
						        
						        }
						        
						        
						        
						        try {
						            HttpClient clientF = new DefaultHttpClient();
						            HttpGet httpgetF = new HttpGet(urlF);
						            HttpResponse responseF = clientF.execute(httpgetF);
						            HttpEntity entityF = responseF.getEntity();
						            if(entityF == null) {
						            	System.err.println("erro 32");
						                result.set(0," ");
						            }
						            InputStream isF = entityF.getContent();
						            String convF = streamToString(isF);
						           	result.set(0,convF);
						        }
						        catch(IOException e){
						        	System.err.println("erro 33");
						            result.set(0," ");
						        }
						        return result;    
					     }

					     
					
					
					protected void onProgressUpdate(Integer... progress) {
					         //setProgressPercent(progress[0]);
					     }

					     protected void onPostExecute(ArrayList<String> sJson) 
					     {
					    	 
					    
					    	   JSONObject jsonF  = null;
					    	   JSONObject jArray = null;
					    	   JSONArray  jfotos = null;
					    	   JSONObject jsonR  = null;
					    	   JSONArray resultArray = null;
					    	   int tipo = 0;
					    	   String id = null;
					    	
					    	if(sJson == null) 
					    	{
					    		dialog.dismiss();
					            return;
					        }        
					         
					        
					    	 if (pontolinha == true)
						        {
					    		 
					    		 
					    		 try
							        {   
							        String four =(String) sJson.get(1).toString();
							  		jArray = new JSONObject(four);
							  		resultArray = jArray.getJSONArray("BuscaLinhasSIMResult");
							    	  } catch (JSONException e) {
							              System.err.println("erro 1");
							              dialog.dismiss();
							              return;
							        } 
					    		 
					    		 
					    		
						  			 try
						  			 {
						  		    
								  	 	   JSONObject det = resultArray.getJSONObject(0);						  		  
								  		   letreiro1linha =   det.getString("DenominacaoTPTS");
								    	   letreiro2linha =   det.getString("DenominacaoTSTP");
								    	   sentidoparada  =   det.getString("Sentido"); 	   
								    	   
								    	      

								  		  
						  			 }
						  			 catch (Exception e)
						  			 {
						  				dialog.dismiss();
						             }
						        }
					    	
					    	
					    	
					    	
					    	
					    	
					    	try
					        {   
					        String four =(String) sJson.get(0).toString();
					  		jArray = new JSONObject(four);
					  		resultArray = jArray.getJSONArray("data");
					    	  } catch (JSONException e) {
					    		  dialog.dismiss();
					              System.err.println("erro 2");
					              return;
					        } 
					        
					      	
					  		  
					  		  
					  		  
					    	 
						      if (resultArray.length() != 0) 
						      {
						    	  for(int j=0; j< resultArray.length(); j++) 
						    	  {
						  			 try
						  			 {
						  		    
								  		  JSONObject det = resultArray.getJSONObject(j);			 
								  		  
								  		  String linha  = null;
								  		  String tester = null;
								  		  int sentidolinha = 0;
								  		  int seq = 0;
								  		  
								  		 if (pontolinha == false) 
								  		 {
								  		  linha = det.getString("metaKey");
								    	  id = linha.replace("Parada_", "");
								    	  sentidolinha = 1;
								  		 } else
								  		 {
								  	      tester = det.getString("metaKey");
								  	      if (tester.length() == 16)
								  	      {
								  	    	  seq =  Integer.parseInt(tester.substring(15, 16));
								  	      }
								  	      if (tester.length() == 17)
								  	      {
								  	    	  seq =  Integer.parseInt(tester.substring(15, 17));
								  	      }
								  	      if (tester.length() == 18)
								  	      {
								  	    	  seq =  Integer.parseInt(tester.substring(15, 18));
								  	      }
								  	      if (tester.length() == 19)
								  	      {
								  	    	  seq =  Integer.parseInt(tester.substring(15, 19));
								  	      }
								  	      
								  	      
								  	      
								  		  linha = tester.substring(5, 12);
								  		  
								  		  String valor = det.getString("metaValue");
								  		  String valor1 = valor.replace("[0] ", "");
								  		  String valorok = valor1.replace("[1] ", "");
								  		  
								  		  
								  		  
								  		  id = valorok;
								  		  
								  		  
								  		  
								  		  
								  		  sentidolinha = Integer.parseInt(tester.substring(13, 14));
								  		  if (sentidolinha == 0) {
								  			sentidolinha = 2;
								  		  }
								  		  
								  		 }
								    	  
								    	  
								    	  
								    	  
								  		  String nome = det.getString("metaValue");
								  		  Double latm = det.getDouble("metaLatitude");
								  		  Double lonm = det.getDouble("metaLongitude");
								  		  Double dista = det.getDouble("distanceInMeters");
								          NumberFormat nf = NumberFormat.getNumberInstance();
							              EventInfo appM = new EventInfo();
						          
							              
							              appM.setSentidolinha(sentidolinha);
							              appM.setSeqparada(seq);
							              
							              
							              if (linha.indexOf("METRO") >= 0) 
							              {
						  		    		appM.setTipo(2);
						  		    		appM.setParada(2);
							  		    	}   else
							  		    	{
							  		    		if (linha.indexOf("CPTM") >= 0) {
							  					appM.setTipo(3);
						  		    			appM.setParada(3);
							  		    	}  else
							  		    	{
							  		    		appM.setTipo(1);
							  		    		appM.setParada(1);
							  		    	}
							  		    	
							  		    	}  
						  		    	
						  		    	
						  	        
							            LatLng latLng = new LatLng(latm , lonm);					  		 	
							  		 	appM.setaLat(latm);
							  	        appM.setaLon(lonm);
							  	        appM.setDistyou(dista);
							  	        appM.setId(id);
							  	        appM.setPonto(nome);
							  	        appM.setLatLong(latLng);        
							  	        appM.setParadacod(id);
							  	        appM.setSentido(1);
							  	        appM.setSentidolinha(sentidolinha);
						  	        
							  	        
							  	        	
							  	       	listaponto.add(appM);						  	        	
							  	     
						  	        
						  	        
						  			  } 
						  			  catch (Exception ex) 
						  			  {
						  				 System.err.println("erro 3");
						  				dialog.dismiss();
						  			  }
						  		
						    	  
						    	  
						    	  
							 } // for
					     
		
						      
						      
						      
						    
						   
				
					     }// if
						      
						      MontaParada2();
							     
							    dialog.dismiss();
		
				}//proc
				
				}
				   
				
				
				
				
				
				
				
				
				
				
				
				private class PegaPontoParada extends AsyncTask<String, Integer, String> {
				     
					   public PegaPontoParada(MostraParadaLinhaActivity mostraParadaLinhaActivity) {
						// TODO Auto-generated constructor stub
					}

					protected String doInBackground(String... params) {
						  
					        
					        String urlF = params[0];
					        String result5 = null;
						      latx =  params[1];
						      lonx =  params[2];
						        
						        try {
						            HttpClient clientF = new DefaultHttpClient();
						            HttpGet httpgetF = new HttpGet(urlF);
						            HttpResponse responseF = clientF.execute(httpgetF);
						            HttpEntity entityF = responseF.getEntity();
						            if(entityF == null) {
						                msg = "No response from server";
						                result5 = null;
						            }
						            InputStream isF = entityF.getContent();
						            String convF = streamToString(isF);
						           	result5 = convF;
						        }
						        catch(IOException e){
						            msg = "No Network Connection";
						            result5 = null;
						        }
						        return result5;    
					     }

					     
					
					
					protected void onProgressUpdate(Integer... progress) {
					         //setProgressPercent(progress[0]);
					     }

					protected void onPostExecute(String sJson) 
					{
				    	 
					    
				    	   JSONObject jsonF  = null;
				    	   JSONObject jtudo = null;
				    	   JSONArray resultArray = null;
				    	   JSONObject det = null;
				    	   
				    	
				      contaparada = contaparada + 1;	   
				    	   
				      if (listaponto.size() == contaparada)
				      {
				    	  terminou = true;
				      }
				    	   
				    	if(sJson == null) {
				    	//	dialog.dismiss();
				    		 System.err.println("erro 10");
				    		return;
				        }        
				         
				        
				    	String four = null;
				    
				    	try
				        {   
				        four = sJson.toString();
				  		  } catch (Exception e) {
				              System.err.println("erro 11");
				            //  dialog.dismiss();
				              return;
				        } 
				        
				      	
				    	try
				    	{			    	
				    	jtudo = new JSONObject(four).getJSONObject("PrevisaoResult");
				  		  } catch (JSONException e) {
				              System.err.println("erro 12");
				            //  dialog.dismiss();
				              return;
				        } 
				    	
				    	
				    	
				    	
				    	try
				    	{			    	
				    	jsonF = jtudo.getJSONObject("p");
				    	codparada = jsonF.getString("cp");
				  		  } catch (JSONException e) {
				              System.err.println("erro 13");
				            //  dialog.dismiss();
				              return;
				        } 
				        
				    	try
				    	{			    	
				    	resultArray = jsonF.getJSONArray("l");
				    	  } catch (JSONException e) {
				    		  System.err.println("erro 14");
				              //System.err.println("erro");
				    		 // dialog.dismiss();
				              return;
				        } 
				    	
				    	
				    	 
				    	  
				          if (resultArray.length() != 0) 
					      {
				        	 	 try
					  			 {
					  		     
					  			 det = resultArray.getJSONObject(0);			 
					  		     sentido  = det.getInt("sl");
					  		     poeSentido(codparada,sentido);
					  		      } 
					  			  catch (Exception ex) 
					  			  {
					  				System.err.println("erro 15");
					  				//dialog.dismiss();
					  			  } 
					    	  
					      }
				          
				          //dialog.dismiss();
					}
				}
				
				
				
				
				public void poeSentido(String parada, int sentido)
				   {
					     
					   for(int h=0; h < listaponto.size() ; h++) 
				    	  {
							EventInfo objeven = listaponto.get(h);				
							int tipo = objeven.getTipo();
							int para = objeven.getParada();			
							if ((tipo == 1) && (para == 1)) 
							{
								String chave = objeven.getId();
								if (chave.equals(parada)) 
								{
									objeven.setSentidolinha(sentido);
									listaponto.set(h, objeven);
									return;
								}
							}
				   }
				   }	
				
				
				
				
			*/	
				
				
				
				
				
				
		    	
		    	
		    	
				
				
				
				
				public String streamToString(final InputStream is) throws IOException
				{
				      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				      StringBuilder sb = new StringBuilder(); 
				      String line = null;
				       
				      try {
				          while ((line = reader.readLine()) != null) {
				              sb.append(line + "\n");
				          }
				      } 
				      catch (IOException e) {
				          throw e;
				      } 
				      finally {           
				          try {
				              is.close();
				          } 
				          catch (IOException e) {
				              throw e;
				          }
				      }
				       
				      return sb.toString();
				  }
				
		
				
				
								
				
				public void Bitinerario(View v)
				{
					
					String latitude_cur = null;
					String longitude_cur = null;
					String latitude = null;
					String longitude = null;
					String paradaorigem = null;
					String paradadestino = null;
					int maior = 0;
					int menor = 5000;
					            
					
					
					  for(int h=0; h < listaponto.size() ; h++) 
			    	  {
						EventInfo objeven = listaponto.get(h);
			    	    int seq = objeven.getSeqparada();
			    	    int seq2 = objeven.getSeqparada();
			    	    int senti = objeven.getSentido();
			    	    
			    	  
			    	    if (senti == 1)
			    	    {
			    	    
				    	    if (seq < menor)
				    	    {
				    	    	
				    	    	menor = seq;
				    	    	latitude_cur = Double.toString(objeven.getaLat());
				    	    	longitude_cur = Double.toString(objeven.getaLon());
				    	    	paradaorigem = objeven.getParadacod();
				    	    	
				    	    }
				    	    
				    	    if (seq > maior)
				    	    {
				    	    	
				    	    	maior = seq;
				    	    	latitude = Double.toString(objeven.getaLat());
				    	    	longitude = Double.toString(objeven.getaLon());
				    	    	paradadestino = objeven.getParadacod();
				    	    	
				    	    }
				       }
			    	    
			    	    
			    	  
			    	  
			   	  }
					
					
					  
					listaponto2.clear();  
					//PegaSomenteParada("Parada_"+paradaorigem);
					//PegaSomenteParada("Parada_"+paradadestino);
					
					  
					  
					
					
							    
					
				}
				
				
				
				
				
				
				
				
								
				
								
				 @Override
			        public boolean onKeyDown(int keyCode, KeyEvent event) {
			            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			                onBackPressed();
			            }
						return true;
			    }

				
				
				@Override
			    public void onBackPressed() {
			        super.onBackPressed();
			        
				    shared_preferences_editor = shared_preferences.edit();
				    shared_preferences_editor.putBoolean("parar", true);
				    shared_preferences_editor.commit();
			        
			        finish();

			    }
	}

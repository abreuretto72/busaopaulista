package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.abreuretto.busaopaulista.R;
import com.abreuretto.busmoni.BusMoni;
import com.abreuretto.busmoni.BusMoniSPTrans;
import com.abreuretto.busmoni.BusMoniSPTransListener;
import com.abreuretto.busmoni.L;
import com.abreuretto.busmoni.V;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MapaMonitoraActivity extends FragmentActivity implements BusMoniSPTransListener, PegaDadosGeocodeListener 
{

	  SharedPreferences shared_preferences;
	  SharedPreferences.Editor shared_preferences_editor;
	    String latx = null;
	    String lonx = null;
	    String paradacodw = null;
	    String linhaW = null;
	    String usertoken = null;
	    
	    double latponto = 0;
	    double lonponto = 0;
	    double distaok = 0;
	    
	    double lath = 0;
		double lonh = 0;
		  
		double latparah = 0;
		double lonparah = 0;
		
		
		
		
	    String tempocalc = null; 
		int distakm = 0;
	    
		 PegaDadosGeocode task = null;

	    LatLng latlonold;
	    
	    
	    String endereco = null;
	    
	    String paradaclick = null;
	    String linhaclick = null;
	    String carroclick = null;
	    String nomeparadaclick = null;
	    
	    boolean temcarro = false;
	    int contasom;
	    boolean prima = true;
	    Date tempoini = null;
	    Date tempoja = null;
	    String tempocalculado = null;
	    
	    
	    
	    
	    int conta;
		int registros = 0;
		int contaespera = 0;
		double lathx = 0; 
	  	double lonhy =  0;
	  	String paradanome = null;
		String codparada = null;
		int sentido  = 0;
		String latparada = null;
		String lonparada = null;
		String letreiro = null; 
		 
		 
	    
	   // ArrayList<AppMapa> lista = new ArrayList<AppMapa>();
	    ArrayList<EventInfo> listanomapa = new ArrayList<EventInfo>();
	    ArrayList<EventInfo> listaponto = new ArrayList<EventInfo>();
	    
	    
	    private HashMap<Marker, EventInfo> eventMarkerMap;
		 ConnectionDetector cd;
		 private ProgressDialog dialog;
		 AlertDialogManager alert = new AlertDialogManager();

	    
	   // PegaPontoTask  pontotask;
	    PegaOnibusTask onibustask;
	    //PegaParadaTask paradatask;
	    
	    
		// Constants
		public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
		public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
		public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
	    
		GoogleMap googleMap;

		HashMap <Marker, EventInfo> ListaObj = new HashMap <Marker, EventInfo>();
		
		
		  List<String> listDataHeader;
		  HashMap<String, List<String>> listDataChild;
		  List<Marker> markerList ;
		
		  Marker posicao = null;
		  
		  
		private Handler handler = new Handler();
		boolean acabou = false;	
		boolean espera = false;	
		TextView txtDist;  
	    TextView txtCarro;
	    TextView txtLocal;
		
	    MediaPlayer mediaPlayer;
		  
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_mapa_monitora);	
			
			
			   
			latlonold = new LatLng(0, 0);
			
			
			
	        shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
		    latx = shared_preferences.getString("lat", "");
		    lonx = shared_preferences.getString("lon", ""); 
		    usertoken = shared_preferences.getString("UserToken", ""); 
		    
			
		   // latx = ("-23.603141"); 
		   // lonx = ("-46.665977");
		    
		     
		    prima = true;
		    contasom = 0;
		    contaespera = 0;
		    
		    Bundle extras = getIntent().getExtras(); 
		    
		       paradaclick = extras.getString("parada");
		       linhaclick = extras.getString("linha");
		       String carroclickw = extras.getString("carro");
		       carroclick = carroclickw.replace("Carro: ", "");
		       nomeparadaclick = extras.getString("nomeparada");
		       latparada = extras.getString("latparada"); 
		       lonparada = extras.getString("lonparada");
		       letreiro = extras.getString("letreiro");
		     
		       
		       tempoini = new Date();
		       
		       
		       
		       
		       MetaData_UserMetaDataValue_Set();
		       
		       
		       mediaPlayer = MediaPlayer.create(this, R.raw.buzina); 
		       
		       
		  	   cd = new ConnectionDetector(getApplicationContext());
				
		       if (!cd.isConnectingToInternet()) {
		           AlertDialog.Builder builder = new AlertDialog.Builder(this);
		           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
		               .setMessage(this.getResources().getString(R.string.interneterror))
		               .setCancelable(false)
		               .setIcon(R.drawable.ic_launcher)
		               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   MapaMonitoraActivity.this.finish();
		                   }
		               });
		             builder.create().show();  
		             finish();
		           return;
		       }    
		       
		       
		       
		       
		       
		       
		        txtCarro = (TextView) findViewById(R.id.txtCarro);
		        txtDist = (TextView) findViewById(R.id.txtDist);
		   //     txtLocal = (TextView) findViewById(R.id.txtlocal);
		       
		       
		       
		       
		       
	        
	        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	        
	        googleMap = mapFragment.getMap();
	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        googleMap.setTrafficEnabled(true);


	        
	        
	        
	        googleMap.setOnInfoWindowClickListener(
	        		  new OnInfoWindowClickListener()
	        		  {
	        		    public void onInfoWindowClick(Marker marker){
	        		    	EventInfo eventInfo = ListaObj.get(marker);
	        		    	String cod = eventInfo.getPonto();
	        		    	String parada = eventInfo.getParadacod();
	        		    	String sentido = Integer.toString(eventInfo.getSentido());
	        		    	
	        		    	/*
	        		    	Intent nextScreen = new Intent(MapaMonitoraActivity.this, MapaMonitoraActivity.class);
	        		        nextScreen.putExtra("parada", "" + parada);
	        		        nextScreen.putExtra("nome", "" + cod);
	        		        nextScreen.putExtra("sentido", "" + sentido );        		        
	        		        startActivityForResult(nextScreen, 0);
	        		        */
	        		        
	        		        
	        		    }
	        		    });

		    
	        
	        
	   
	        
	        
	        
	        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
	        	 
	        	
	        	
	        ArrayList<String> list = new ArrayList<String>();	
	          private final View contents = getLayoutInflater().inflate(R.layout.mapainfo, null);
	          private final View contentsponto = getLayoutInflater().inflate(R.layout.mapainfopara, null);
	          

	        	
	            @Override
	            public View getInfoWindow(Marker arg0) {
	                return null;
	            }
	 
	            // Defines the contents of the InfoWindow
	            @Override
	            public View getInfoContents(Marker marker) {

	                

	             EventInfo eventInfo = ListaObj.get(marker);

	             int sentido = eventInfo.getSentido();
	             int cor = 0;
	             if (sentido == 1)
	         	{
	         		cor = getResources().getColor(R.color.onibus1);
	         	}  else
	         	{
	         		cor = getResources().getColor(R.color.onibus1);
	         	}
	         	
	             
	             
	             
	          
	             
	               int parada = eventInfo.getParada();            
	                 
	                
	               if (parada > 0) {
	            	   
	            	   
	            	   
	            	    String ponto = eventInfo.getPonto();
	            	    String nome = null;
	            	    String ende = null;
	            	    
	            	    String[] items = ponto.split("@");
	            	   
	                       nome =  items[0].trim();
	                       ende = items[1].trim();
	            	    
	            	    
	            	    
	            	    
	            	    
	            	    
	            	   
	            	   
	            	   TextView txtTitulo = ((TextView) contentsponto.findViewById(R.id.txtTitulo));
	            	   TextView txtPonto = ((TextView) contentsponto.findViewById(R.id.txtPonto));
	            	   LinearLayout ratingCntr = ((LinearLayout) contentsponto.findViewById(R.id.ratingCntr));
	            	   
	            	   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	            			   LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


	            	   
	            	   
	            	   txtTitulo.setTextColor(cor);
	            	   txtPonto.setTextColor(cor);
	            	   
	            	   
	            	   
	            	    NumberFormat nf = NumberFormat.getNumberInstance();
	                	ratingCntr.removeAllViewsInLayout();
	                	
	                	ImageView iv = new ImageView(getBaseContext());
	                	TextView  tv = new TextView(getBaseContext());
	                
	                	ImageView iv1 = new ImageView(getBaseContext());
	                	TextView  tv1 = new TextView(getBaseContext());
	                
	                	
	                	
	                	//ImageView cad = new ImageView(getBaseContext());
	                	
	                    float distance = 0;
	                    float distance2 = 0;
	                    
	                	if (parada == 2) {
	                	 Location locationA = new Location("point A");
		  		         locationA.setLatitude(Double.parseDouble(latx));
		  		         locationA.setLongitude(Double.parseDouble(lonx));
		  		         Location locationB = new Location("point B");
		  		         locationB.setLatitude(lath);
		  		         locationB.setLongitude(lonh);
		  		         distance = locationA.distanceTo(locationB); 
		  		         iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.buspeq));
	                	 tv.setText(" Distância ônibus: "+nf.format(distance/1000)+"Km ");   	
		  		         
		  		         
		  		         
		  		         tv.setTextSize(12);                	
			             tv.setTextColor(cor);
			           	 	
	                	   	ratingCntr.addView(iv, layoutParams);	
		                	ratingCntr.addView(tv, layoutParams);
		                 
		  		         
		  		         
		  		         
		  		         
		  		         
		  		         
		  		         
		  		    	}
	                	
	                	
	                 	if (parada == 3) {
	                 		 double laty =    eventInfo.getaLat();
	                 		 double lony =    eventInfo.getaLon();
	                 		 
		                	 Location locationA = new Location("point A");
			  		         locationA.setLatitude(laty);
			  		         locationA.setLongitude(lony);
			  		         Location locationB = new Location("point B");
			  		         locationB.setLatitude(lath);
			  		         locationB.setLongitude(lonh);
			  		         distance = locationA.distanceTo(locationB);   
			  		         iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.buspeq));
		                	 tv.setText(" Distância ônibus: "+nf.format(distance/1000)+"Km ");
		                	
		                	tv.setTextSize(12);                	
		                	tv.setTextColor(cor);
		                	
		                	ratingCntr.addView(iv, layoutParams);	
		                	ratingCntr.addView(tv, layoutParams);
		                	
		                	
			  		    	}
		                	
	                	
	                	
	                 	if (parada == 1) {
		                 	
	                	
	                	
	                	
	                	iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_eu));
	                	tv.setText(" Distância: "+nf.format(distance)+"m       ");
	                	tv.setTextSize(12);                	
	                	tv.setTextColor(cor);
	                	ratingCntr.addView(iv);	
	                	ratingCntr.addView(tv);
	                   
	                		
	                 	}
	                	
	                	
	                	
	                   txtTitulo.setText(nome);
	                   txtPonto.setText(ende);
	      
	                   
	                   
	            	   return contentsponto;
	            	   
	               }
	                
	               else
	            	   
	            {
	            	   
	            	   
	                String ponto = eventInfo.getPonto();
	           	    String nome = null;
	           	    String ende = null;
	           	    String cadeira = null;
	           	    String carro = null;
	           	    
	           	    cadeira = eventInfo.getCadeirante();
	           	    
	           	    
	           	    
	           	    
	           	   
	           	   
	                      nome =  eventInfo.getPonto();
	                      ende = eventInfo.getLocalizacao();
	            	   
	            	   
	               
	               TextView txtLetreiro = ((TextView) contents.findViewById(R.id.txtTitulo));
	               TextView txtCarro = ((TextView) contents.findViewById(R.id.txtCarro));
	               TextView txtPonto = ((TextView) contents.findViewById(R.id.txtPonto));
	               TextView txtPrevi = ((TextView) contents.findViewById(R.id.txtPrevisao));
	               LinearLayout ratingCntr = ((LinearLayout) contents.findViewById(R.id.ratingCntr));
	              // TextView txtHora = ((TextView) contents.findViewById(R.id.txthora));
	            	  
	               Date d1=new Date();  
	               SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	               String datay = formatter.format(d1);
	               //txtHora.setText(datay);
	               
	               
	               
	               
	               txtCarro.setText("Carro: "+eventInfo.getVeiculo());//+"  "+eventInfo.getLocalizacao());
	               carro = eventInfo.getVeiculo();
	               
	               String monta = nome.replace("[1]", "");
	               String montax = monta.replace("[0]", "");
	               
	               
	               txtPonto.setText("Destino: "+montax);
	              
	               
	               txtLetreiro.setText(eventInfo.getLetreiro());
	               
	               
	               String tempo = CalculaTempo(eventInfo.getPrevisao());
	               
	               
	               
	               /*
	               Calendar cal = Calendar.getInstance();
	               Date currentLocalTime = cal.getTime();
	               SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");   
	               //date.setTimeZone(TimeZone.getTimeZone("GMT")); 
	               String localTime = date.format(currentLocalTime); 
	               String horabus = localTime.substring(0, 10)+" "+eventInfo.getPrevisao()+":00 BRT";
	               SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");  
	               Date datebus = null;
	               
	               
	               try {  
	                   try {
						datebus = format.parse(horabus);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	                   //System.out.println(date);  
	               } catch (ParseException e) {  
	                   // TODO Auto-generated catch block  
	                   e.printStackTrace();  
	               }
	               
	               
	               
	               //System.out.println(localTime);

	               long totalTime = datebus.getTime() - currentLocalTime.getTime();	 
	               
	               long seconds = totalTime / 1000;
	               long minutes = seconds / 60;
	               long hours = minutes / 60;
	               seconds = seconds % 60;
	               minutes = minutes % 60;
	               hours = hours % 60;
	               
	               
	               
	               
	               String monta = formatTime(totalTime);	   	           
	               String tempo = null;
	               
	               if (hours > 0)
	               {
	            	   tempo = monta+" h";   
	               }
	               
	   	           if ((minutes > 0) && (hours == 0))
	   	           {
	   	        	 tempo = monta.replace("00 : ", "");
	   	        	 tempo = tempo.substring(0,2)+" minutos";
	   	           }
	   	           
	   	         if ((minutes == 0) && (hours == 0))
	   	           {
	   	        	 tempo = monta.replace("00 : ", "")+" seg.";
	   	           }
	   	           
	   	         
	   	         */
	   	         
	   	         
	   	         
	   	        String previ =    eventInfo.getPrevisao();
	   	        String previok = previ.replace(":", "h");
	               
	   	        txtPrevi.setText("Previsão: "+previok+"   Espera +-: "+tempo);
	   	        txtPrevi.setTextColor(cor);
	            NumberFormat nf = NumberFormat.getNumberInstance();
             	ratingCntr.removeAllViewsInLayout();
             	
             	ImageView iv = new ImageView(getBaseContext());
             	TextView  tv = new TextView(getBaseContext());
             	//ImageView cad = new ImageView(getBaseContext());
             	
            
             	double dista = eventInfo.getDistponto();
             	distaok = 0;
             	
             	
             		
             	distaok = dista / 1000;
             		
             	
             	
            
             		iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto1));
             	
             	
             	
             	tv.setText(" Distância: "+nf.format(distaok)+" Km    ");
             	tv.setTextSize(14);                	
             	tv.setTextColor(cor);
             		
             	
             	
             	
             	ratingCntr.addView(iv);	
             	ratingCntr.addView(tv);
              
             	if (cadeira.equals("SIM")) {
             		
             	
          	//cad.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_cadeira));
             //	ratingCntr.addView(cad);	
             	}
	             
             	if (acabou == true)
             	{
             	contents.setBackgroundColor(getResources().getColor(R.color.amarelo));
             	}  
	               
	                return contents;

	        }
	                
	                
	               }

	        });

	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
			
	        listDataHeader = new ArrayList<String>();
	  	    listDataChild = new HashMap<String, List<String>>();
	  	    markerList = new ArrayList<Marker>();
	        
	  	    
	  	  iniciaMapa();
	  	    
	        
	  	  
		    
	  	 handler.postDelayed(runnable, 1000); 
		    
		    
		    
			}	    
		

		public void onBusMoniSPTransComplete(List<BusMoni> busmoni)
		 {
	    	
	    	
	    	String codlinha = null;
	    	int sentido  = 0;
	    	String letreiro0 = null;
	    	String letreiro1 = null;

	    	String headernome = null;
	    	
	    	temcarro = false;
	    	
	    	
	    	 for(int j=0; j < busmoni.size(); j++) 
	    	  {
	    		 
	    		 
	    		 BusMoni listano = busmoni.get(j);	    		 
	    		 com.abreuretto.busmoni.P  Pno =  listano.getP();
	    		 List<L>  Listalinhas =  Pno.getL();
	    		 
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
			  	
			  			  
	    			 
	    			 
	    			 List<com.abreuretto.busmoni.V>  ListaVeiculos =   linhas.getVs();
	    			 
	    			 
	    			 
	    			 for(int k=0; k < ListaVeiculos.size(); k++) 
			    	  {
	    				 
	    				 com.abreuretto.busmoni.V veiculos = ListaVeiculos.get(k);
	    				 String carro = veiculos.getP().toString();
	    				 
	    				 
	    				 
	    				 if ((codlinha.indexOf(linhaclick) >= 0) && (carro.equals(carroclick))) 
		  				  {					
				  			 temcarro = true;
				  			 contaespera = 0;				  		
				  		 	 temcarro = true;
				  			 contaespera = 0;
				  			 espera = false; 
				  			 PegaCarro(linhas, veiculos);
					     	 Finaliza(); 								  			 
				  			 return;
	    				 
		  				  } else
		  				  {
		  					  
		  				  }
	    			 
	    				
	    	  					  
	    				 } 
	    				 
	    				 
			    	  }
	    			 
	    			 
	    			 
		    	  }	 
	    		 
	    	    Finaliza(); 
	    		 
	    		 
	    	  }
	    	
	    	  
	    	
	    	
		
	    
	    public void onBusMoniSPTransFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
	    
	
		
		
		public String CalculaTempo(String hora)
		{
			
			 
			
			
			  SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              Date currentDate = new Date();
				   String agora  = formatter.format(currentDate.getTime());
				   String depois = agora.substring(0,10)+" "+hora+":00";
				   
				   
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
			
			
			
			
			
			
			
			
			
			
			/*
			
			
	   		
			
			Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();
            
            int anoa = cal.get(Calendar.YEAR);
            String ano = String.valueOf(anoa);
              
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        	
            
            
            
            
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");   
            date.setTimeZone(TimeZone.getTimeZone("GMT")); 
            String localTime = date.format(currentLocalTime); 
            String horabus = localTime.substring(0, 10)+" "+hora+":00 BRT";
            SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z"); 
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            Date datebus = null;
            
            try {  
                try {
					datebus = format.parse(horabus);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
                //System.out.println(date);  
            } catch (ParseException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }
            
            
            //System.out.println(localTime);

            

            Date date1 = null;
            Date date2 = null;
            Date date3 = null;
            
            String anox = String.valueOf(datebus.getYear()+1900);
            String mesx = String.valueOf(datebus.getMonth());
            String diax = String.valueOf(datebus.getDay());
            
            
        	try {
				
        		 date1 = sdf.parse(ano+"-10-18 23:59:59 BRT");
        		 date2 = sdf.parse(ano+"-02-15 23:59:59 BRT" );
        	//	 date3 = sdf.parse(anox+"-"+mesx+"-"+diax);
				
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
            
            
            
            
            
            
        	Calendar cal1 = Calendar.getInstance();
        	Calendar cal2 = Calendar.getInstance();
        	Calendar cal3 = Calendar.getInstance();
        	
        	cal1.setTime(date1);
        	cal2.setTime(date2);
        	cal3.setTime(datebus);
 
        	
 */
        	           
            
            
            
            long totalTime = (datedepois.getTime() - dateagora.getTime());            
            long seconds = totalTime / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 60;
            

            
            
            String monta = formatTime(totalTime);	   	           
            String tempo = null;
            
            if (hours > 0)
            {
         	   tempo = monta+" h";   
            }
            
	           if ((minutes > 0) && (hours == 0))
	           {
	        	 tempo = monta.replace("00 : ", "");
	        	 tempo = tempo.substring(0,2)+" minutos";
	           }
	           
	         if ((minutes == 0) && (hours == 0))
	           {
	        	 tempo = monta.replace("00 : ", "")+" seg.";
	           }
	           
	        String previ =    hora;
	        String previok = previ.replace(":", "h");
            
	        return tempo;
			
			
			
		}
		
		
		
		public String CalculaTempo2(Date dt1, Date dt2)
		{
			long totalTime = (dt1.getTime() - dt2.getTime());            
        	String tempo = formatTime(totalTime);
		    return tempo;
			
		}
		
		
		
		
		private Runnable runnable = new Runnable() {
	    	
	    	
	    	
	    	@Override
	    	   public void run() {
	    	      /* do what you need to do */
	    	      conta = conta + 1;
	    	      tempoja =  new Date();
	    	      tempocalculado = CalculaTempo2(tempoja, tempoini);
	    	      setTitle("Coletando SPTRANS: "+conta+" vezes   "+tempocalculado);
	    	     
	    	      
	    	      
	    	      
	    	      if (acabou == true) 
	    	      {return;}	 
	    	      
	    	      if (espera == false)
	    	      {
	    	      espera = true;	  
	    	 	  PegaParada(paradaclick);
	    	      }
	    	      
	    	      
	    	      handler.postDelayed(runnable, 3000); 
	              
	    	   }
	    	};
		


		private String formatTime(long millis) {
          String output = "00:00:00";
          long seconds = millis / 1000;
          long minutes = seconds / 60;
          long hours = minutes / 60;

          seconds = seconds % 60;
          minutes = minutes % 60;
          hours = hours % 60;

          String secondsD = String.valueOf(seconds);
          String minutesD = String.valueOf(minutes);
          String hoursD = String.valueOf(hours);

          if (seconds < 10)
                secondsD = "0" + seconds;
          if (minutes < 10)
                minutesD = "0" + minutes;
          if (hours < 10)
                hoursD = "0" + hours;

          output = hoursD + " : " + minutesD + " : " + secondsD;
          return output;
    }
	
		
	
		
		 public void animateMarker(final Marker marker, final LatLng toPosition,
		            final boolean hideMarker) {
		        final Handler handler = new Handler();
		        final long start = SystemClock.uptimeMillis();
		        Projection proj = googleMap.getProjection();
		        Point startPoint = proj.toScreenLocation(marker.getPosition());
		        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		        final long duration = 500;

		        final android.view.animation.Interpolator interpolator = new LinearInterpolator();

		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		                long elapsed = SystemClock.uptimeMillis() - start;
		                float t = interpolator.getInterpolation((float) elapsed
		                        / duration);
		                double lng = t * toPosition.longitude + (1 - t)
		                        * startLatLng.longitude;
		                double lat = t * toPosition.latitude + (1 - t)
		                        * startLatLng.latitude;
		                marker.setPosition(new LatLng(lat, lng));

		                if (t < 1.0) {
		                    // Post again 16ms later.
		                    handler.postDelayed(this, 16);
		                } else {
		                    if (hideMarker) {
		                        marker.setVisible(false);
		                    } else {
		                        marker.setVisible(true);
		                    }
		                }
		            }
		        });
		    }	
		
		
			
		public void iniciaMapa()
		{
			 LatLng latLng = null;
			 latLng = new LatLng(Double.parseDouble(latx) , Double.parseDouble(lonx));
			 
			 
			   MarkerOptions markerOptions = new MarkerOptions();
			   markerOptions.title("Você");
			   markerOptions.position(latLng);
			   markerOptions.snippet("aqui");
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.eu));			   
			 
			 
			 
		        
		        
		        
		        googleMap.getUiSettings().setCompassEnabled(true);
		        googleMap.getUiSettings().setZoomControlsEnabled(true);
		        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		        //googleMap.animateCamera(cameraUpdate);
		        
		     // Move the camera instantly to Sydney with a zoom of 15.
		        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

		        // Zoom in, animating the camera.
		        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

		        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
		        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
		        
		       
		        EventInfo ob = new EventInfo();
		        ob.setParada(2);
		        ob.setPonto("Você @ ");
		        ob.setLatLong(latLng);
		        ob.setaLat(Double.parseDouble(latx));
		        ob.setaLon(Double.parseDouble(lonx));
		        ob.setSentido(0);
		        Marker marker = googleMap.addMarker(markerOptions); 
		           
		        ListaObj.put(marker, ob);
		        
		        
		        
		        
		        Circle circle = googleMap.addCircle(new CircleOptions()
		        .center(latLng)
		        .radius(500)
		        .strokeColor(Color.GRAY)
		        .strokeWidth(2)
		        .fillColor(0x40cccccc));
		        
		        
		         LatLng latLngPara = null;
				 latLngPara = new LatLng(Double.parseDouble(latparada) , Double.parseDouble(lonparada));
			        
				 MarkerOptions markerOptions2 = new MarkerOptions();
				   
			        
			         markerOptions2.title(nomeparadaclick);
					 markerOptions2.position(latLngPara);
					 markerOptions2.snippet(letreiro);
					 markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));			   
					 
				      EventInfo ob1 = new EventInfo();
					          
					    ob1.setSentido(0);
					    ob1.setParada(3);
				        ob1.setPonto(nomeparadaclick +"@ ");
				        ob1.setLatLong(latLngPara);
				        ob1.setaLat(Double.parseDouble(latparada));
				        ob1.setaLon(Double.parseDouble(lonparada));
				        latparah = Double.parseDouble(latparada);
				        lonparah = Double.parseDouble(lonparada);
				        
				            
				        Marker marker2 = googleMap.addMarker(markerOptions2); 
				           
				        ListaObj.put(marker2, ob1);
				            
			        
			     			        
			       
			        
		        
		        
		        
		        
			
			
		}
		
		
		    
	   public void montamapaponto(EventInfo ob)
	   {
		   
		   
		   if (prima == true) 
		   {
			prima = false;	   
		   
		   latponto =ob.getaLat();
		   lonponto =ob.getaLon();		   
			
			
		   LatLng latLng = null;
		   latLng = new LatLng(ob.getaLat() , ob.getaLon());	   
		   String dista = Double.toString(ob.getDistyou());	   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(ob.getPonto());
		   markerOptions.snippet(dista);
		   
		   if (ob.getSentido() == 1)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));
		   if (ob.getSentido() == 2)
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto2));
		   
		   
		   Marker marker = googleMap.addMarker(markerOptions); 
		   
		  // marker.showInfoWindow();
		   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		   
		   ListaObj.put(marker, ob);
		   
		   
		   
		   
		   
		   
		   espera = false;
		   }
		   
		   		   
			   
	   }
	   
	   
	   
	   public void onInfoWindowClick1(Marker marker) {
		    Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
		  }

	   
	   
	  
	   
	   
	   
	   
	   public void montamapaonibus_vai(EventInfo oni)
	   {
		   
		   boolean tem_marca = true;
		   
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistponto());	   
		   String carro = oni.getVeiculo();
		   boolean mostrar = false;
		 //  Marker marca = null;
		   
		   
		   
		  // googleMap.clear();
		   
		   
		  
		   
		   
		   if (posicao != null)
		   {
			   
			  
			   
			  /* 
			   Polyline line = googleMap.addPolyline(new PolylineOptions()
			     .add(marca.getPosition(), oni.getLatLong())
			     .width(5)
			     .color(Color.RED));
			   */
			  
			   double lng1 = lonponto;
			   double lat1 = latponto;
			   double lat2 = oni.getaLat();
			   double lng2 = oni.getaLon();
			   
			   
			   
			   double dLon = (lng2-lng1);
			   double y = Math.sin(dLon) * Math.cos(lat2);
			   double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
			   double brng = Math.toDegrees((Math.atan2(y, x)));
			   int brngx = (int) Math.round( (360 - ((brng + 360) % 360)));
		
			   
			   
			   
			   NumberFormat nf = NumberFormat.getNumberInstance();
               double distaok2 = oni.getDistponto() / 1000;
			   
               
               /*
               txtDist.setText("Distância: "+nf.format(distaok2)+"km");
			   txtCarro.setText("Carro: "+carro);
			   */
               
               
               pegaende(Double.toString(oni.getLatLong().latitude), Double.toString(oni.getLatLong().longitude));
               
               
               txtDist.setText(endereco);
			   //txtCarro.setText("Carro: "+carro);
			   
               
               
               
			   
			   
			   //txtCarro.refreshDrawableState();
			   txtDist.refreshDrawableState();
			   
			   LatLng nova = null;
			   nova = new LatLng(oni.getLatLong().latitude, oni.getLatLong().longitude);
			   
			   
			   

			   
			   
			   
			   
			   if ((latlonold.latitude == nova.latitude) && (latlonold.longitude == nova.longitude)) 
			   {
				   return;
				   
			   } else
			   {
				   latlonold = oni.getLatLong();
			   }
			   
			   
			   
			   
			   
			   
			   posicao.setPosition(oni.getLatLong());			   
			   posicao.showInfoWindow();
			   
			   
			   CameraPosition cameraPosition = new CameraPosition.Builder()
			    .target(oni.getLatLong())      // Sets the center of the map to Mountain View
			    .zoom(17)                   // Sets the zoom
			    .bearing(brngx)                // Sets the orientation of the camera to east
			    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
			    .build();                   // Creates a CameraPosition from the builder
			   googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			   
			   
			   
			  
			  // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oni.getLatLong(), 16));
			   
			   
			  // oni.setMarca(marker);
			   //atualiza(oni);
			   
			   
			   
			   //oni.setMarca(marca);
			   //atualiza(oni);
			   
			   
			   //marca.remove();
			
			   
			  
               
               
               		  				
              
				  
			   
			   
			   //double distay = oni.getDistponto()/1000;
			   //double distah = 1.500;
			   
			   
			  
			   
			   
			   /*
			   
			   if (distay <= distah) 
			   {
				   
				   
				   contasom = contasom +1;
				
				   if ((contasom < 2) || (temcarro == false))
						   {
					   
							   if(mediaPlayer!=null && mediaPlayer.isPlaying())
							   {
								   mediaPlayer.stop();
							   }
		
						   mediaPlayer.start();
						   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						   v.vibrate(400);
						   
						   } 
				              else
						   {
							   if(mediaPlayer!=null && mediaPlayer.isPlaying())
							   {
								   mediaPlayer.stop();
								   
							   }
							   finish();
							   acabou = true;
							   espera = true;
							   return;
						   }
				 
				   
			   }
			   
			   
			   */
			   
			   
			   espera = false;
			   
			      
		   }
			   
			   else
			   
		   {
		  
		   
		   
		   
		   
		   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   
		   
		   
		   
		   
		   if (carroclick.indexOf(carro) >= 0)
		   {
			   NumberFormat nf = NumberFormat.getNumberInstance();
               double distaok3 = oni.getDistponto() / 1000;
			   txtDist.setText(endereco);
			   txtCarro.setText("Carro: "+carro);
			   txtCarro.refreshDrawableState();
			   txtDist.refreshDrawableState();
			   
			   
			   
			
			   
			   
			   
			   if (oni.getDistponto() <= 0.500) {
				   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				   v.vibrate(400);			 
				  
			   }
			   
			   
			   
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busidacs));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busidas));
			   }
			  // mostrar = true;
			   
		   } 
		   
		   
		   
		   else
		   
		   
		   {
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busidac));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busida));
			   }
		   }
		   
		   posicao = googleMap.addMarker(markerOptions); 
		

		   ListaObj.put(posicao, oni);		   
		   espera = false;
		   
		   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		   
		   try
		   {
		   posicao.showInfoWindow();
		   }
		   catch (Exception e)
		   {
			   
			   Toast.makeText(this, posicao.getTitle(), Toast.LENGTH_LONG).show();
		   }
		   
		   
		   
		   
		   }
		   
		   
		  
		   
		   
		   
		   
		
		  
			
		  
		  
	   }
	   
	   
	   public void montamapaonibus_vem(EventInfo oni)
	   {
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistponto());	
		   String carro = oni.getVeiculo();
		   boolean mostrar = false;
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   
		   //googleMap.clear();
		   
		   if (carroclick.indexOf(carro) >= 0)
		   {
			   NumberFormat nf = NumberFormat.getNumberInstance();
               double distaok4 = oni.getDistponto() / 1000;
			   txtDist.setText(endereco);
			   txtCarro.setText("Carro: "+carro);
			   
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvoltacs));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvoltas));
			   }
		   mostrar = true;
		   if (oni.getDistponto() <= 0.500) {
			   
			   
			   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			   v.vibrate(400);
			   
			   acabou = true;
		   }
		   } else
		   {
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvoltac));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvolta));
			   }
		   }
		   Marker marker = googleMap.addMarker(markerOptions);   
		   
		   if (mostrar == true) {
			   marker.showInfoWindow();
			   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		   }
		   
		   espera = false;
		   ListaObj.put(marker, oni);
		   markerList.add(marker);	
		   
	   }	
		
		
		
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.mapa_geral, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		     

		       
		        case R.id.action_about:
		            pegaAbout();
		            return true;
		            
		            
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	    
		
		public void pegaAbout()
		{
	        Intent intent = new Intent(this, SobreActivity.class);
	        startActivity(intent);
		}	
		

		


				  
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

				
				
				
				
				
					
				
				protected void PegaParada(String parada)
				{
				
					
					if (!cd.isConnectingToInternet()) {
				           AlertDialog.Builder builder = new AlertDialog.Builder(this);
				           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
				               .setMessage(this.getResources().getString(R.string.interneterror))
				               .setCancelable(false)
				               .setIcon(R.drawable.ic_launcher)
				               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                	   MapaMonitoraActivity.this.finish();
				                   }
				               });
				            // builder.create().show();  
				             espera = false;
				             //finish();
				           return;
				       }    
				       
					
					
					
					
					
				 paradacodw = parada;					
				 String finalURLStr = "http://api.olhovivo.sptrans.com.br/v0/Previsao/Parada?codigoParada="+parada;       // API Call
				 String url = finalURLStr.toString();
				 BusMoniSPTrans task3 = new BusMoniSPTrans(this);
				 task3.execute(url,latx,lonx);
				 Date d1=new Date();
				 Log.d("abreuretto", "pegaparada:"+parada+"   "+d1.toString()); 
				 
				 
				 
				}
				private class PegaOnibusTask extends AsyncTask<String, Integer, String> {
				     
					   public PegaOnibusTask(MapaMonitoraActivity mapaMonitoraActivity) {
						// TODO Auto-generated constructor stub
					}

					protected String doInBackground(String... params) {
						  
						      String urlF = params[0];
						      String result1 = null;				      
						     
						   try {
						          HttpClient clientF = new DefaultHttpClient();
						          HttpGet httpgetF = new HttpGet(urlF);
						          HttpResponse responseF = clientF.execute(httpgetF);
						          HttpEntity entityF = responseF.getEntity();
						      
						          if(entityF == null) {
						              //msg = "No response from server";
						              result1 =" ";
						             // return null;        
						          }
						          InputStream isF = entityF.getContent();
						         // return 
						          
						          String convF = streamToString(isF);
						          
						         	result1 = convF;
						         	//Log.d("valorori", streamToString(isF));
						      }
						      catch(IOException e){
						          //msg = "No Network Connection";
						    	  espera = false;
						          result1 = " ";
						      }
						        
						      return result1;  

					     }

					     protected void onProgressUpdate(Integer... progress) {
					         //setProgressPercent(progress[0]);
					     }

					     protected void onPostExecute(String sJson) {
					    	 
					    
					    	   JSONObject jsonF  = null;
					    	   JSONObject jtudo = null;
					    	   JSONArray resultArray = null;
					    	   JSONArray busArray = null;
					    	   JSONObject det = null;
					    	   JSONObject cada = null;
				         	   String linha = null;
					  		   String sentido1 = null;;
					  		   String sentido2 = null;
					  		   String four = null;
					  	     String carro = null;					  		    
					  		   int veiculos = 0;
					  		   
					    	if(sJson == null) {
					    		System.err.println("erro0");
					    		espera = false;
					            return;
					        }        
					         
					        
					    	
					    
					    	try
					        {   
					        four = sJson.toString();
					  		  } catch (Exception e) {
					              System.err.println("erro1");
					              espera = false;
					              return;
					        } 
					        
					    	try
					    	{			    	
					    	jtudo = new JSONObject(four).getJSONObject("PrevisaoResult");
					  		  } catch (JSONException e) {
					              System.err.println("erro2");
					              espera = false;
					              return;
					        } 
				
					    	try
					    	{			    	
					    	jsonF = jtudo.getJSONObject("p");
					    	codparada = jsonF.getString("cp");
					  		  } catch (JSONException e) {
					              //System.err.println("erro3");
					              espera = false;
					              temcarro = false;
					              Finaliza();
					              return;
					        } 
					    	
					    	
					    	ParadaLista();
					    	
					    	
					    	try
					    	{			    	
					    	String hora =  jtudo.getString("hr");
					  		  } catch (JSONException e) {
					  			 espera = false; 
					              System.err.println("erro4");
					              return;
					        } 
					    	
					    	
					    	try
					    	{			    	
					    	resultArray = jsonF.getJSONArray("l");
					    	} catch (JSONException e) {
					    		  espera = false;
					              System.err.println("erro6");
					              return;
					        } 
					    	
					    	
					    	 temcarro = false;
					    	  
					          if (resultArray.length() != 0) 
						      {
					        	  
					        	  
						    	  for(int j=0; j< resultArray.length(); j++) 
						    	  {
						  			 try
						  			 {
						  			 det = resultArray.getJSONObject(j);			 
				    	  			 } 
						  			  catch (Exception ex) 
						  			  {
						  				espera = false; 
						  				return;
						  			  } 
						  			
						  			 
						  			 
						  			 
						  			 try
						  			 { 					  		    
						  			    busArray = det.getJSONArray("vs");		  			
						  				for(int k=0; k< busArray.length(); k++)
								  		{
								  		  det      = resultArray.getJSONObject(j);			 
								  		  linha    = det.getString("c");	
								  		  cada     = busArray.getJSONObject(k);
								  		  carro    = cada.getString("p");
								  	
								  		  if ((linha.indexOf(linhaclick) >= 0) && (carro.equals(carroclick))) 
						  				  {					
								  			 temcarro = true;
								  			 contaespera = 0;
								  			// PegaCarro(det, cada);
								  			 espera = false; 
								  			 Finaliza(); 								  			 
								  			 return;
								  			 
								  		   } else
						  				   {
						  					  
						  			       }
								  		}
						  			 }
						  				catch (Exception e)
						  			{
						  					 espera = false; 
								  		
						  			}
						    	  }
						  				
						  			   				  		            
				  		            
						      }
						      
				   
					          
					         
					       Finaliza();   
					          
					          
					          
					    
				}
				   

				}
					   
				
				
				public void Finaliza()
				{
					
					double compa = 450;
					String tempos = tempocalc;
					String minu = tempos.replace(" minutos", "");
					int minutos = 0;
					
					try
					{
					minutos = Integer.parseInt(minu);
										
					} catch (Exception e)
					{
						minutos = 0;
					}
					
					
					
					 if ((temcarro == false) || (distakm < compa) || (minutos <= 1))
					   {
						   
						 contaespera = contaespera + 1;
						 
						 Log.d("abreuretto", "Finaliza: "+contaespera );
						 
						 if ((contaespera > 60) || (distakm < compa) || (minutos <= 1))
						 {
								   if(mediaPlayer!=null && mediaPlayer.isPlaying())
								   {
									   mediaPlayer.stop();
								   }
		
									 
									   acabou = true;
									   espera = true;
									   
									   mediaPlayer.start();
									   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
									   v.vibrate(400); 
									   
									   
									   if (posicao != null)
									   {
										   
										   
										   getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
										 //  Intent intent = getIntent();
									     //  startActivity(intent);	   
								       posicao.showInfoWindow();
								       //Intent intent = getIntent();
								       //startActivity(intent);
								       //finish();
								    
									   }
									   
									   if (contaespera > 60)
									   {
										   Toast.makeText(this, "Carro: "+carroclick+" .O SPTrans parou de enviar registros para este carro nesta parada.", Toast.LENGTH_LONG).show();
									   }
									   
									   
									   /*
									   
									   if (listanomapa.size()>1) 
									   {
										   
										   
										   
										   
										   
										   if (posicao != null)
										   {
									       posicao.showInfoWindow();
										   Toast.makeText(this, "Carro: "+carroclick+" .O SPTrans parou de enviar registros para este carro nesta parada.", Toast.LENGTH_LONG).show();
										   }
										   
										   
									   } 
									   
									   else
									   
									   {
										   
										   Toast.makeText(this, "Carro: "+carroclick+" próximo a parada ou já passou! O SPTrans não enviou o registro de monitoramento para este carro nesta parada.", Toast.LENGTH_LONG).show();
									   }
									  
									  */
									   
						 } else
						 {
							 espera = false;
						 }
						   
					   }  
					 
					 
					 else
						   
					   {
						   espera = false; 
					   }
				
				
					
					
				}
				
			
				
				public void onPause(){
					super.onPause();
					}
					
				public void onResume(){
					super.onResume();
					}
				
				
				public void PoeMensagem(String msg)
				{
					
					 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
			               .setMessage(msg)
			               .setCancelable(false)
			               .setIcon(R.drawable.ic_launcher)
			               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   MapaMonitoraActivity.this.finish();
			                   }
			               });
			             builder.create().show(); 
					
					
				}
				
				
				
				
				
				
				
					     
				public void ParadaLista()
				{
					    EventInfo appM2 = new EventInfo();  
			            LatLng latLng = new LatLng(Double.parseDouble(latparada) , Double.parseDouble(lonparada));					  		 	
			  		 	appM2.setaLat(Double.parseDouble(latparada));
			  	        appM2.setaLon(Double.parseDouble(lonparada));
			  	        appM2.setSentido(1);
			  	        appM2.setId(paradacodw);
			  	        appM2.setPonto(nomeparadaclick);
			  	        appM2.setLatLong(latLng);        
			  	        appM2.setParadacod(paradacodw);
			  	        appM2.setParada(1);
			  	        appM2.setTipo(1);
			  	        appM2.setPonto(nomeparadaclick);						  	        
			  	        Location locationA = new Location("point A");
	  		            locationA.setLatitude(Double.parseDouble(latx));
	  		            locationA.setLongitude(Double.parseDouble(lonx));
	  		            Location locationB = new Location("point B");
	  		            locationB.setLatitude(Double.parseDouble(latparada));
	  		            locationB.setLongitude(Double.parseDouble(lonparada));
	  		            float distance = locationA.distanceTo(locationB);   
	  		            appM2.setDistyou(distance);					  		            
	  		            Date d1=new Date();
	 				    Log.d("abreuretto", "parada:"+paradacodw+"   "+d1.toString()+"  conta: "+conta);
	  		            if (prima == true) 
	  		            {
	  		            listanomapa.add(appM2);
	  		            montamapaponto(appM2); 
	  		            }
	  		          espera = false;
				}
				
				
				
				
				public EventInfo VeSeExiste(String compara)
				{
					
					EventInfo objeven = null;
					EventInfo achouobj = null; 
					
					for(int h=0; h < listanomapa.size() ; h++) 
			    	  {
						objeven = listanomapa.get(h);  					
	  					int tipo = objeven.getTipo();
						int para = objeven.getParada();
						if ((tipo == 1) && (para == 0)) 
						{
							
							String chave = objeven.getId();
							if (chave.equals(compara)) 
							{
								achouobj =  objeven;
								return achouobj;
								
							}
						}
			    	  }
					
					return achouobj;
					
					
				}
				
				
				
				
				
				
				
				 
				
				
				
				
				
				
				public String streamToString(final InputStream is) throws IOException{
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
				
				
				
				
				public void PegaCarro(L linhas, V veiculos )
				{
					
					   
			    	   
		         	   String linha = null;
			  		   String sentido1 = null;;
			  		   String sentido2 = null;
			  		   String carro = null;					  		    
			  		   boolean defi = false;
			  		   String cadeira = null;
			  		   String tempo = null;
			  		   float distance = 0;
			  		   NumberFormat nf = null;			  		  
			  		   String poedist = null;
			  		  
			  		   
			  		 EventInfo appM1 = new EventInfo();   
			  		   
			  		 try
			  		 {
			  		 linha    = linhas.getC().toString();	
			  		 linhaW   = linha;
			  		 sentido1 = linhas.getLt0().toString();
			  		 sentido2 = linhas.getLt1().toString();
		  		     sentido  = linhas.getSl();								  		    
		  			 defi     = veiculos.getA();				  
	  			     carro    = veiculos.getP().toString();
	  			     tempo    = veiculos.getT().toString();  
	  				 lath     = veiculos.getPy();
 			    	 lonh     = veiculos.getPx(); 
 			    	 tempocalc = CalculaTempo(tempo);
			  		 }
			  		 catch (Exception e)
			  		 {
			  			Log.d("abreuretto", "linha:"+linha+"   Exception PegaCarro  conta: "+conta);
			  			espera = false;
			  			 return;
			  		 }
 			    	 
 			    	 
 			    	 
 			    	  if (defi==true) {
 		  					cadeira = "SIM";  
 		  				  }
 		  				  else
 		  				  {
 		  					cadeira = "NÃO";  
 		  				  }
 			    	 Location locationA = new Location("point A");
 		             locationA.setLatitude(Double.parseDouble(latparada));
 		             locationA.setLongitude(Double.parseDouble(lonparada));
 		             Location locationB = new Location("point B");
 		             locationB.setLatitude(lath);
 		             locationB.setLongitude(lonh);
 		             distance = locationA.distanceTo(locationB);     
 		           
 		             
 		             nf = NumberFormat.getNumberInstance();
			          
			         distakm  = (int) Math.round(distance); 
			          
			          
			       
			    
			      	boolean achou = false;
	  				
			      	EventInfo objretorno = null;
			      	
			      	objretorno = VeSeExiste(linha+"_"+carro);
			      	
			      	
			      	if (objretorno != null)
			      	{
			      		
			      		achou = true;
			      		objretorno.setaLatold(objretorno.getaLat());
			      		objretorno.setaLonold(objretorno.getaLon());
			      		objretorno.setaLat(lath);
			      		objretorno.setaLon(lonh);
		  				LatLng latLng = new LatLng(lath , lonh);
		  				objretorno.setLatLong(latLng);
		  				objretorno.setDistponto(distance);
		  				objretorno.setPrevisao(tempo);
		  				objretorno.setTempo(tempocalc);
		  				objretorno.setCadeirante(cadeira);
		  				Log.d("abreuretto", "lat:"+lath+" Lon:"+lonh+"  Distancia: "+distance);
						atualiza(objretorno);
						
		  				if (sentido == 1) 
	  				    {
		  				 montamapaonibus_vai(objretorno);
			  		    }  else
	  			        {
			  		      montamapaonibus_vai(objretorno);
			  			} 
			      		
		  			 espera = false;
			      	 return;
		  				
			      	}
			      	
			      	
			      	
	  					appM1.setId(linha+"_"+carro);
		  				appM1.setTipo(1);
		  				appM1.setVeiculo(carro);
		  				appM1.setPonto(nomeparadaclick);
		  				appM1.setPrevisao(tempo);
		  				appM1.setTempo(tempocalc);
		  				appM1.setCadeirante(cadeira);
		  				appM1.setDistponto(distance);
		  				appM1.setSentido(sentido);
		  				appM1.setParadacod(codparada);
		  				paradacodw = codparada;
		  				appM1.setaLat(lath);
		  				appM1.setaLon(lonh);
		  				LatLng latLng = new LatLng(lath , lonh);
		  				appM1.setLatLong(latLng);
		  				String letreiros = null;
		  				appM1.setParada(0);
		  				listanomapa.add(appM1);
		  				if (sentido == 1) 
	  				    {
	  					appM1.setLetreiro(sentido1+" | "+linha);
	  					letreiros = sentido1+" | "+linha;
	  					 montamapaonibus_vai(appM1);
			  		    }  else
	  			        {
			  		    	appM1.setLetreiro(sentido2+" | "+linha);
			  		    	letreiros = sentido2+" | "+linha;
		  				  montamapaonibus_vai(appM1);
			  			} 
		  				
		  				
		  				espera = false;
		  			
				}
				
				
				
				
				
				
				
				
				
				
		
				public void atualiza(EventInfo oni)
				   {
					   String id = oni.getId();  
					   for(int h=0; h < listanomapa.size() ; h++) 
				    	  {
							EventInfo objeven = listanomapa.get(h);				
							int tipo = objeven.getTipo();
							int para = objeven.getParada();			
							if ((tipo == 1) && (para == 0)) 
							{
								String chave = objeven.getId();
								if (chave.equals(id)) 
								{
									listanomapa.set(h, oni);
								}
							}
				   }
				   }
						
				// Code sample requires Android version 2.2 or later
				
				// Constants
				
				
				public void MetaData_UserMetaDataValue_Set() {
				  
				  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_UserMetaDataValue_Set";           //  API Call 
				  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME); 
				  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
				  finalURLStr = finalURLStr + "&userToken=" + encodeValue(usertoken);                   // Logged-in user’s user-token
				  finalURLStr = finalURLStr + "&MetaKey=" + encodeValue("favorito_"+linhaclick+"_"+paradaclick);                          // Metadata key
				  finalURLStr = finalURLStr + "&MetaValue=" + encodeValue(nomeparadaclick+"__"+letreiro);                      // Metadata value
				  finalURLStr = finalURLStr + "&MetaLatitude="+latparada ;             // Location latitude 
				  finalURLStr = finalURLStr + "&MetaLongitude="+lonparada;           // Location longitude
				  finalURLStr = finalURLStr + "&ApplicationTag=";  // App related data
				  finalURLStr = finalURLStr + "&RESERVED=";                                             // Leave empty
				    
				
				
			    	    
			    	  String url = finalURLStr.toString();
			    	  PoeFavorito pontotask = new PoeFavorito(this);
					 pontotask.execute(url,latx,lonx);
			    	}

			    
			    
			    
			    
			    private class PoeFavorito extends AsyncTask<String, Integer, String> {
				     
					   public PoeFavorito(MapaMonitoraActivity mapaMonitoraActivity) {
						// TODO Auto-generated constructor stub
					}

					protected String doInBackground(String... params) {
						String urlF = params[0];
				        String urlG = params[1];
				        
				        
				        String result1 = null;
					      
					      latx =  params[1];
					      lonx =  params[2];
					        
					        try {
					            HttpClient clientF = new DefaultHttpClient();
					            HttpGet httpgetF = new HttpGet(urlF);
					            HttpResponse responseF = clientF.execute(httpgetF);
					            HttpEntity entityF = responseF.getEntity();
					            if(entityF == null) {
					            	System.err.println("erro 32");
					                result1 = null;
					            }
					            InputStream isF = entityF.getContent();
					            String convF = streamToString(isF);
					           	result1 = convF;
					        }
					        catch(IOException e){
					        	System.err.println("erro 33");
					            result1 = null;
					        }
					        return result1;    
				     }


					protected void onProgressUpdate(Integer... progress) {
				         //setProgressPercent(progress[0]);
				     }

				     protected void onPostExecute(ArrayList<String> sJson) 
				     {
				    	 
				     }
				     
				     
				     
				     }
			    
			    
			    public void onPegaDadosGeocodeComplete(final String ende) {
					 if(dialog != null)  dialog.dismiss();
				       
					 if (ende.indexOf("RMSP") >= 0)
				 		 {		    	    
				           
						           
					      endereco = ende;   	    
						    
				    	 }
						  else
						  {
							Toast.makeText(MapaMonitoraActivity.this, "Local selecionado fica fora da cidade de São Paulo!", Toast.LENGTH_LONG).show(); 
							
							endereco = "";
							
			           	  }
				 
				      // task.cancel(true);
				   }   
				 
				 
				   
				   
				   
				   
				   public void onPegaDadosGeocodeFailure(String msg) {
				       if(dialog != null)  dialog.dismiss();
				   }   

			  	
			    public void pegaende(String lat, String lon)
			    {
			    	   	String url =  "http://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lon+"&zoom=18&addressdetails=1";
		              	task =	new PegaDadosGeocode(MapaMonitoraActivity.this);
		                task.execute(url);
			    }

			   

				

				
				
				
				
	}

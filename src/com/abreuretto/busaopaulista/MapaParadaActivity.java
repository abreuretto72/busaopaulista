package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
//import java.sql.Date;
//import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;



import com.abreuretto.busaopaulista.R;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Point;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;








import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




public class MapaParadaActivity extends FragmentActivity {

	SharedPreferences shared_preferences;
	  SharedPreferences.Editor shared_preferences_editor;
	    String latx = null;
	    String lonx = null;
	    String paradacodw = null;
	    
	    String paradaclick = null;
	    String linhaclick = null;
	    String carroclick = null;
	    String nomeparadaclick = null;
	    
	    
	    
	    
	    int conta;
		int registros = 0;
		 double lathx = 0; 
	  	 double lonhy =  0;
	  	 String paradanome = null;
		 String codparada = null;
		 int sentido  = 0;
		 String latparada = null;
		 String lonparada = null;
		 
		 
		 
	    
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
		
		
			
		  
		  
		  
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_mapa_parada);	
			
			
			
	        shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
		    latx = shared_preferences.getString("lat", "");
		    lonx = shared_preferences.getString("lon", ""); 
		    
		     
		    
		    
			
		   // latx = ("-23.603141"); 
		   // lonx = ("-46.665977");
		    
		     
		    Bundle extras = getIntent().getExtras(); 
		    
		       paradaclick = extras.getString("parada");
		       linhaclick = extras.getString("linha");
		       carroclick = extras.getString("carro");
		       nomeparadaclick = extras.getString("nomeparada");
		       latparada = extras.getString("latparada"); 
		       lonparada = extras.getString("lonparada");
		     
		       
		       
		       
		       cd = new ConnectionDetector(getApplicationContext());
				
		       if (!cd.isConnectingToInternet()) {
		           AlertDialog.Builder builder = new AlertDialog.Builder(this);
		           builder.setTitle(this.getResources().getString(R.string.interneterrorpede))
		               .setMessage(this.getResources().getString(R.string.interneterror))
		               .setCancelable(false)
		               .setIcon(R.drawable.ic_launcher)
		               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   MapaParadaActivity.this.finish();
		                   }
		               });
		             builder.create().show();  
		             finish();
		           return;
		       }    
		       
		       
		       
		       
		       
		       
		       
		       
		       
	        
	        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	        
	        googleMap = mapFragment.getMap();
	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        googleMap.setTrafficEnabled(true);


	        
	        
	        
	        googleMap.setOnInfoWindowClickListener(
	        		  new OnInfoWindowClickListener(){
	        		    public void onInfoWindowClick(Marker marker){
	        		    	EventInfo eventInfo = ListaObj.get(marker);
	        		    	String nomeparadaw = eventInfo.getPonto();
	        		    	String parada = eventInfo.getParadacod();
	        		    	String sentido = Integer.toString(eventInfo.getSentido());
	        		    	String carrow =  eventInfo.getVeiculo();
	        		    	
	        		    	
	        		    	Intent nextScreen = new Intent(MapaParadaActivity.this, MostraLinhaActivity.class);
	        		        nextScreen.putExtra("parada", "" + paradaclick);
	         		        nextScreen.putExtra("linha", "" + linhaclick);
	         		        nextScreen.putExtra("carro", "" + carrow );
	         		        nextScreen.putExtra("nome", "" + nomeparadaclick);
	         		        nextScreen.putExtra("latparada", "" + latparada );
	         		        nextScreen.putExtra("lonparada", "" + lonparada );
	        		        startActivityForResult(nextScreen, 0);
	        		        
	        		        
	        		        
	        		    }
	        		  }
	        		);

		    
	        
	        
	   
	        
	        
	        
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

	             int sentido = 1;//eventInfo.getSentido();
	             int cor = 0;
	             if (sentido == 1)
	         	{
	         		cor = getResources().getColor(R.color.onibus1);
	         	}  else
	         	{
	         		cor = getResources().getColor(R.color.onibus2);
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
	            	   txtTitulo.setTextColor(cor);
	            	   txtPonto.setTextColor(cor);
	            	   
	            	   
	            	   
	            	    NumberFormat nf = NumberFormat.getNumberInstance();
	                	ratingCntr.removeAllViewsInLayout();
	                	
	                	ImageView iv = new ImageView(getBaseContext());
	                	TextView  tv = new TextView(getBaseContext());
	                	//ImageView cad = new ImageView(getBaseContext());
	                	
	                	
	                	iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_eu));
	                	//cad.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_cadeira));
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
	           	    String cadeira = null;
	           	    String carro = null;
	           	    
	           	    cadeira = eventInfo.getCadeirante();
	           	    
	           	    String[] items = ponto.split("@");
	           	   
	                      nome =  items[0].trim();
	                      ende = items[1].trim();
	            	   
	            	   
	               
	               TextView txtLetreiro = ((TextView) contents.findViewById(R.id.txtTitulo));
	               TextView txtCarro = ((TextView) contents.findViewById(R.id.txtCarro));
	               TextView txtPonto = ((TextView) contents.findViewById(R.id.txtPonto));
	               TextView txtPrevi = ((TextView) contents.findViewById(R.id.txtPrevisao));
	               LinearLayout ratingCntr = ((LinearLayout) contents.findViewById(R.id.ratingCntr));
	            	  
	               
	               txtCarro.setText("Carro: "+eventInfo.getVeiculo());//+"  "+eventInfo.getLocalizacao());
	               carro = eventInfo.getVeiculo();
	               txtPonto.setText("Parada: "+nome);
	              
	               
	               txtLetreiro.setText(eventInfo.getLetreiro());
	               
	               
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
	   	           
	   	        String previ =    eventInfo.getPrevisao();
	   	        String previok = previ.replace(":", "h");
	               
	   	        txtPrevi.setText("Previsão: "+previok+"   Espera +-: "+tempo);
	            NumberFormat nf = NumberFormat.getNumberInstance();
               	ratingCntr.removeAllViewsInLayout();
               	
               	ImageView iv = new ImageView(getBaseContext());
               	TextView  tv = new TextView(getBaseContext());
               	//ImageView cad = new ImageView(getBaseContext());
               	
              
               	double dista = eventInfo.getDistponto();
               	double distaok = 0;
               	
               	
               		
               	distaok = dista / 1000;
               		
               	
               	
              
               		iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto1));
               	
               	
               	
               	tv.setText(" Distância: "+nf.format(distaok)+" Km       ");
               	tv.setTextSize(12);                	
               	tv.setTextColor(cor);
               		
               	
               	
               	
               	ratingCntr.addView(iv);	
               	ratingCntr.addView(tv);
                
               	if (cadeira.equals("SIM")) {
               		
               	
            	//cad.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_cadeira));
               	//ratingCntr.addView(cad);	
               	}
	               
	               
	               
	                return contents;

	        }
	                
	                
	               }

	        });

	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
			
	        listDataHeader = new ArrayList<String>();
	  	    listDataChild = new HashMap<String, List<String>>();
	        
	  	    
	  	  iniciaMapa();
	  	    
	        
	  	  PegaParada(paradaclick);	
		    
		    
		    
		    
		    
			}	    
		


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
		        googleMap.addMarker(new MarkerOptions()
		        .position(latLng)
		        .title("Você")
		        .snippet("Aqui")
		        .icon(BitmapDescriptorFactory.fromResource(R.drawable.eu)));
		        
		        
		        
		        googleMap.getUiSettings().setCompassEnabled(true);
		        googleMap.getUiSettings().setZoomControlsEnabled(true);
		        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		        //googleMap.animateCamera(cameraUpdate);
		        
		     // Move the camera instantly to Sydney with a zoom of 15.
		        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

		        // Zoom in, animating the camera.
		        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

		        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
		        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
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
		   
		   if (ob.getSentido() == 1)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));
		   if (ob.getSentido() == 2)
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));
		   
		   
		   Marker marker = googleMap.addMarker(markerOptions);   
		   ListaObj.put(marker, ob);
		   
		   
			   
	   }
	   
	   
	   
	   public void onInfoWindowClick(Marker marker) {
		    Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
		  }

	   
	   
	   
	   
	   public void montamapaonibus_vai(EventInfo oni)
	   {
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistyou());	   
		   String carro = oni.getVeiculo();
		   boolean mostrar = false;
		   
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   
		   if (carroclick.indexOf(carro) >= 0)
		   {
			   
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busidacs));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busidas));
			   }
			   
			   
			   mostrar = true;
			   
		   } else
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
		   
		   Marker marker = googleMap.addMarker(markerOptions);   

		   ListaObj.put(marker, oni);
		
		   if (mostrar == true) {
			   marker.showInfoWindow();
			   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
		   }
	  
	   }
	   
	   
	   public void montamapaonibus_vem(EventInfo oni)
	   {
		   LatLng latLng = null;
		   latLng = new LatLng(oni.getaLat() , oni.getaLon());	   
		   String dista = Double.toString(oni.getDistyou());	
		   String carro = oni.getVeiculo();
		   MarkerOptions markerOptions = new MarkerOptions();
		   markerOptions.position(latLng);
		   markerOptions.title(oni.getPonto());
		   markerOptions.snippet(dista);
		   if (carroclick.indexOf(carro) >= 0)
		   {
			   if (oni.getCadeirante().equals("SIM"))
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvoltacs));
			   }
			   else
			   {
			   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.b_busvoltas));
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
		   ListaObj.put(marker, oni);
		   
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
				
				 paradacodw = parada;	
					
				 String finalURLStr = "http://api.olhovivo.sptrans.com.br/v0/Previsao/Parada?codigoParada="+parada;       // API Call
				 String url = finalURLStr.toString();
				 onibustask = new PegaOnibusTask(this);
				 onibustask.execute(url,latx,lonx);
				 
				 
				 
				}
				private class PegaOnibusTask extends AsyncTask<String, Integer, String> {
				     
					   public PegaOnibusTask(MapaParadaActivity mapaParadaActivity) {
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
					  		   
					  		    
					  		    int veiculos = 0;
					  		   
					    	   
					    	   
					    	if(sJson == null) {
					            return;
					        }        
					         
					        
					    	String four = null;
					    
					    	try
					        {   
					        four = sJson.toString();
					  		  } catch (Exception e) {
					              System.err.println("erro1");
					              return;
					        } 
					        
					      	
					    	try
					    	{			    	
					    	jtudo = new JSONObject(four).getJSONObject("PrevisaoResult");
					  		  } catch (JSONException e) {
					              System.err.println("erro2");
					              return;
					        } 
					    	
					    	
					    	
					    	
					    	try
					    	{			    	
					    	jsonF = jtudo.getJSONObject("p");
					    	//paradanome = jsonF.getString("np");
					    	codparada = jsonF.getString("cp");
					    	
					    	
					  		  } catch (JSONException e) {
					              System.err.println("erro3");
					              return;
					        } 
					        
					    	
					    	
					    	
					    
					    	
					    	
					    	
					    
					    	try
					    	{			    	
					    	String hora =  jtudo.getString("hr");
					  		  } catch (JSONException e) {
					              System.err.println("erro4");
					              return;
					        } 
					    	
					    	
					    	try
					    	{			    	
					    	resultArray = jsonF.getJSONArray("l");
					    	  } catch (JSONException e) {
					              //System.err.println("erro");
					              return;
					        } 
					    	
					    	
					    	 
					    	  
					          if (resultArray.length() != 0) 
						      {
					        	  
					        	  ArrayList<String> linhasw = new ArrayList<String>();
					        	  
					        	  
						    	  for(int j=0; j< resultArray.length(); j++) 
						    	  {
						  			 try
						  			 {
						  		     
						  			 det = resultArray.getJSONObject(j);			 
						  		     linha    = det.getString("c");
						  		     sentido1 = det.getString("lt0");
						  		     sentido2 = det.getString("lt1");
						  		     veiculos = det.getInt("qv");
						  		     sentido  = det.getInt("sl");
						  		    
						  		    
						  		    
						  			} 
						  			  catch (Exception ex) 
						  			  {
						  				  
						  			  } 
						  			
						  			 
						  			 
						  			 
						  			 try
						  			 { 
						  			 
						  		    
						  			busArray = det.getJSONArray("vs");
						  			
						  			
								  		for(int k=0; k< busArray.length(); k++)
								  		{
						  				
						  				  cada = busArray.getJSONObject(k);
						  				  boolean defi = cada.getBoolean("a");
						  				  String cadeira = null;
						  				  if (defi==true) {
						  					cadeira = "SIM";  
						  				  }
						  				  else
						  				  {
						  					cadeira = "NÃO";  
						  				  }
						  				  String carro = cada.getString("p");
						  				  String tempo = cada.getString("t");  
						  				  
						  				  
						  				EventInfo appM1 = new EventInfo();  
						  				
						  				
						  				
						  				boolean achou = false;
						  				
						  				for(int h=0; h < listanomapa.size() ; h++) 
								    	  {
										
						  					EventInfo objeven = listanomapa.get(h);
						  					
						  					
											int tipo = objeven.getTipo();
											int para = objeven.getParada();
											
											
											if ((tipo == 1) && (para == 0)) 
											{
												
												String chave = objeven.getId();
												if (chave.equals(linha+"_"+carro)) 
												{
													achou = true;
												}
											}
									
											if ((tipo == 1) && (para == 1)) 
											{
												String chave = objeven.getId();
												if (chave.equals(codparada)) 
												{
													objeven.setSentido(sentido);
												}
												
											}
											
											
											
												
										 }
											
								    	  
						  				
						  			    if (achou == false)
						  			    {
						  			
						  			    	
						  			    	 double lath =  cada.getDouble("py");
						  			    	 double lonh =  cada.getDouble("px");
						  			    	 
						  			    	
						  			    	
						  			    	 Location locationA = new Location("point A");
						  		             locationA.setLatitude(Double.parseDouble(latparada));
						  		             locationA.setLongitude(Double.parseDouble(lonparada));
						  		             Location locationB = new Location("point B");
						  		             locationB.setLatitude(cada.getDouble("py"));
						  		             locationB.setLongitude(cada.getDouble("px"));
						  		             float distance = locationA.distanceTo(locationB);     
						  		           
						  		             
						  		              NumberFormat nf = NumberFormat.getNumberInstance();
									          
									          int distakm  = (int) Math.round(distance); 
									          String poedist = null;
									          
									          if (distakm >= 1000) 
									          {
									        	  
									        	  double distakmd = (double) distakm/1000;
									        	  poedist = nf.format(distakmd)+" Km  ";
									        	  
									          }  else
									        	  
									          {
									        	  poedist = nf.format(distakm)+" m  ";
									          }
									        	  
									          
									          
									   
									    		
									    		
									    
									    
									    
									    
									    
									    
									    
									          
									    	
						  				appM1.setId(linha+"_"+carro);
						  				appM1.setTipo(1);
						  				appM1.setVeiculo(carro);
						  				appM1.setPonto(nomeparadaclick);
						  				appM1.setPrevisao(tempo);
						  				appM1.setCadeirante(cadeira);
						  				appM1.setDistponto(distance);
						  				appM1.setSentido(sentido);
						  				appM1.setParadacod(codparada);
						  				paradacodw = codparada;
						  				
						  				//appM1.setLinha("Carro: "+carro+" P: "+tempo+" PNE: "+cadeira+" Dist: "+poedist);
						  				appM1.setaLat(cada.getDouble("py"));
						  				appM1.setaLon(cada.getDouble("px"));
						  				
						  				
						  				LatLng latLng = new LatLng(cada.getDouble("py") , cada.getDouble("px"));
						  				appM1.setLatLong(latLng);
						  				
						  				String letreiros = null;
						  				
						  				
						  				
						  				   /*
						  				   Geocoder geocoder;
							               List<Address> addresses;
							               geocoder = new Geocoder(MapaParadaActivity.this, Locale.getDefault());
							               addresses = geocoder.getFromLocation(cada.getDouble("py"), cada.getDouble("px"), 1);

							               String address = addresses.get(0).getAddressLine(0);
							               String city = addresses.get(0).getAddressLine(1);
							               String country = addresses.get(0).getAddressLine(2);			  				
						  				
						  				   appM1.setLocalizacao(address);
						  				   */
						  				
						  			
						  				
						  				
						  				if (linha.indexOf(linhaclick) >= 0)
						  				{  
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
						  				
						  				
						  				
						  				
						  				
						  				}  
						  				  
						  			    }
						  				
						  			    
						  			    
						  			    
						  				
						  		}
								    	  
						  			
						  		
						  		
										
						  		 
						  		
											
											
						  		
							
						  		
						  		
						  		
						  			 }
						  			 catch (Exception ex) 
						  			  {
						  				  
						  			  } 
						  		 }
						    	  
						    	  EventInfo appM2 = new EventInfo();  
						            LatLng latLng = new LatLng(Double.parseDouble(latparada) , Double.parseDouble(lonparada));					  		 	
						  		 	appM2.setaLat(Double.parseDouble(latparada));
						  	        appM2.setaLon(Double.parseDouble(lonparada));
						  	        appM2.setSentido(sentido);
						  	        appM2.setId(paradacodw);
						  	        appM2.setPonto(nomeparadaclick);
						  	        appM2.setLatLong(latLng);        
						  	        appM2.setParadacod(paradacodw);
						  	        //appM2.setLinhas(linhasw);
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
				  		            listanomapa.add(appM2);
				  		            montamapaponto(appM2); 
									
						    	 
						    		 
					  
							 }
						      
				   
					     }
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
				
		
				
				
				
				
				
				
	}

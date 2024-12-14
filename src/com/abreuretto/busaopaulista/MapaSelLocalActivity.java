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


import com.abreuretto.busaopaulista.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
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

public class MapaSelLocalActivity extends FragmentActivity  implements PegaDadosGeocodeListener 
{

	   
	
	  ConnectionDetector cd;
	  private ProgressDialog dialog;
	  AlertDialogManager alert = new AlertDialogManager();
	  GoogleMap googleMap;
	  Button novo;
	 
	  PegaDadosGeocode task = null;
	  
	    String latx = null;
	    String lonx = null;
	    String latxflip = null;
	    String lonxflip = null;
	    String msg = null;
	    String letreiro1linha = null;
	    String letreiro2linha = null;
	    String sentidoparada = null;    
	    boolean escolhavalida = false;
	    
	    String paradacodw = null;
	    int conta;
		int registros = 0;
		int contaparada = 0;
		
		 double lathx = 0; 
	  	 double lonhy =  0;
	  	 
	     String paradacod = null;
	 	 boolean semonibus_w = false;
	 	 boolean selecionado_w = false;
		 
	 	 
	  	 
	  	 
	  	 String paradanome = null;
		 String codparada = null;
		 int sentido  = 0;
		 boolean flip = true;
		boolean terminou = false;
		 
		 String distanciaw = "500";
		 boolean pontolinha = false;
	    
	    ArrayList<EventInfo> listanomapa = new ArrayList<EventInfo>();
	    ArrayList<EventInfo> listaponto = new ArrayList<EventInfo>();
	    ArrayList<EventInfo> listaponto2 = new ArrayList<EventInfo>(); 
	    
	    private HashMap<Marker, EventInfo> eventMarkerMap;
	    private Handler handler = new Handler();

	    
	
		// Constants
		public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
		public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
		public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
	    
		
		HashMap <Marker, EventInfo> ListaObj = new HashMap <Marker, EventInfo>();
		
		
		  List<String> listDataHeader;
		  HashMap<String, List<String>> listDataChild;
		  
		 
		  private static final int RESULT_SETTINGS = 1; 
		  HelperSharedPreferences appPrefs = null;
		  
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		
		
		
		
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_mapa_sel_local);	
		    
		
			   	        
			//dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));  
		        
			
			//showUserSettings();
			
			
			 cd             = new ConnectionDetector(getApplicationContext());
			 appPrefs       = new HelperSharedPreferences(getApplicationContext());
	
			
			 
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
			                	   MapaSelLocalActivity.this.finish();
			                	   return;
			                   }
			               });
			             builder.create().show();
			             return;
			 	   
			   }	
			 
			 
	        PegaPreferences();
			
			
		   
	        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	        googleMap = mapFragment.getMap();
	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        googleMap.setTrafficEnabled(true);
	        googleMap.setMyLocationEnabled(true);
	         
	        googleMap.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() 
	        {
	            public boolean onMyLocationButtonClick() 
	            {
	            	if (googleMap != null) {
	            		googleMap.stopAnimation();
	                    Location myloc = googleMap.getMyLocation();
	                    
	                   
	                    
	                    
	                    if (myloc != null) {
	                    	googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(myloc.getLatitude(), myloc.getLongitude())));
	                        iniciaMapa(Double.toString(myloc.getLatitude()),Double.toString(myloc.getLongitude()));
	                    }
	                    
	                }

	            	
	                
	                return false;
	                
	                
	                
	            }
	        });

	      
	        googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
				
	        	String url = null;
	        	
	        	
				@Override
				public void onMapLongClick(LatLng point) {
					
										
			
				    escolhavalida = false;
				    iniciaMapa(Double.toString(point.latitude),  Double.toString(point.longitude)	);
					
				    
				    
				    
				   

				    
				    
				    
				    
				    
				    
				    
				    
				    
				    
				    
				}
			});
	   
	        
	        
	        
	    	  Iniciar();
	  	    
	  	   
			    
			}	  
		
		
		
		
		 public void Iniciar()
		   {
			   
		      
		       
		       iniciaMapaPrima();
		  	    

			   
		   }
		
			 public void PegaPreferences()
			   {
				   latx      =     appPrefs.getString("lat", "");
				   lonx      =     appPrefs.getString("lon", "");
				   latxflip  =     appPrefs.getString("latflip", "");
				   lonxflip  =     appPrefs.getString("lonflip", "");	   
				   paradacod =     appPrefs.getString("paradacod", "");
				   selecionado_w = appPrefs.getBoolean("selecionado_w", false);
				   semonibus_w =   appPrefs.getBoolean("semonibus_w", false);
				   
				   if (!latxflip.equals("")) {
					   
					   latx = latxflip;
					   lonx = lonxflip;
					   
				   }
				   
				   
			   }
			
			
		
		
		
		
		
		

		 public void onPegaDadosGeocodeComplete(final String ende) {
			 if(dialog != null)  dialog.dismiss();
		       
			 if (ende.indexOf("SP, RMSP") >= 0)
				 if (ende.indexOf("SP, RMSP") >= 0)
					 if (ende.indexOf("SP, RMSP") >= 0)
						 if (ende.indexOf("SP, RMSP") >= 0)
							 	{
		    	   
		    	    
		           
				    AlertDialog.Builder builder = new AlertDialog.Builder(MapaSelLocalActivity.this);
			        builder.setTitle("Novo local selecionado")
			            .setMessage("Confirma a escolha do novo local?")
			            .setCancelable(false)
			            .setIcon(R.drawable.ic_launcher)
			            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			               
			                latx = 	latxflip;
			                lonx =  lonxflip;
			                	
			                appPrefs.setString("listapontos", "OK");	
			                appPrefs.setString("lat", latx);
			                appPrefs.setString("lon", lonx);
			                appPrefs.setString("latflip", latx);
				    	    appPrefs.setString("lonflip", lonx);
				    	    appPrefs.setString("endereco", ende);
				    	    
				            escolhavalida = true; 
			                
			                finish();
			                	
			                }
			            })
			            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                    dialog.cancel();
			                }
			            
			            
			            });
			 
			        builder.create().show();        
			        
			         	    
				    
		    	 }
				  else
				  {
					escolhavalida = false;
					Toast.makeText(MapaSelLocalActivity.this, "Local selecionado fica fora da cidade de São Paulo!", Toast.LENGTH_LONG).show(); 
					iniciaMapaPrima();
					
					
					
	           	  }
		 
		      // task.cancel(true);
		   }   
		 
		 
		   
		   
		   
		   
		   public void onPegaDadosGeocodeFailure(String msg) {
		       if(dialog != null)  dialog.dismiss();
		   }   
		

		
		
		
		
		
		public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
		    {
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
			         
			            
			        case R.id.action_settings:
	                   pegaConfi(); 
			            break;
			 
						            
			        default:
			            return super.onOptionsItemSelected(item);
			    }
				return true;
			}
		    
		    
		    
			
			public void pegaAbout()
			{
		        Intent intent = new Intent(this, SobreActivity.class);
		        startActivity(intent);
			}	
			
			public void pegaConfi()
			{	
			Intent i = new Intent(this, UserSettingActivity.class);
	        startActivityForResult(i, RESULT_SETTINGS);				
			}

		
			
			
			public void iniciaMapaPrima()
			{
				
				googleMap.clear();
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
			        Toast.makeText(MapaSelLocalActivity.this, "Escolha uma nova localização clicando no mapa!", Toast.LENGTH_LONG).show(); 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
		public void iniciaMapa(String lat, String lon )
		{
			
			googleMap.clear();
			 LatLng latLng = null;
			 latLng = new LatLng(Double.parseDouble(lat) , Double.parseDouble(lon));
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
		        
		        latxflip = lat;
		        lonxflip = lon;
		        
		        
		        String 	url =  "http://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lon+"&zoom=18&addressdetails=1";
              	task =	new PegaDadosGeocode(MapaSelLocalActivity.this);
                task.execute(url);
		        
		        
		       try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		        
		        
		        
		        
		        
		        
		        
		        
		        
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
			        
			        
			        appPrefs.setBoolean("parar", true);
			        
			        
				   
			        finish();

			    }
				
				
				
			  
			  			
				
				
				
	}

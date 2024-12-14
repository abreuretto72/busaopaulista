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



import com.abreuretto.buddysearchnearby.BuddySearchNearBy;
import com.abreuretto.buddysearchnearby.Datum;
import com.abreuretto.buddysearchnearby.PegaDadosSearchByListener;
import com.abreuretto.buddysearchnearby.PesqBuddySearchBy;
import com.abreuretto.busaopaulista.R;

import com.abreuretto.googleplaces.GooglePlaces;
import com.abreuretto.googleplaces.ListaGoogle;
import com.abreuretto.googleplaces.PegaBusStation;
import com.abreuretto.googleplaces.PegaBusStationListener;
import com.abreuretto.googleplaces.Result;
import com.abreuretto.pesqparada.PesqParada;
import com.abreuretto.pesqparada.PesqParadaSPTransListener;
import com.abreuretto.pesqparada.PesqParadaSpTrans;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
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

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class MapaTrajetoLinha extends FragmentActivity  implements   PesqParadaSPTransListener, PegaDadosSearchByListener
{

   
	
  ConnectionDetector cd;
  private ProgressDialog dialog;
  AlertDialogManager alert = new AlertDialogManager();
  GoogleMap googleMap;


  
  
	ArrayList<ListaGoogle> listagoogle = new ArrayList<ListaGoogle>();
	ArrayList<ListaGoogle> listabuddy  = new ArrayList<ListaGoogle>();
	
	
	
	
    String latx = null;
    String lonx = null;
    String latxflip = null;
    String lonxflip = null;
    String msg = null;
    String letreiro1linha = null;
    String letreiro2linha = null;
    String sentidoparada = null; 
    String endereco = null;  
    String linha = null;
    
    String paradacodw = null;
    int conta;
	int registros = 0;
	int contaparada = 0;
	
	 double lathx = 0; 
  	 double lonhy =  0;
  	 
     String paradacod = null;
 	 boolean semonibus_w = false;
 	 boolean selecionado_w = false;
 	 boolean temponto = false;
 	 
  	 
  	 
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
    

    private Handler handler = new Handler();

    
   // PegaPontoTask     pontotask;
    //PegaPontoParada   pontoparada;
	// Constants
	public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
	public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
	public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
    
	
	HashMap <Marker, EventInfo> ListaObj = new HashMap <Marker, EventInfo>();
	

	  
	 
	  private static final int RESULT_SETTINGS = 1; 
	  HelperSharedPreferences appPrefs = null;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	
	
	
	
	{
		super.onCreate(savedInstanceState);
		
		
		
		
	
		

		
		
		
		
		
		
		
		dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));  
		
		setContentView(R.layout.activity_mapa_trajeto_linha);	
	    
	
		//Bundle extras = getIntent().getExtras(); 
	    
	    //   paradanome = extras.getString("nome");
	    //   linha = extras.getString("linha");
	    

                
		
	        
		
		
		 cd             = new ConnectionDetector(getApplicationContext());
		 appPrefs       = new HelperSharedPreferences(getApplicationContext());
      

		
		 
	
		temponto = false; 
		 
		
		
		
		
		
		
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
	                	   MapaTrajetoLinha.this.finish();
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
        googleMap.setMyLocationEnabled(false);
         
        
        googleMap.setOnInfoWindowClickListener(
                        new OnInfoWindowClickListener(){
                            public void onInfoWindowClick(Marker marker) {
                            	EventInfo eventInfo = ListaObj.get(marker);
		        		    	String cod = eventInfo.getPonto();
		        		    	
		        		    	
		        		    	if (!cod.equals("Você"))
		        		    	{
		        		    	
		        		    	
		        		    	String parada = eventInfo.getParadacod();
		        		    	String sentido = Integer.toString(eventInfo.getSentido());
		        		    	String latparada = Double.toString(eventInfo.getaLat());
		        		    	String lonparada = Double.toString(eventInfo.getaLon());
		        		    	Intent nextScreen = new Intent(MapaTrajetoLinha.this, MostraLinhaActivity.class);
		        		        nextScreen.putExtra("parada", "" + parada);
		        		        nextScreen.putExtra("nome", "" + cod);
		        		        nextScreen.putExtra("sentido", "" + sentido );
		        		        nextScreen.putExtra("latparada", "" + latparada );
		        		        nextScreen.putExtra("lonparada", "" + lonparada );
		        		        startActivityForResult(nextScreen, 0);
		        		        appPrefs.setString("paradacod", parada);
		        		        appPrefs.setBoolean("selecionado_w", true);
		        		        appPrefs.setBoolean("semonibus_w", false);
		        		        Gson lista = new Gson();
		        		        String listagem = lista.toJson(listaponto.toArray()).toString();
		        		        appPrefs.setString("listapontos", listagem);
		        		    	}
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
 
            @Override
            public View getInfoContents(Marker marker) {

             EventInfo eventInfo = ListaObj.get(marker);

            
             
             
             
             int sentido = 1;
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
            	    String nome = eventInfo.getPonto();
            	    String endex = eventInfo.getLocalizacao();
            	    String ende = endex.replace("Null", "");
            	    
            		
        	    
            	    
            	    
            	   
            	    	
            	  	if (eventInfo.getTipo() < 3)
            	    	{
            	    	
            	  		   if (eventInfo.getTipo() == 1)           	  		
            	    		nome = "Estação Metrô: "+nome;
            	    		
            	    		if (eventInfo.getTipo() == 2)
                	    		nome = "Estação CPTM: "+nome;
            	    			
            	    	
	  				   Geocoder geocoder;
		               List<Address> addresses;
		               geocoder = new Geocoder(MapaTrajetoLinha.this, Locale.getDefault());
		               
		               try
		               {
		               addresses = geocoder.getFromLocation(eventInfo.getaLat(), eventInfo.getaLon(), 1);
		               ende = addresses.get(0).getAddressLine(0);
		               
		               
		               /*
		               if (eventInfo.getSentidolinha() == 1)
		               {
		               nome = letreiro1linha+" | "+letreiro2linha;
		               } else
		               {
		               nome = letreiro2linha+" | "+letreiro1linha;
		               }
		               */
		               
		               eventInfo.setPonto(ende+" @ "+ende);
		               atualiza(eventInfo);
		               ListaObj.remove(marker);
		               ListaObj.put(marker, eventInfo);
		               }
		               catch (Exception e)
		               {
		               }
            	    	
	        	    }
            	    else
            	    {
	        	  //  String[] items = ponto.split("@");
                   //    nome =  items[0].trim();
                   //    ende = items[1].trim();
            	    }
            	    
            	    
            	    
            	    TextView txtTitulo = ((TextView) contentsponto.findViewById(R.id.txtTitulo));
            	    TextView txtPonto = ((TextView) contentsponto.findViewById(R.id.txtPonto));
            	    LinearLayout ratingCntr = ((LinearLayout) contentsponto.findViewById(R.id.ratingCntr));
            	    ImageView ima = ((ImageView) contentsponto.findViewById(R.id.appIcon)); 	   
            	   
            	   
            	    txtTitulo.setTextColor(cor);
            	    txtPonto.setTextColor(getResources().getColor(R.color.textoende));
            	    NumberFormat nf = NumberFormat.getNumberInstance();
                	ratingCntr.removeAllViewsInLayout();
                	ImageView iv = new ImageView(getBaseContext());
                	TextView  tv = new TextView(getBaseContext());
                	ImageView cad = new ImageView(getBaseContext());
                	
                	if (eventInfo.getTipo() == 3)
                	{
                	ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada));
                	}
                	if (eventInfo.getTipo() == 1)
                	{
                	ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto_metro));
                	}
                	if (eventInfo.getTipo() == 2)
                	{
                	ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto_trem));
                	}
                	
                	
                	if (eventInfo.getTipo() == 4)
                	{
                	ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto_taxi));
                	}
                	if (eventInfo.getTipo() == 5)
                	{
                	ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ponto_aviao));
                	}
                	
                	
                	
                	
                	
                	
                	
                	iv.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_eu));
                	cad.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.parada_cadeira));
                	tv.setText(" Distância: "+nf.format(eventInfo.getDistyou())+"m       ");
                	tv.setTextSize(12);                	
                	tv.setTextColor(cor);
                	ratingCntr.addView(iv);	
                	ratingCntr.addView(tv);
                   String valor1 = nome.replace("[0] ", "");
                   String valorok = valor1.replace("[1] ", "");
                   txtTitulo.setText(valorok);
                   txtPonto.setText(ende);
            	   return contentsponto;
            	   
               }
                
               else
            	   
            {
            	   
              
           	   TextView txtLetreiro = ((TextView) contents.findViewById(R.id.txtTitulo));
               TextView txtCarro = ((TextView) contents.findViewById(R.id.txtCarro));
               TextView txtPonto = ((TextView) contents.findViewById(R.id.txtPonto));
               TextView txtPrevi = ((TextView) contents.findViewById(R.id.txtPrevisao));
               ImageView ima = ((ImageView) contents.findViewById(R.id.appIcon));
               
               ima.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.eu));
               
               txtCarro.setText("");
               txtPonto.setText("Aqui");
               txtPrevi.setText("");
               txtLetreiro.setText("Você");
               
                return contents;

        }
                
                
               }

        });

        
    	  Iniciar();
  	    
  	   
		    
		}	  
	
	
	
	
	 public void Iniciar()
	   {
		   
	     
	       
	      /* 
	 	  if (latx.equals("-23.550268") && lonx.equals("-46.634325"))
	  	    {	    		
	  	    	if (latx.equals(""))
	  	    	{
	  	    		Bposicao(null);
	  	    		return;
	  	    	}
	    		  
	  	    	latx = latxflip;
	  	    	lonx = lonxflip;
	  	    }
	       
	       */
	       
	       
	       
	 	  if (temponto == true)
	 	  {
	 		 iniciaMapa();
	 		 MontaParada2();	 		
	 		 return;
	 	  } 
	 	  
	 	  
	 	  
	       //iniciaMapa();
	  	   //PegaPonto();
	       //MontaParada2();
		   
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
			   linha       =   appPrefs.getString("linha","");
			   
			   String value = appPrefs.getString("listaparadas", "OK");
			   
			   if (!value.equals("OK"))
			   {
			   
			   GsonBuilder gsonb = new GsonBuilder();
			   Gson gson = gsonb.create();
			   EventInfo[] listaP = gson.fromJson(value, EventInfo[].class);
			   listaponto.clear();
			  
			   
			   for(int h=0; h < listaP.length ; h++) 
		    	  {
				   listaponto.add(listaP[h]);
				   temponto = true;
		    	  }
        	   
			   
			   }
			   
		   }
		

		 
		 
		 
		 
		 @SuppressLint("DefaultLocale")
		public void VerificaListas()
		 {
			 
			 listaponto.clear();
			 
			   
					 for(int i=0; i< listabuddy.size(); i++) 
		     	      {
						
						    
						 ListaGoogle nobuddy = new ListaGoogle();
						 nobuddy = listabuddy.get(i);
						 
						 //String monta = nogoogle.getNomeparada().toString().toLowerCase(Locale.getDefault());
						 //String googleNome = monta.replace("/", "");
						 String buddyNome  = nobuddy.getNomeparada().toString().toLowerCase(Locale.getDefault());
						 
						 
							 
							 
					    	  String id = nobuddy.getCodigoparada().toString();
					  		  Double latm = Double.parseDouble(nobuddy.getLat());
					  		  Double lonm = Double.parseDouble(nobuddy.getLon());
					  		  EventInfo appM = new EventInfo();
			                  appM.setSentidolinha(1);
				              appM.setSeqparada(0);
					          LatLng latLng = new LatLng(latm , lonm);					  		 	
					  		  appM.setaLat(latm);
					  	      appM.setaLon(lonm);
					  	      appM.setId(id);
					  	      appM.setPonto(nobuddy.getNomeparada().toString());
					  	      appM.setLocalizacao(nobuddy.getEndereco().toString());
					  	      appM.setLatLong(latLng);        
					  	      appM.setParadacod(id);
					  	      appM.setSentido(1);
					  	      appM.setSentidolinha(1);
					  	      appM.setParada(1);
					  	      Location locationA = new Location("point A");
					          locationA.setLatitude(Double.parseDouble(latx));
					          locationA.setLongitude(Double.parseDouble(lonx));
					          Location locationB = new Location("point B");
					          locationB.setLatitude(latm);
					          locationB.setLongitude(lonm);
					          float distance = locationA.distanceTo(locationB);   
					          appM.setDistyou(distance);	
					          int tipox = Integer.parseInt(nobuddy.getTipo());
					          appM.setTipo(tipox);
					  	      listaponto.add(appM);	
					  	      montamapaponto(appM);
					  	    
			 			 
					 
    	      }
			 
			 if(dialog != null)  dialog.dismiss();
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 public void onPegaDadosSearchByComplete(List<BuddySearchNearBy> paradabuddy)
		 {
			
			 //String url1 = "https://maps.googleapis.com/maps/api/place/search/json?location="+latx+","+lonx+"&radius=500&types=bus_station&sensor=false&key=AIzaSyBRhULv84uFwIW0JYyGonkTM5sr5dvOR9I";
			 //PegaBusStation taskstation = new PegaBusStation(this);
			 //taskstation.execute(url1,latx,lonx );
			
			 
			 if (paradabuddy.equals(null))
        	 {
        		 
				  if(dialog != null)  dialog.dismiss();
        		 return;
        		 
        	 }
			 
			 
			 
			 
        	 for(int j=0; j< paradabuddy.size(); j++) 
       	      {
        		 BuddySearchNearBy paradas =  paradabuddy.get(j);
        		 
        		 List<Datum> listano =   paradas.getData();
        		
        		 for(int i=0; i< listano.size(); i++) 
          	      {
        			 
        			 Datum no = listano.get(i);
        			 
        			ListaGoogle buddy = new ListaGoogle();
        			buddy.setDistancia(Double.parseDouble(no.getDistanceInMeters()));
        			buddy.setLat(no.getMetaLatitude());
        			buddy.setLon(no.getMetaLongitude());
        			String monta = no.getMetaKey().toString();
        			String cod = monta.replace("STOP_", "");
        			buddy.setCodigoparada(cod);      			
        			
        			String monta2 = no.getMetaValue().toString();
        			
        			if (monta2.indexOf("[1]") >= 0) buddy.setTipo("1");
        			if (monta2.indexOf("[2]") >= 0) buddy.setTipo("2");
        			if (monta2.indexOf("[3]") >= 0) buddy.setTipo("3");
        			
        			String monta3 = monta2.replace("[1]", "");
        			String monta4 = monta3.replace("[2]","");
        			String monta5 = monta4.replace("[3]","");
        			
  	        	    String[] items = monta5.split("@");
                    String nome =  items[0].trim();
                    String ende = items[1].trim();
        		    buddy.setNomeparada(nome);
        		    buddy.setEndereco(ende);
        		   	listabuddy.add(buddy); 
        			 
        					 
          	      }
        		 
        		 
        		 
       	      }
			 
			 
        	 if(dialog != null)  dialog.dismiss();
        	 VerificaListas();
			 
			 
		 }
		 
		 public void onPegaDadosSearchByFailure(String msg) 
		 
		 {
		       if(dialog != null)  dialog.dismiss();
		       
		       
		   }  
		 
		 
		 
		 
		 
		 
		 
		 
	
	
		 public void onPesqParadaSPTransComplete(List<PesqParada> paradasp) 
		 {
		
			 if (paradasp.equals(null))
        	 {
				  if(dialog != null)  dialog.dismiss();
        		 return;
        		 
        	 }
			 
			 
		 	 
        	 for(int j=0; j< paradasp.size(); j++) 
       	      {
        	
                  PesqParada paradas =  paradasp.get(j); 		
        		  String id = paradas.getCodigoParada().toString();
		  		  Double latm = paradas.getLatitude();
		  		  Double lonm = paradas.getLongitude();
		  		  EventInfo appM = new EventInfo();
                  appM.setSentidolinha(1);
	              appM.setSeqparada(0);
		          LatLng latLng = new LatLng(latm , lonm);					  		 	
		  		  appM.setaLat(latm);
		  	      appM.setaLon(lonm);
		  	      appM.setId(id);
		  	      appM.setPonto(paradas.getNome());
		  	      appM.setLocalizacao(paradas.getEndereco().toString());
		  	      appM.setLatLong(latLng);        
		  	      appM.setParadacod(id);
		  	      appM.setSentido(1);
		  	      appM.setSentidolinha(1);
		  	      appM.setParada(1);
		  	      Location locationA = new Location("point A");
		          locationA.setLatitude(Double.parseDouble(latx));
		          locationA.setLongitude(Double.parseDouble(lonx));
		          Location locationB = new Location("point B");
		          locationB.setLatitude(latm);
		          locationB.setLongitude(lonm);
		          float distance = locationA.distanceTo(locationB);   
		          appM.setDistyou(distance);					  		   
		  	      
		          String compara = paradas.getNome().toLowerCase(Locale.getDefault()).toString();
		          
		          for(int i=0; i< listagoogle.size(); i++) 
	       	      {
		        	  
		        	  ListaGoogle no = new ListaGoogle();
		        	  no=listagoogle.get(i);
		        	  String endecompa = no.getEndereco().toString();
		        	  String googlecompa = no.getNomeparada().toString().toLowerCase(Locale.getDefault());
		        	  if ((compara.indexOf(googlecompa) >= 0) && (endecompa.equals("@@@")))
		        	  {
		        		 
				  	      listaponto.add(appM);	
				  	       montamapaponto(appM);
				  	      
		        		  
		        		  
		        	  }
		        	  
		        	  
		        	  
		        	  
	       	      }
		          
		          
		          
		  	    
        		 
        		 
       	      }
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
		 }
		 
		 public void onPesqParadaSPTransFailure(String msg) {
		       if(dialog != null)  dialog.dismiss();
		   }   
			 
		 
		 
		 
		 
		 
		 
		 
		 
		 
         public void onPegaBusStationComplete(List<GooglePlaces> bus_station) 
         {
		
        	 
        	 String pagina = null;
        	 
        	 if (bus_station.equals(null))
        	 {
        		  if(dialog != null)  dialog.dismiss();
        		 return;
        		 
        	 }
        	 
        
        	 
        	 for(int j=0; j< bus_station.size(); j++) 
       	  {
        		 
        		 GooglePlaces place = new GooglePlaces();
        		 
        		 pagina = place.getNext_page_token();
        		 
        		 place = bus_station.get(j);
        		 
        		 List<Result> resultado = place.getResults();
        		 
        		 for(int i=0; i< resultado.size(); i++) 
        		 {
        			
        		 EventInfo appM = new EventInfo();
        			 
        		 
        		  Result local =  resultado.get(i);
        		  String nome = local.getName();
        		  List<String> tipos =  local.getTypes();
        		
        		  
        		  appM.setaLat(local.getGeometry().getLocation().getLat());
        		  appM.setaLon(local.getGeometry().getLocation().getLng());
        		  
        		  String tipo = tipos.get(0).toString();
        		  if (tipo.equals("bus_station")) {appM.setTipo(1); } //onibus
        		  if (tipo.equals("subway_station")) {appM.setTipo(2); } //metro
        		  if (tipo.equals("train_station")) {appM.setTipo(3); } //trem
        		  if (tipo.equals("taxi_stand")) {appM.setTipo(4); } //taxi
        		  if (tipo.equals("airport")) {appM.setTipo(5); } //aeroporto
        	
        		  appM.setPonto(nome);
        		  
        		  
        		  Location locationA = new Location("point A");
		          locationA.setLatitude(Double.parseDouble(latx));
		          locationA.setLongitude(Double.parseDouble(lonx));
		          Location locationB = new Location("point B");
		          locationB.setLatitude(local.getGeometry().getLocation().getLat());
		          locationB.setLongitude(local.getGeometry().getLocation().getLng());
		          float distance = locationA.distanceTo(locationB);   
		          appM.setDistyou(distance);					  	
        		  appM.setParada(1);
        		  appM.setSentido(1);
        		  appM.setSentidolinha(1);
        		  appM.setLocalizacao(" ");
        		  listaponto.add(appM);
        		  montamapaponto(appM);
        			 
        		 }
        		 
        		 
        		 
       	  }
        	 
        	 
        	/* 
        	 try
        	 {
        	 
        		 
        		 
        	 if (!pagina.equals(null))
        	 {
        		 
        		 String url1 = "https://maps.googleapis.com/maps/api/place/search/json?location="+latx+","+lonx+"&radius=500&types=subway_station%7Cbus_station%7Ctaxi_stand%7Ctrain_station%7Cairport&sensor=false&key=AIzaSyBRhULv84uFwIW0JYyGonkTM5sr5dvOR9I&pagetoken="+pagina;
				 PegaBusStation taskstation = new PegaBusStation(this);
				 taskstation.execute(url1,latx,lonx );	 
        		 
        		 
        	 }
        	 }
        	 catch (Exception e)
        	 {
        		 
        	 }
        	 */
        	 
        	 
        	 
        	 if(dialog != null)  dialog.dismiss();
        	
        	 
        	 
        	 
		 }
		 
		 public void onPegaBusStationFailure(String msg) {
		       if(dialog != null)  dialog.dismiss();
		   }
		 
	
	
	
/*
	 public void onPegaDadosGeocodeComplete(String ende) {
		 if(dialog != null)  dialog.dismiss();
	       if (ende.indexOf("São Paulo") >= 0)
	 		 {
	    	    appPrefs.setString("latflip", latx);
	    	    appPrefs.setString("lonflip", lonx);
	    	    iniciaMapa();	  	    
			    PegaPonto();
	    	 }
			  else
			  {
				Toast.makeText(MapaGeralOkActivity.this, "Local selecionado fica fora da cidade de São Paulo!", Toast.LENGTH_LONG).show(); 
           	  }
	 
	   }   
	   
	   
	   
	   
	   public void onPegaDadosGeocodeFailure(String msg) {
	       if(dialog != null)  dialog.dismiss();
	   }   
	
*/
	
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	    {
			// Inflate the menu; this adds items to the action bar if it is present.
		
		
	
		
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
	
	private Runnable runnable = new Runnable() {
    	
    	
    	
    	@Override
    	   public void run() {
    	      /* do what you need to do */
    	      if (terminou == true) 
    	      {
    	    	  //MontaPrada2();
    	    	  return;
    	    	  }	 
    	      handler.postDelayed(runnable, 500); 
              
    	   }
    	};
	
	
	
		
/*	
	
public void MontaParada()
{
	
	 LatLng latLng = null;
	
	 terminou = false;
	 contaparada = 0;
	// dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));				 
	 for(int j=0; j< listaponto.size(); j++) 
	  {
		 EventInfo appM3 = listaponto.get(j);
		 String id = listaponto.get(j).getParadacod();		 
		 PegaPontoParadaUrl("http://200.189.189.54/InternetServices/Previsao?codigoParada="+id); 
	  }
	 //dialog.dismiss();
	
	 handler.postDelayed(runnable, 3000); 
	 
	 
	 
	 	
}
	
	*/
	
public void MontaParada2()
{
	 LatLng latLng = null;
	
		
	 
	 if  (listaponto.size() == 0) {
		 Toast.makeText(this, "Localização sem paradas em um raio de 500m. Selecione outra!", Toast.LENGTH_LONG).show();
		 if(dialog != null)  dialog.dismiss();
		 return;
		 
		 
	 }
	 
	
	 for(int j=0; j< listaponto.size(); j++) 
	  {
		 EventInfo appM3 = listaponto.get(j);
		 String id = listaponto.get(j).getParadacod();
		 linha = appM3.getLinha();
		 String letreiro = appM3.getLetreiro();
		 setTitle("Paradas: "+linha+" "+letreiro);
		 montamapaponto(appM3); 
		 latLng = appM3.getLatLong();
	  }
	
	 
	 
	 
	 
	 
	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    googleMap.animateCamera(CameraUpdateFactory.zoomIn());
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
	
	if(dialog != null)  dialog.dismiss();
	
}



	

	
	
	
	public void iniciaMapa()
	{
		 LatLng latLng = null;
		 latLng = new LatLng(Double.parseDouble(latx) , Double.parseDouble(lonx));
	       
		 
		 /*
		 googleMap.addMarker(new MarkerOptions()
	        .position(latLng)
	        .title("Você")
	        .snippet("Aqui")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.eu)));
	       */
	        
	       MarkerOptions markerOptions = new MarkerOptions();
	 	   markerOptions.position(latLng);
		   markerOptions.title("Você");
		   markerOptions.snippet("Aqui");
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.eu));
		   Marker marker = googleMap.addMarker(markerOptions);   
	        
	        
	        
	        googleMap.getUiSettings().setCompassEnabled(true);
	        googleMap.getUiSettings().setZoomControlsEnabled(true);
	        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
	        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
	        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
	        Circle circle = googleMap.addCircle(new CircleOptions()
	        .center(latLng)
	        .radius(600)
	        .strokeColor(Color.GRAY)
	        .strokeWidth(2)
	        .fillColor(0x40cccccc));
	        
	        EventInfo ob = new EventInfo();
	        ob.setId("VOCÊ");
	        ob.setPonto("Você");
	        ob.setaLat(Double.parseDouble(latx) );
	        ob.setaLon(Double.parseDouble(lonx));
	        ob.setSentidolinha(1);
	        ob.setSentido(1);
	        ob.setParada(0);
	        
	        ListaObj.put(marker, ob);
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
	   
	   
	   
	   if (ob.getTipo() == 3)
	   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_bus));
	   
	   if (ob.getTipo() == 1)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_metro));
		   
	   if (ob.getTipo() == 2)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_train));
		
	   if (ob.getTipo() == 4)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_taxi));
	   if (ob.getTipo() == 5)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_airport));
	   
		
	   
	   
	   /*
	   
	   if (ob.getSentidolinha() == 1)
	   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto1));
	   
	   if (ob.getSentidolinha() == 2)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto2));
	   
	   if (ob.getSentidolinha() == -1)
		   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto3));
	   
	   */
	  
	   
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
	   
	   
	   
	//   String marcatem = ob.getPonto();
	//   String vemarca  = marcatem.substring(0, 4);
	//   if (vemarca.equals("[0] ")) {
	//	   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto3));
	 //  }
	   
	   
	   
	   
	   
	   
	   Marker marker = googleMap.addMarker(markerOptions);   
	   
	   
	  
       
      
       
	   ListaObj.put(marker, ob);
	   
	   
		   
   }
   
   


   
   
  /* 
   
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
			protected void PegaPontoLinha(String linhaw)
			{		
				
				//novo.setVisibility(View.VISIBLE);
				//dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
				listaponto.clear();
				pontolinha = true;
				String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_SearchNearby";       // API Call
				  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
				  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
				  finalURLStr = finalURLStr + "&SearchDistance=50000"; // Search distance (int)
				  finalURLStr = finalURLStr + "&Latitude=" + latx;                        // Location latitude
				  finalURLStr = finalURLStr + "&Longitude=" + lonx;                      // Location longitude
				  finalURLStr = finalURLStr + "&RecordLimit=" + "5500";        // Max number of records (int)
				  finalURLStr = finalURLStr + "&MetaKeySearch="+encodeValue("%rota_"+linhaw+"%") ;          //  
				  finalURLStr = finalURLStr + "&MetaValueSearch=";      //
				  finalURLStr = finalURLStr + "&TimeFilter=-1" ;            // Time in minutes since last update
				  finalURLStr = finalURLStr + "&MetaValueMin=";         // Minimum numeric value
				  finalURLStr = finalURLStr + "&MetaValueMax=";         // Maximum numeric value
				  finalURLStr = finalURLStr + "&SearchAsFloat=" + 1;                          // Search as numeric value
				  finalURLStr = finalURLStr + "&SortResultsDirection=";   // Sort direction (1=ascending)
				  finalURLStr = finalURLStr + "&DisableCache=" + "true";                      // Cache use flag (true or empty)
				 String url = finalURLStr.toString();
				 
				 
				 
				String url2 = "http://200.189.189.54/InternetServices/BuscaLinhasSIM?termosBusca="+linhaw;
				pontotask = new PegaPontoTask(this);
				pontotask.execute(url,url2,latx,lonx);
				 
				 
			}	
			
			*/
			
			
			
			
			
			
			
			protected void PegaPonto()
			{	
				
				  listaponto.clear();
				  pontolinha = true;
				  
				  
				  
				  /*
					 String url1 = "https://maps.googleapis.com/maps/api/place/search/json?location="+latx+","+lonx+"&radius=500&types=subway_station%7Cbus_station%7Ctaxi_stand%7Ctrain_station%7Cairport&sensor=false&key=AIzaSyBRhULv84uFwIW0JYyGonkTM5sr5dvOR9I";
					 PegaBusStation taskstation = new PegaBusStation(this);
					 taskstation.execute(url1,latx,lonx );
				  */
					
				  
				  
				  
				  
				  
				  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_SearchNearby";       // API Call
				  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
				  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
				  finalURLStr = finalURLStr + "&SearchDistance=" + distanciaw; // Search distance (int)
				  finalURLStr = finalURLStr + "&Latitude=" + latx;                        // Location latitude
				  finalURLStr = finalURLStr + "&Longitude=" + lonx;                      // Location longitude
				  finalURLStr = finalURLStr + "&RecordLimit=" + "5500";        // Max number of records (int)
				  finalURLStr = finalURLStr + "&MetaKeySearch="+encodeValue("%STOP_%");          //  
				  finalURLStr = finalURLStr + "&MetaValueSearch=";      //
				  finalURLStr = finalURLStr + "&TimeFilter=-1" ;            // Time in minutes since last update
				  finalURLStr = finalURLStr + "&MetaValueMin=";         // Minimum numeric value
				  finalURLStr = finalURLStr + "&MetaValueMax=";         // Maximum numeric value
				  finalURLStr = finalURLStr + "&SearchAsFloat=" + 1;                          // Search as numeric value
				  finalURLStr = finalURLStr + "&SortResultsDirection=";   // Sort direction (1=ascending)
				  finalURLStr = finalURLStr + "&DisableCache=" + "true";                      // Cache use flag (true or empty)
				  String url = finalURLStr.toString();
				  PesqBuddySearchBy taskbuddy = new PesqBuddySearchBy(this); 
				  taskbuddy.execute(url,latx,lonx);
				  
				  
				  
				  
				  
			}	
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*
			
			
			protected void PegaParadaSPTrans()
			{	
				
				
				for(int j=0; j< listagoogle.size(); j++) 
		    	  {
					
					 ListaGoogle no = new ListaGoogle();
					 no = listagoogle.get(j);
					 String parada =  no.getNomeparada();
					 String monta = parada.replace(" ", "%20");
					 String url = "http://olhovivo.sptrans.com.br/v0/Parada/Buscar?termosBusca="+monta;
					 PesqParadaSpTrans taskparada = new PesqParadaSpTrans(this);
					 taskparada.execute(url,latx,lonx);
					 try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
		    	  }
				
				
			 dialog.dismiss();
				
				/*
				
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
				 
				
			}	
			
			
			
			
			private class PegaParadaSPTrans extends AsyncTask<String, Integer, String> {
			     
				   public PegaParadaSPTrans(MapaGeralOkActivity mapaGeralOkActivity) {
					// TODO Auto-generated constructor stub
				}

				protected String doInBackground(String... params) {
					if (params == null) return null;
				     
				      String result = null; 
				      Cookie cookie = null;
				      String urlF = params[0];
				      DefaultHttpClient client = new DefaultHttpClient();
				      HttpGet httpget = new HttpGet("http://olhovivo.sptrans.com.br/");
				      List<Cookie> cookies = client.getCookieStore().getCookies();
				      HttpResponse response1 = null;
					
				      
				      try {
							
				    	  response1 = client.execute(httpget);
							
						} catch (ClientProtocolException e) 
						{
							msg = "Servidor indisponível. Tente mais trade.";	
							return result;
							
						} 
				      catch (IOException e) {
				    	  msg = "Servidor indisponível. Tente mais trade.";	
							return result;
						}
				      
				      
				      HttpEntity entity1 = response1.getEntity();
				      if (entity1 != null)
				      {
				          try {
								entity1.consumeContent();
							} catch (IOException e) {
								msg = "Servidor indisponível. Tente mais trade.";	
								return result;
								
							}
				      }
				     
				      if (cookies.isEmpty()) 
				      {
				    	  msg = "Servidor indisponível. Tente mais trade.";	
				    	  return result;
				      } 
				      else 
				      {
				          
				      	for (int i = 0; i < cookies.size(); i++) 
				      	{
				             // System.out.println("- " + cookies.get(i).toString());
				              cookie = cookies.get(i);
				              
				          }
				      }
				      
					      PersistentCookieStore myCookieStore = new PersistentCookieStore(MapaGeralOkActivity.this);
					      BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(),cookie.getValue());
					      newCookie.setVersion(cookie.getVersion());
					      newCookie.setDomain(cookie.getDomain());
					      newCookie.setPath(cookie.getPath());
					      myCookieStore.addCookie(newCookie);				      
						   HttpClient httpclient = new DefaultHttpClient();
						   HttpContext localContext = new BasicHttpContext();
						   localContext.setAttribute(ClientContext.COOKIE_STORE, myCookieStore);
						   HttpGet httpget2 = new HttpGet(urlF); 
						   HttpResponse response25 = null;
					   
				   
					try {
						response25 = httpclient.execute(httpget2, localContext);
					} catch (ClientProtocolException e1) {
						msg = "Servidor indisponível. Tente mais trade.";	
						return result;
						
					} catch (IOException e1) {
						msg = "Servidor indisponível. Tente mais trade.";	
						return result;
					}
				   
				   HttpEntity entity2 = response25.getEntity();
				   
				   String content = null;
					try {
						content = EntityUtils.toString(entity2);
					} catch (ParseException e) {
						msg = "Servidor indisponível. Tente mais trade.";	
						return result;
					} catch (IOException e) {
						msg = "Servidor indisponível. Tente mais trade.";	
						return result;
					}


				      return content;

					
				}
			
				protected void onProgressUpdate(Integer... progress) {
			         //setProgressPercent(progress[0]);
			     }

			     protected void onPostExecute(String sJson) 
			     {
			    	  List<PesqParada> ListaLinha = new ArrayList<PesqParada>();
			    	   
			    		try
			    	    {   
			    			  	  if(sJson == null) {
			    			        
			    			       }   
			    			   	Gson gson = new Gson(); 	
			    			   	PesqParada obj2 = gson.fromJson(sJson, PesqParada.class);
			    			    ListaLinha.add(obj2);
			    			  	  
			    	     } catch (Exception e) {
			    	    	 msg = "Servidor indisponível. Tente mais trade.";	
			    	       
			    	     
			    	    }   
			    	    
			    	 
			     }
			}
			
			
			
			protected void PegaPontoParadaUrl(String url)
			{				
				// dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));				 
				 pontoparada = new PegaPontoParada(this);
				 pontoparada.execute(url,latx,lonx);
			}	
			
			
		
			private class PegaPontoTask extends AsyncTask<String, Integer, ArrayList<String>> {
			     
				   public PegaPontoTask(MapaGeralOkActivity mapaGeralOkActivity) {
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
					                result.set(1,null);
					               // return null;        
					            }
					            InputStream is = entity.getContent();
					           // return 
					            String conv = streamToString(is);
					           	result.set(1,conv);
					        }
					        catch(IOException e){
					        	System.err.println("erro 31");
					            result.set(1,null);
					        }
					        
					        }
					        
					        
					        
					        try {
					            HttpClient clientF = new DefaultHttpClient();
					            HttpGet httpgetF = new HttpGet(urlF);
					            HttpResponse responseF = clientF.execute(httpgetF);
					            HttpEntity entityF = responseF.getEntity();
					            if(entityF == null) {
					            	System.err.println("erro 32");
					                result.set(0,null);
					            }
					            InputStream isF = entityF.getContent();
					            String convF = streamToString(isF);
					           	result.set(0,convF);
					        }
					        catch(IOException e){
					        	System.err.println("erro 33");
					            result.set(0,null);
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
				    		//dialog.dismiss();
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
						             // dialog.dismiss();
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
					  				//dialog.dismiss();
					             }
					        }
				    	
				    	
				    	
				    	
				    	
				    	
				    	try
				        {   
				        String four =(String) sJson.get(0).toString();
				  		jArray = new JSONObject(four);
				  		resultArray = jArray.getJSONArray("data");
				    	  } catch (JSONException e) {
				    		  dialog.dismiss();
				    		  Toast.makeText(MapaGeralOkActivity.this, "Problema com a conexão. Tente mais tarde.", Toast.LENGTH_LONG).show(); 
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
							  		  id = det.getString("metaValue");
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
					  	        
						  	        
						  	        	
						  	        	//PegaParada(id);
						  	        	listaponto.add(appM);						  	        	
						  	        	//PegaPontoParadaUrl("http://200.189.189.54/InternetServices/Previsao?codigoParada="+id);
					  	        
					  	        
					  	        
					  			  } 
					  			  catch (Exception ex) 
					  			  {
					  				 System.err.println("erro 3");
					  				//dialog.dismiss();
					  			  }
					  		
					    	  
					    	  
					    	  
						 } // for
				     
	
					      
					      
					     // Terminou();
					      
					    
					   
			
				     }// if
					      
					      MontaParada2();
						     
						   dialog.dismiss();
	
			}//proc
			
			}
			   
			
			
			
			
			
			
			
			
			
			
			
			private class PegaPontoParada extends AsyncTask<String, Integer, String> {
			     
				   public PegaPontoParada(MapaGeralOkActivity mapaGeralOkActivity) {
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
			
			
			
			
			
			
			
			
			
			protected void PegaSomenteParada(String codparada)
			{		
				//dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
				pontolinha = false;
				  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_Get";       // API Call
				  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
				  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
				  finalURLStr = finalURLStr + "&SocketMetaKey="+encodeValue(codparada) ;          //  
				 String url = finalURLStr.toString();
				 PegaSomenteParada pontoparada3 = new PegaSomenteParada(MapaGeralOkActivity.this);
				 pontoparada3.execute(url,latx,lonx);
			}	
	    
	    	private class PegaSomenteParada extends AsyncTask<String, Integer, String> {
			     
				   public PegaSomenteParada(MapaGeralOkActivity mapaGeralOkActivity) {
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
			    	   JSONObject jArray = null;
			    	   JSONArray  jfotos = null;
			    	   JSONObject jsonR  = null;
			    	   JSONArray resultArray = null;
			    	   int tipo = 0;
			    	   String id = null;
			    	
			    	   
			    	
			    
			    	   
			    	if(sJson == null) {
			    		 System.err.println("erro 10");
			    		return;
			        }        
			         
			        
			    	String four = null;
			    
			    	try
			        {   
			        four =(String) sJson.toString();
			  		jArray = new JSONObject(four);
			  		resultArray = jArray.getJSONArray("data");
			    	  } catch (JSONException e) {
			    		  //dialog.dismiss();
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
						    	  id = linha.replace("Parada_", "");
						  		  String nome = det.getString("metaValue");
						  		  Double latm = det.getDouble("metaLatitude");
						  		  Double lonm = det.getDouble("metaLongitude");
						  		  EventInfo appM = new EventInfo();
				                  appM.setSentidolinha(1);
					              appM.setSeqparada(seq);
						            LatLng latLng = new LatLng(latm , lonm);					  		 	
						  		 	appM.setaLat(latm);
						  	        appM.setaLon(lonm);
						  	        appM.setId(id);
						  	        appM.setPonto(nome);
						  	        appM.setLatLong(latLng);        
						  	        appM.setParadacod(id);
						  	        appM.setSentido(1);
						  	        appM.setSentidolinha(1);
					  	        	listaponto2.add(appM);						  	        	
					  	    
				  	        
				  	        
				  			  } 
				  			  catch (Exception ex) 
				  			  {
				  				 System.err.println("erro 3");
				  				//dialog.dismiss();
				  			  }
				  		
				    	  
				    	  
				    	  
					 } // for
			     

				      
				      
				     // Terminou();
				      
				    
				   
		
			     }// if
			    	//dialog.dismiss();
			    	
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
			
	
			
			
			public void Blinha(View v)
			{

				appPrefs.setString("listalinhas", "");
				
				Intent nextScreen = new Intent(MapaTrajetoLinha.this, MostraSearchLinhasActivity.class);
		        startActivity(nextScreen);
				
				
		        
		        
		        
		        
		        
				/*
				
				
				
				
				
				PromptDialog dlg = new PromptDialog(MapaGeralOkActivity.this, R.string.prompttitle, R.string.promptmens) {   
					 @Override  
					 public boolean onOkClicked(String input) {   
					 	 if (input.trim().length() == 7)
								 {	
					 		 		Intent nextScreen = new Intent(MapaGeralOkActivity.this, MostraParadaLinhaActivity.class);
			        		        nextScreen.putExtra("linha", "" + input);
			        		        startActivityForResult(nextScreen, 0);
						  	 } else
								 {
									 Toast.makeText(MapaGeralOkActivity.this, "O código da linha deve ter 7 caracteres. Ex: 971D-10!", Toast.LENGTH_LONG).show(); 
								 }
					  return true; // true = close dialog   
					 }   
					};   
					
					dlg.show();
				*/
				
			}
			
			
			
			
			/*
			
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
		    	    int senti = objeven.getSentidolinha();
		    	    
		    	    if ((seq < menor) && (senti == 1)){
		    	    	
		    	    	menor = seq;
		    	    	latitude_cur = Double.toString(objeven.getaLat());
		    	    	longitude_cur = Double.toString(objeven.getaLon());
		    	    	paradaorigem = objeven.getParadacod();
		    	    	
		    	    }
		    	    
		    	    if ((seq > maior) && (senti ==1)){
		    	    	
		    	    	maior = seq;
		    	    	latitude = Double.toString(objeven.getaLat());
		    	    	longitude = Double.toString(objeven.getaLon());
		    	    	paradadestino = objeven.getParadacod();
		    	    	
		    	    }
		    	    
		    	    
		    	    
		    	  
		    	  
		    	  }
				
				
				  
				listaponto2.clear();  
				PegaSomenteParada("Parada_"+paradaorigem);
				PegaSomenteParada("Parada_"+paradadestino);
				
				  
				  
				
				
						    
				
			}
			
			
			*/
			
			
			
			
			
			public void Bfavorito(View v)
			{

				
				
				Intent intent = new Intent(this, PegaCidaActivity.class);
				Bundle b = new Bundle();
				intent.putExtras(b);
				startActivity(intent);
				    
				    
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			public void Brefresh(View v)
			{

				
				
				
				
				 googleMap.clear();
				 iniciaMapa();	  	    
			     PegaPonto();
				
			}
			
			
			
			
			public void Bconfi(View v)
			{

				 Intent i = new Intent(this, UserSettingActivity.class);
		         startActivityForResult(i, RESULT_SETTINGS);
				
				
				/*
				 googleMap.clear();
				 iniciaMapa();	  	    
			     PegaPonto();
				*/
			}
			
			
			
			
			public void Btrajeto(View v)
			{

				Intent intent = new Intent(this, SobreActivity.class);
		        startActivity(intent);
				
				 /*
				    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				    Uri.parse("http://maps.google.com/maps?mode=transit"));
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    intent.addCategory(Intent.CATEGORY_LAUNCHER );     
				    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				    startActivity(intent);
				   */ 
				    
			}
			
			
			
			
			
			
			public void Btrajetos(View v)
			{
				Intent intent = new Intent(this, MostraTrajetoActivity.class);
			
				startActivity(intent);
					
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			public void Bposicao(View v)
			{
				Intent intent = new Intent(this, MapaSelLocalActivity.class);
				Bundle b = new Bundle();
				intent.putExtras(b);
				startActivity(intent);
					
				
				
				/*
					distanciaw = "500";
					googleMap.clear();
					  LatLng latLng = null;
						 latLng = new LatLng(Double.parseDouble("-23.550268") , Double.parseDouble("-46.634325"));
						 googleMap.addMarker(new MarkerOptions()
					        .position(latLng)
					        .title("Local")
					        .snippet("Praça da Sé")
					        .icon(BitmapDescriptorFactory.fromResource(R.drawable.eu)));
						 googleMap.getUiSettings().setCompassEnabled(true);
						 googleMap.getUiSettings().setZoomControlsEnabled(true);
						 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
						 googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
					
						 */
						 
						// Toast.makeText(this, "Indique no mapa a nova posição com um longo click!", Toast.LENGTH_LONG).show();
					
					
				
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
			
			
			
		    @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		        super.onActivityResult(requestCode, resultCode, data);
		 
		        switch (requestCode) {
		        case RESULT_SETTINGS:
		            showUserSettings();
		            break;
		 
		        }
		 
		    }

			
			
		    public void showUserSettings() {
		        SharedPreferences sharedPrefs = PreferenceManager
		                .getDefaultSharedPreferences(this);
		    }
			
		    		    
		    
			
			
			
}

package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Callbacks;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.Response;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;




public class MainActivity extends Activity implements PegaDadosGeocodeListener{

	
	
	 String latx = "";
	 String lonx = "";
	 String latxflip = "";
	 String lonxflip = "";	 
	 String latold = "";
	 String lonold = "";
	 String Endereco = null;
	 String UserID = null;
	 String UserToken = null;
	 boolean flip = true;
	 boolean parar = false;
	 double latmarca =  0; 
 	 double lonmarca =  0;
 	 boolean semonibus_w = false;
 	 boolean selecionado_w = false;

 	 GMapV2Direction teste = new GMapV2Direction();
	 PegaDadosGeocode  task  = new PegaDadosGeocode(this);
	 ConnectionDetector cd;
	 private ProgressDialog dialog;
	 AlertDialogManager alert = new AlertDialogManager();
	 SharedPreferences shared_preferences;
	 SharedPreferences.Editor shared_preferences_editor;
     boolean existe = false;
	
		public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
		public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
		public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
		public final static String SPtransToken = "03d46461ccbf04b6ae98629e563cedcaf153bcdb31f9aa2c531a9a08e7aab7bb";
		
		public final static String SPTransURL = "http://api.olhovivo.sptrans.com.br/v0/";
		
		
		
		// Login/Autenticar?token={token}
		// Linha/Buscar?termosBusca={termosBusca}
		// Linha/CarregarDetalhes?codigoLinha={codigoLinha} 
		// Parada/Buscar?termosBusca={termosBusca}
		// Parada/BuscarParadasPorLinha?codigoLinha={codigoLinha}
		// Parada/BuscarParadasPorCorredor?codigoCorredor={codigoCorredor}
		// Corredor
		// Posicao?codigoLinha={codigoLinha}
		// Previsao?codigoParada={codigoParada}&codigoLinha={codigoLinha}
		// Previsao/Linha?codigoLinha={codigoLinha} 
		// Previsao/Parada?codigoParada={codigoParada} 
		
		 String sUsernameID = null;
		
		// Get AuthenticatedUser object to get data of user
		AuthenticatedUser mAuthUser = null;
		
		Context mContext = null;
		
		private static BuddyClient buddyClient = null;

		 private static final int RESULT_SETTINGS = 1;
		private static final int GPS_ERRORDIALOG_REQUEST = 0; 
		 
		  HelperSharedPreferences appPrefs = null;
	  
   @Override
   protected void onCreate(Bundle savedInstanceState) {
     
	   super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       cd = new ConnectionDetector(getApplicationContext());
       appPrefs = new HelperSharedPreferences(getApplicationContext());
      
       
       
       
       
       
       
       if (!cd.isConnectingToInternet())
	   {
           AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           builder.setTitle(MainActivity.this.getResources().getString(R.string.interneterrorpede))
               .setMessage(MainActivity.this.getResources().getString(R.string.interneterror))
               .setCancelable(false)
               .setIcon(R.drawable.ic_launcher)
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                  	 MainActivity.this.finish();
                  	 return;
                   }
               });
             builder.create().show();  
       return;
	   }
       
       
       
       
       
       
       Iniciar();
       
       
      
       
       
       
       

		
        
        /*
	    latx = shared_preferences.getString("lat", "");
	    lonx = shared_preferences.getString("lon", ""); 
	    UserID = shared_preferences.getString("UserID", "");
	    UserToken = shared_preferences.getString("UserToken", "");
	    flip = shared_preferences.getBoolean("flip", true);
	    latxflip = shared_preferences.getString("latflip", "");
	    lonxflip = shared_preferences.getString("lonflip", "");     
	    parar = shared_preferences.getBoolean("parar", false);     
	    shared_preferences_editor = shared_preferences.edit();
	    shared_preferences_editor.putString("paradacod", "");
	    shared_preferences_editor.putBoolean("selecionado_w", false);
	    shared_preferences_editor.putBoolean("semonibus_w", false);
	    shared_preferences_editor.commit();
	    */
	    
	    
	    
	    
	    
	    
	   /* 
	    
	    if (parar == false)
     {
       inicio();
     }
	    else
	    {
		    shared_preferences_editor = shared_preferences.edit();
		    shared_preferences_editor.putBoolean("parar", false);
		    shared_preferences_editor.commit();
		    ligaGPS();
	    }
       */
       

 
   }
   
   
   
   
   public void Iniciar()
   {
	   
	 
	   PegaPreferences();
     //  showUserSettings();
    
	   if (servicesOk() == false) return;
	   
	   
	   
	   dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.iniciando));
       
	   
       Inicio();

	   
   }
   
   
   public void PegaPreferences()
   {
	   
	  // appPrefs.setString("listapontos", "OK");
	   appPrefs.setString("listalinhas", "OK");
	   
	   
	   
	   latx      =   appPrefs.getString("lat", "");
	   lonx      =   appPrefs.getString("lon", "");
	   UserID    =   appPrefs.getString("UserID", "");
	   UserToken =   appPrefs.getString("UserToken", "");
	   latxflip  =   appPrefs.getString("latflip", "");
	   lonxflip  =   appPrefs.getString("lonflip", "");	   
   }
   
   
   public boolean TemInternet()
   {
	   if (!cd.isConnectingToInternet())
	   {
           AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           builder.setTitle(MainActivity.this.getResources().getString(R.string.interneterrorpede))
               .setMessage(MainActivity.this.getResources().getString(R.string.interneterror))
               .setCancelable(false)
               .setIcon(R.drawable.ic_launcher)
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                  	 MainActivity.this.finish();
                   }
               });
             builder.create().show();  
           return false;
           
	   } else
	   {
		   return true;
	   }
	   
   }
   
   
   private boolean servicesOk() {
	    int isAvailable = GooglePlayServicesUtil
	            .isGooglePlayServicesAvailable(this);
	    if (isAvailable == ConnectionResult.SUCCESS) {
	        return true;
	    } else 
	   
	    	if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
	                this, GPS_ERRORDIALOG_REQUEST);
	        
	        dialog.show();
	    } else {
	        Toast.makeText(this, "O seu celular não possui o Google Play atualizado", Toast.LENGTH_SHORT)
	                .show();

	    }
	    return false;
	}
  
   
   
   
   
   
   
   
   
   
   public void Clicou(View v)
	{
	   
	   
	   appPrefs.setString("lat", latx);
	   appPrefs.setString("lon", lonx);
	   Intent intent = new Intent(this, MapaGeralOkActivity.class);
       startActivity(intent);
		
	   /*
	   
	    shared_preferences_editor = shared_preferences.edit();
	    shared_preferences_editor.putString("lat", latx);
	    shared_preferences_editor.commit();
	    shared_preferences_editor = shared_preferences.edit();
	    shared_preferences_editor.putString("lon", lonx);
	    shared_preferences_editor.commit();
	     */   
	}
   
   private void Inicio()
   {
     
       
        ligaGPS();
        
        
        if  (UserToken.equals(""))
        {
        sUsernameID = UUID.randomUUID().toString();
        try
        {
        Thread.sleep(110);
        }
        catch (Exception e)
        {
        	
        }
        UserAccount_Profile_CheckUserName();
        }
        
        
   }
   

   
   public void onPegaDadosGeocodeComplete(String ende) {
       // dismiss the progress dialog
       
	   
	   if(dialog != null)  dialog.dismiss();
 
	   
	   try
	   {
	   
	   
	   
       if (ende.indexOf("SP, RMSP") >= 0)
 		  {
 	        
    	   appPrefs.setString("endereco", ende); 
   	       }
		  else
		   {
		 	 latx = ("-23.550268"); 
        	 lonx = ("-46.634325");
        	 
        	 appPrefs.setString("endereco", "Praça da Sé, São Paulo");
      	    
        	 
		   }
       
	   
   } catch (Exception e) {
	   
	   latx = ("-23.550268"); 
  	   lonx = ("-46.634325"); 	 
  	   appPrefs.setString("endereco", "Praça da Sé, São Paulo");
	    
     }
   
       
       
 
/*
       appPrefs.setString("lat", latx);
	   appPrefs.setString("lon", lonx);
	   Intent intent = new Intent(this, MapaGeralOkActivity.class);
       startActivity(intent);
  */         
   }   
   
   
   
   
   public void onPegaDadosGeocodeFailure(String msg) {
       
	   
	   if(dialog != null)  dialog.dismiss();
 
	   Toast.makeText(this, "Problema com a conexão. Tente mais tarde.", Toast.LENGTH_LONG).show();
	   
	   
   }   
   
   
   
   
   public void ligaGPS()
   {
	   
	   GPSTracker gps = new GPSTracker(this);     
       
	   
	   if(gps.canGetLocation())
       
       { 
       
       	
       double latw = gps.getLatitude(); // returns latitude
       double lonw = gps.getLongitude(); // returns longitude
       
       
       latx = Double.toString(latw);
       lonx = Double.toString(lonw);
       
       String url =  "http://nominatim.openstreetmap.org/reverse?format=json&lat="+latx+"&lon="+lonx+"&zoom=18&addressdetails=1";
    
       // String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+latx+","+lonx+"&sensor=false";
        task.execute(url);
       
      
       gps.stopUsingGPS();
       }

	   
	   
   }
   
   
   
     
   
   
   public void UserAccount_Profile_CheckUserName() {
	   
	   
	   existe = false;
	   
	     try {
	    	 
	         mContext = this.getApplicationContext();
    	     buddyClient = new BuddyClient("busao_sp", "31336B44-C31B-4254-87CE-1B61AB69AECE", mContext);
	         buddyClient.checkIfUserNameExists(sUsernameID, null, new Callbacks.OnCallback<Response<Boolean>>() {
	   
	         @Override
	         public void OnResponse(Response<Boolean> response, Object state) {
	   
	           if (response != null) 
	           {
	             if (response.getThrowable() != null) {
	              // Log.v("UserAccount_Profile_CheckUserName Error: ", response.getThrowable().toString());
	             } else {
	             	 UserAccount_Profile_Create(); 
	               //Log.v("Does this username exist? ", response.getResult().toString());
	   
	             }
	           } else
	           {
	        	  existe = true; 
	           }
	   
	         }
	       });
	   
	     } catch (Exception e) {
	       // Handle exception
	     }
	   
	   
   
	     
	   }

   
   
   

   
   
   public void UserAccount_Profile_Create() {
	   
	     try {
	   
	       // Get Application Context object
	       mContext = this.getApplicationContext();
	   
	       // Get BuddyClient object using Application Context
	       buddyClient = new BuddyClient("busao_sp", "31336B44-C31B-4254-87CE-1B61AB69AECE", mContext);
	   
	       buddyClient.createUser(sUsernameID, "laranjamecanica", new OnCallback<Response<AuthenticatedUser>>() {
			
			@Override
			public void OnResponse(Response<AuthenticatedUser> arg0, Object arg1) {
				 if (arg0 != null) {
					   
		             // Check for the error
		             if (arg0.getThrowable() != null) {
		              // Log.v("UserAccount_Profile_Create Error: ", arg0.getThrowable().toString());
		             } else {
		   
		               // If user is created successfully then get user
		               // data.
		               mAuthUser = arg0.getResult();
		   
		               if (mAuthUser.getToken() != null) {
		   
		            	   
		            	   mAuthUser.checkIn(Double.parseDouble(latx), Double.parseDouble(lonx), "1000-Usuário Novo", "", null, new Callbacks.OnCallback<Response<Boolean>>() {
		            		   
		            		   @Override
		            		                  public void OnResponse(Response response, Object state) {
		            		   
		            		                     if (response != null) {
		            		                       // Check for the error
		            		                       if (response.getThrowable() != null) {
		            		                        // Log.v("UserAccount_Location_Checkin Error: ", response.getThrowable().toString());
		            		                       } else {
		            		                         if ((Boolean) response.getResult()) {
		            		                           // Handle the response,
		            		                          // Log.v("LOG_TAG", "User checked in at Latitude " + latx + " Longitude " + lonx);
		            		   
		            		                         }
		            		   
		            		                       }
		            		                     }
		            		   
		            		                   }
		            		                 });
		            	 
		                 appPrefs.setString("UserToken", mAuthUser.getToken());
		                 appPrefs.setString("UserID", sUsernameID);
		                
		               }
		             }
		           }
		   
		         }
				
			
		});
	   
	     } catch (Exception ex) {
	       // Handle the error
	     }
	   
	   }
	
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main, menu);    
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
		
		public void Bconfi()
		{

			
			/*
			 Intent i = new Intent(this, UserSettingActivity.class);
	         startActivityForResult(i, RESULT_SETTINGS);
			*/
			
			
		}
		




		
		
		protected void PegaSomenteParada()
		{		
			//dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
			
			
			
			
			 String url = "http://www.olhovivo.sptrans.com.br/#sp?";
			 String url2 = URLEncoder.encode("cat=Linha&l=");
			 String url3 = url+url2+"509M";
			 
			 PegaSomenteParada pontoparada3 = new PegaSomenteParada(MainActivity.this);
			 pontoparada3.execute(url3);
		}	
    
    	private class PegaSomenteParada extends AsyncTask<String, Integer, String> {
		     
			   public PegaSomenteParada(MainActivity mainActivity) {
				// TODO Auto-generated constructor stub
			}

			protected String doInBackground(String... params) {
				  
			        
			        String urlF = params[0];
			        String result5 = null;
				      
			       
			        
				        
				        try {
				            HttpClient clientF = new DefaultHttpClient();
				            HttpGet httpgetF = new HttpGet(urlF);
				            HttpResponse responseF = clientF.execute(httpgetF);
				            HttpEntity entityF = responseF.getEntity();
				            if(entityF == null) {
				              
				                result5 = null;
				            }
				            InputStream isF = entityF.getContent();
				            String convF = streamToString(isF);
				           	result5 = convF;
				        }
				        catch(IOException e){
				           
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
		    	
		    	
		    	
		    	

		}
		

    
    	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
    	public String encodeValue(String rawValue) {
			  String result = null;      
			  try {
			    result = URLEncoder.encode(rawValue, "UTF-8") 
			                        .replaceAll("\\%28", "(")
			                        .replaceAll("\\%26", "&")
			                        .replaceAll("\\%29", ")")
			                        .replaceAll("\\+", "%20")
			                        .replaceAll("\\%27", "'")
			                        .replaceAll("\\%21", "!")
			                        .replaceAll("\\%7E", "~")
			                        .replaceAll("\\%3D", "=")
			                        .replaceAll("\\%3F", "?")
			                        .replaceAll("\\%2A", "*");
			  } catch (UnsupportedEncodingException e) {
			    // Handle the error
			  }
			  return result;
			}  
		
		
		
		
		
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
		

		
		
		
		
		
		
		
		
		
		
		
		/*
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
			
		*/
		
		
		
				
		
				
}

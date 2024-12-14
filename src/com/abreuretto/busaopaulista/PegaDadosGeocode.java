package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;


public class PegaDadosGeocode extends AsyncTask<String, Void, String>{
    private final PegaDadosGeocodeListener listener;
    private String msg;



  public PegaDadosGeocode(PegaDadosGeocodeListener listener) {
      this.listener = listener;
       
  }
  
  
  @Override
  protected String doInBackground(String... params) {
      if(params == null) return null;
      String urlG = params[0];
      String result = null; 
      
      if (isCancelled()) 
      {
      	msg = "No response from server";	
      	return result;
      }
      
      try {
          HttpClient clientF = new DefaultHttpClient();
          HttpGet httpgetF = new HttpGet(urlG);
          HttpResponse responseF = clientF.execute(httpgetF);
          HttpEntity entityF = responseF.getEntity();
      
          if(entityF == null) {
              msg = "No response from server";
              result = null;
          }
          InputStream isF = entityF.getContent();
          String convF = streamToString(isF);
        	result = convF;
      }
      catch(IOException e){
          msg = "No Network Connection";
          result = null;
      }
  
      return result;  

      
      
      
  }
   
  protected void onPostExecute(String sJson) {
   
	  String endereco = null;
   
	try
    {   
		  	  if(sJson == null) {
		          if(listener != null) listener.onPegaDadosGeocodeFailure(msg);
		          return;
		       }   
		  	  
		  	  
		JSONObject jsonObject = new JSONObject(sJson);  	  
		  	  
		endereco = jsonObject.getString("display_name"); 
		
		
		  
		  	  
     } catch (Exception e) {
        msg = "Foursquare unavailable...";
        if(listener != null) listener.onPegaDadosGeocodeFailure(msg);
     
    }   
    if(listener != null) listener.onPegaDadosGeocodeComplete(endereco);
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


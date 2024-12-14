package com.abreuretto.googleplaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import com.google.gson.Gson;





//https://maps.googleapis.com/maps/api/place/search/json?location=-23.603102,-46.665793&radius=500&types=subway_station|bus_station|taxi_stand|train_station|airport&sensor=false&key=AIzaSyBRhULv84uFwIW0JYyGonkTM5sr5dvOR9I

//subway_station|bus_station|taxi_stand|train_station|airport



public class PegaBusStation extends AsyncTask<String, Void, String>{
    private final PegaBusStationListener listener;
    private String msg;



  public PegaBusStation(PegaBusStationListener listener) {
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
   
	  List<GooglePlaces> ListaLinha = new ArrayList<GooglePlaces>();
   
	try
    {   
		  	  if(sJson == null) {
		          if(listener != null) listener.onPegaBusStationFailure(msg);
		          return;
		       }   
		   	Gson gson = new Gson(); 	
		   	GooglePlaces obj2 = gson.fromJson(sJson, GooglePlaces.class);
		    ListaLinha.add(obj2);
		  	  
     } catch (Exception e) {
        msg = "Foursquare unavailable...";
        if(listener != null) listener.onPegaBusStationFailure(msg);
     
    }   
    if(listener != null) listener.onPegaBusStationComplete(ListaLinha);
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


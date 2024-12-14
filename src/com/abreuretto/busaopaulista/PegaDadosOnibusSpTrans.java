package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.widget.Toast;

public class PegaDadosOnibusSpTrans extends AsyncTask<String, Void, String>{
    private final PegaDadosOnibusSpTransListener listener;
    private String msg;



  public PegaDadosOnibusSpTrans(PegaDadosOnibusSpTransListener listener) {
      this.listener = listener;
       
  }
  
  
  @Override
  protected String doInBackground(String... params) {
      if(params == null) return null;
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
   
  protected void onPostExecute(String sJson) {
   
	  List<OnibusRoot> ListaOnibus = new ArrayList<OnibusRoot>();
	  
   
	try
    {   
 
		if(sJson == null) 
		{
         return;
        }        
      
 	Gson gson = new Gson(); 	
 	OnibusRoot obj2 = gson.fromJson(sJson, OnibusRoot.class);
    ListaOnibus.add(obj2);
        
     } catch (Exception e) {
        msg = "Foursquare unavailable...";
        if(listener != null) listener.onPegaDadosOnibusFailure(msg);
     
     }   

    
    if(listener != null) listener.onPegaDadosOnibusComplete(ListaOnibus);
       
       
       
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


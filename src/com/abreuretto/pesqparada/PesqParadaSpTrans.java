package com.abreuretto.pesqparada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.PersistentCookieStore;



//Pesquisa parada no SPTrans:  curl --cookie sptrans_cookies http://olhovivo.sptrans.com.br/v0/Parada/Buscar?termosBusca=paulista




public class PesqParadaSpTrans extends AsyncTask<String, Void, String>{
    private final PesqParadaSPTransListener listener;
    private String msg;
    

  public PesqParadaSpTrans(PesqParadaSPTransListener listener) {
      this.listener = listener;
       
  }
  
  
  @Override
  protected String doInBackground(String... params) {
      
	  
	  
	  
	  
	  if (params == null) return null;
	     
      String result = null; 

    
      
      
      if (isCancelled()) 
      {
      	msg = "Servidor indisponível. Tente mais trade.";	
      	return result;
      }
    
      
      
		
      Cookie cookie = null;
      String urlF = params[0];
      DefaultHttpClient client1 = new DefaultHttpClient();   
      String Ende = "http://api.olhovivo.sptrans.com.br/v0/Login/Autenticar?token=03d46461ccbf04b6ae98629e563cedcaf153bcdb31f9aa2c531a9a08e7aab7bb";
      HttpPost httpget = new HttpPost(Ende);
    //  httpget.setHeader("Content-Length", "20");      
      httpget.setHeader("Content-Type", "charset=utf-8");
      httpget.setHeader("User-Agent", "Fiddler");
      httpget.setHeader("Host", "api.olhovivo.sptrans.com.br");
      
    
      
      List<Cookie> cookies = client1.getCookieStore().getCookies();
      HttpResponse response1 = null;
	  try {
			
    	  response1 = client1.execute(httpget);
			
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
      
	      //PersistentCookieStore myCookieStore = new PersistentCookieStore(null);
	      BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(),cookie.getValue());
	      newCookie.setVersion(cookie.getVersion());
	      newCookie.setDomain(cookie.getDomain());
	      newCookie.setPath(cookie.getPath());
	      //myCookieStore.addCookie(newCookie);

      
		   HttpClient httpclient = new DefaultHttpClient();
		   HttpContext localContext = new BasicHttpContext();
		   CookieStore cookieStore = new BasicCookieStore();
		   cookieStore.addCookie(newCookie);
		   localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
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
   
  protected void onPostExecute(String sJson) {
   
	  ArrayList<PesqParada> ListaLinha = new ArrayList<PesqParada>();
   
	try
    {   
		  	  if(sJson == null) {
		          if(listener != null) listener.onPesqParadaSPTransFailure(msg);
		          return;
		       }   
		  	  
		  	  
		  	
		  	JSONArray   para = new JSONArray(sJson);  
		  	for(int j=0; j< para.length(); j++) 
     	      {
		  		
		  		PesqParada paradasp = new PesqParada();
		  		
		  		JSONObject det = para.getJSONObject(j);	
		  		String Nome = det.get("Nome").toString();
		  		String Endereco = det.get("Endereco").toString();
		  		double Latitude = (Double) det.get("Latitude");
		  		double Longitude = (Double) det.get("Longitude");
		  		String CodigoParada = det.get("CodigoParada").toString();
		  		
		  		
		  		paradasp.setCodigoParada(CodigoParada);
		  		paradasp.setEndereco(Endereco);
		  		paradasp.setNome(Nome);
		  		paradasp.setLatitude(Latitude);
		  		paradasp.setLongitude(Longitude);
		  		ListaLinha.add(paradasp);
		  		

		  		
		  		
     	      }
		  	
		  	
		  	
		  			
		  
		  	  
     } catch (Exception e) {
    	 msg = "Servidor indisponível. Tente mais trade.";	
        if(listener != null) listener.onPesqParadaSPTransFailure(msg);
     
    }   
    if(listener != null) listener.onPesqParadaSPTransComplete(ListaLinha);
    
    
    
    
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


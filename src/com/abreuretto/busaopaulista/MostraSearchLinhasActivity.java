package com.abreuretto.busaopaulista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;





import com.abreuretto.buddysearchnearby.Datum;
import com.abreuretto.busaopaulista.R;
import com.abreuretto.pesqlinha.PesqLinha;
import com.abreuretto.pesqlinha.PesqLinhaSPTrans;
import com.abreuretto.pesqlinha.PesqLinhaSPTransListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MostraSearchLinhasActivity extends ListActivity implements PesqLinhaSPTransListener{

	//private ListView lv;

	private EditText et;
	String pesquisar = null;
   // PesqBuddySearchBy task; 
    ConnectionDetector cd;
    InputMethodManager imm ;
    
    ArrayList<Datum> enviar = new ArrayList<Datum>();
	
    private ProgressDialog dialog;
  //  ArrayList<BuddySearchNearBy> listaponto = new ArrayList<BuddySearchNearBy>();
    ArrayList<LinhasNomes> listalinhas = new ArrayList<LinhasNomes>();
    
    HelperSharedPreferences appPrefs = null;

	// Constants
	public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
	public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
	public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
    
	
    
    
    
	private String listview_array[] = { "ONE", "TWO", "THREE", "FOUR", "FIVE",

	"SIX", "SEVEN", "EIGHT", "NINE", "TEN" };

	private ArrayList<String> array_sort= new ArrayList<String>();

	int textlength=0;




	public void onCreate(Bundle savedInstanceState)

	{

	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_mostra_search_linhas);

	 
	
	
	
	
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
             	   MostraSearchLinhasActivity.this.finish();
                }
            });
          builder.create().show();  
          
        return;

	}
	
	
	
	
	
	
	
	
	
	
	
	 

	//lv = (ListView) findViewById(R.id.ListView01);
	et = (EditText) findViewById(R.id.EditText01);
	imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
	appPrefs = new HelperSharedPreferences(getApplicationContext());
	
	String valores = appPrefs.getString("listalinhas", "OK");
	 
	 if (!valores.equals("OK"))
				   {
		 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
				   GsonBuilder gsonb = new GsonBuilder();
				   Gson gson = gsonb.create();
				   Datum[] listaP = gson.fromJson(valores, Datum[].class);
				   enviar.clear();
							   for(int h=0; h < listaP.length ; h++) 
						 	  {
								   enviar.add(listaP[h]);
						 	  }
							   
							   
							     AdapterSearchLinhas adapter = new AdapterSearchLinhas(MostraSearchLinhasActivity.this, enviar);
				  	             adapter.notifyDataSetChanged();         
				  	             setListAdapter(adapter);  
				  	             dialog.dismiss();
				  	             mostra();
		   
							   
				   
				   }



	}	
	
	
	
	
	
	
	
	
	
	
	
	 public void onPesqLinhaSPTransComplete(List<PesqLinha> listalinha) 
	 {
		 
		 if(dialog != null)  dialog.dismiss();
     	
		 
		 
		 for (int i = 0; i < listalinha.size(); i++)
  		{
			 
			 PesqLinha nolinha = new PesqLinha();
			 nolinha = listalinha.get(i);
			 int sentido = nolinha.getSentido();
			 if (sentido == 1)
			 {
		    	   Datum linhasnomes = new Datum();
		    	   linhasnomes.setMetaKey(nolinha.getLetreiro().toString()+"-"+nolinha.getTipo().toString()+"-1");
		    	   linhasnomes.setMetaValue(nolinha.getDenominacaoTPTS().toString()+" / "+nolinha.getDenominacaoTSTP().toString());
		    	   
		    	   try
		    	   {
		    	   linhasnomes.setAppTag(nolinha.getCodigoLinha().toString());
		    	   }
		    	   catch (Error e) 
		    	   {
		    		   linhasnomes.setAppTag("0");
		    	   }
		    			   
		    	   
		    	   enviar.add(linhasnomes);
			 } 
			 
			 else
			
			 {
				 Datum linhasnomes = new Datum();
				   linhasnomes.setMetaKey(nolinha.getLetreiro().toString()+"-"+nolinha.getTipo().toString()+"-0");
		    	   linhasnomes.setMetaValue(nolinha.getDenominacaoTSTP().toString()+" / "+nolinha.getDenominacaoTPTS().toString());
		    	   try
		    	   {
		    	   linhasnomes.setAppTag(nolinha.getCodigoLinha().toString());
		    	   }
		    	   catch (Error e) 
		    	   {
		    		   linhasnomes.setAppTag("0");
		    	   }
		    	   enviar.add(linhasnomes);
				 
			 }
			 
			 
  		}
		 
		 
		 
		 
		
				
				
				try
				{
	            Gson lista = new Gson();
	            String listagem = lista.toJson(enviar.toArray()).toString();
	            appPrefs.setString("listalinhas", listagem);
				}
				catch (Exception e)
				{
					
				}
				
				
				
				 
				 AdapterSearchLinhas adapter = new AdapterSearchLinhas(MostraSearchLinhasActivity.this, enviar);
	             adapter.notifyDataSetChanged();         
	             setListAdapter(adapter);  
	             if(dialog != null)  dialog.dismiss();
	             mostra();


		 
		 
		 
		 
		 
		 
		 
		 
	 }
	
	
	 public void onPesqLinhaSPTransFailure(String msg) {
		 if(dialog != null)  dialog.dismiss();
        
       

       	Toast.makeText(this, "Problema na conexão para esta pesquisa. Tente masi tarde!", Toast.LENGTH_LONG).show(); 
       	return;
 	   }   
	
	
	
	
	
	
	
	
	
	
	
	
	public void Clicou(View v)
	{
		
		

		pesquisar = et.getText().toString();	
		enviar.clear();
		et.setText("");

		
		
		if (pesquisar.trim().length() > 2)
		{
		esconde();
		PegaLinhaSP(pesquisar.trim());
		} else
		{
			if ((pesquisar.trim().length() > 0))
					{
			Toast.makeText(this, "Informe no mínimo 3 caracteres!", Toast.LENGTH_LONG).show();
					}
		}

		
		
		
	}
	
	
	
	
	/*
	protected void PegaPontoLinha(String linhaw, int tipo)
	{		
		
		
		  listaponto.clear();
		 
		  dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));
		  
		  
		  String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_ApplicationMetaDataValue_SearchData";       // API Call
		  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
		  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
		  finalURLStr = finalURLStr + "&SearchDistance=40075000"; // Search distance (int)
		  finalURLStr = finalURLStr + "&Latitude=-1" ;                        // Location latitude
		  finalURLStr = finalURLStr + "&Longitude=-1";                      // Location longitude
		  finalURLStr = finalURLStr + "&RecordLimit=" + "5500";        // Max number of records (int)
		  
		  if (tipo == 0) 
		  {
			  String linhaok = encodeValue("%trip_"+linhaw+"%"); 
		  finalURLStr = finalURLStr + "&MetaKeySearch="+linhaok+"&MetaValueSearch=";          //  
		  } else
		  {
			  String linhaok = encodeValue("%"+linhaw+"%");
			  
			  finalURLStr = finalURLStr + "&MetaKeySearch=&MetaValueSearch="+linhaok;
		  }
		  
		  finalURLStr = finalURLStr + "&TimeFilter=-1" ;            // Time in minutes since last update
		  finalURLStr = finalURLStr + "&MetaValueMin=";         // Minimum numeric value
		  finalURLStr = finalURLStr + "&MetaValueMax=";         // Maximum numeric value
		  finalURLStr = finalURLStr + "&SearchAsFloat=0";                          // Search as numeric value
		  finalURLStr = finalURLStr + "&SortResultsDirection=";   // Sort direction (1=ascending)
		  finalURLStr = finalURLStr + "&DisableCache=" + "true";                      // Cache use flag (true or empty)
		  String url = finalURLStr.toString();
		  
		  
		  task  = new PesqBuddySearchBy(this);
		  
		  task.execute(url);
		 
		  
		  


		  
		  
		 
	}	
	
	*/
	
	public String encodeValue(String rawValue) {
		  String result = null;      
		  try {
		    result = URLEncoder.encode(rawValue, "UTF-8") 
		                        .replaceAll("\\%28", "(")
		                        //.replaceAll("\\%25", "%")		                        
		                        .replaceAll("\\%29", ")")
		                        .replaceAll("\\+", "%20")
		                        .replaceAll("\\%27", "'")
		                        .replaceAll("\\%21", "!")
		                        .replaceAll("\\%7E", "~")
		                        .replaceAll("\\%0A", "") 	                        
		                        .replaceAll("\\%2A", "*");
		  } catch (UnsupportedEncodingException e) {
		    // Handle the error
		  }
		  return result;
		}  
	
	
	
	protected void PegaLinhaSP(String linha)
	{		
		
	 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.pesqlinha));
				
	String monta = linha.replaceAll(" ", "%20");
		
	 String url = "http://api.olhovivo.sptrans.com.br/v0/Linha/Buscar?termosBusca="+monta;    
	 PesqLinhaSPTrans task3 = new PesqLinhaSPTrans(this);
	 task3.execute(url);
	 
	 
	}
	
	
	
	
	
	
	
	
	
	
	/*
	
	
	 public void onPegaDadosSearchByComplete(List<BuddySearchNearBy> lista) {
	       // dismiss the progress dialog
	     

		     listalinhas.clear();
		 			 
			 BuddySearchNearBy buddy = new BuddySearchNearBy();
			 buddy = lista.get(0);	
             List<Datum> dados;
             dados = buddy.getData();

           

             
            if (dados.size() == 0) 
            {
            	
            	 if(dialog != null)  dialog.dismiss();
            	  mostra();
            	Toast.makeText(this, "Nenhum dado retornado para esta pesquisa: "+pesquisar, Toast.LENGTH_LONG).show(); 
            	return;
            	
            }
             
             
     		for (int i = 0; i < dados.size(); i++)
     		{
   
		     		Datum app = new Datum();	
		     		app = dados.get(i);
		     		 String chave =  app.getMetaKey();
		             if (chave.indexOf("trip_") >= 0)
		        	   {         
		               String linha = chave.substring(5,12);	 
		        	   enviar.add(app);
		        	   PegaLinhaSP(linha);
				        	   
		               }
     		}             
             
            
     		
     		try 
    	    {
			Thread.sleep(1000);
		    } catch (InterruptedException e) 
		    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
     		
     		
     		
     		
     		
     		
     		enviar.clear();
     		
     		
     		for (int i = 0; i < listalinhas.size(); i++)
     		{
     			
     			
     			LinhasNomes nomes = new LinhasNomes();
     			nomes = listalinhas.get(i);
     			Datum dado = new Datum();
     			dado.setMetaKey(nomes.getLinhatipo());
     			dado.setMetaValue(nomes.getLetreiro1()+" "+nomes.getLetreiro2());
     			enviar.add(dado);
   
     		}
     		
     		
     		
             AdapterSearchLinhas adapter = new AdapterSearchLinhas(this, enviar);
             adapter.notifyDataSetChanged();         
             setListAdapter(adapter);  
             if(dialog != null)  dialog.dismiss();
             mostra();

             
		      
             
        		 
		 
		 
	           
	   }   
	   
	   */
	
	 
	 public void esconde()
	   {
			
			
		 imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			     
		   
	   }	 
	 
	 
	   
   public void mostra()
   {

	   //imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
	   
   }

 

/*
	public void onPegaDadosSearchByFailure(String msg) {
		 if(dialog != null)  dialog.dismiss();
        
       
		 mostra();
       	Toast.makeText(this, "Problema na conexão para esta pesquisa. Tente masi tarde!", Toast.LENGTH_LONG).show(); 
       	return;
 	   }   
	*/
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	

	
	
	
	/*
	private class PegaFSTask extends AsyncTask<String, Integer, String> {
	     
		   public PegaFSTask(MostraSearchLinhasActivity mostraSearchLinhasActivity) {
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
		    	   JSONObject jArray = null;
		    	   JSONArray  jfotos = null;
		    	   JSONObject jsonR  = null;
		    	   JSONArray resultArray = null;
		    	   
		    	   String id = null;   
		    	   
		    	if(sJson == null) {
		            return;
		        }        
		         
		        
		    	String four = null;
		    
		    	
		        
		      	
		    	try
		        {   
		        four =(String) sJson.toString();
		  		jArray = new JSONObject(four);
		  		resultArray = jArray.getJSONArray("BuscaLinhasSIMResult");
		    	  } catch (JSONException e) {
		              System.err.println("erro 1");
		              Toast.makeText(MostraSearchLinhasActivity.this, "Nenhum dado retornado para esta pesquisa: "+pesquisar, Toast.LENGTH_LONG).show(); 
		             // dialog.dismiss();
		              return;
		        } 
    		 
    		 
    		
	  			 try
	  			 {
	  				 
	  				 
	  				for (int i = 0; i < resultArray.length(); i++)
	  	     		{
	  	     			
	  					   JSONObject det = resultArray.getJSONObject(i);						  		  
				  		   String letreiro1linha =   det.getString("DenominacaoTPTS");
				    	   String letreiro2linha =   det.getString("DenominacaoTSTP");
				    	   String letreiro       =   det.getString("Letreiro"); 	 
				    	   String tipo           =   det.getString("Tipo"); 
				    	   String sentido        =   det.getString("Sentido"); 
				    	   
				    	   
				    	   
				    	   if (sentido.equals("1"))
				    	   {
				    	   Datum linhasnomes = new Datum();
				    	   linhasnomes.setMetaKey(letreiro+"-"+tipo);
				    	   linhasnomes.setMetaValue(letreiro1linha+" / "+letreiro2linha);
				    	   enviar.add(linhasnomes);
				    	   }
	  	     		
	  	     		
	  	     		}
	  				
	  			 }
	  			 catch (Exception e)
	  			 {
	  				//dialog.dismiss();
	             }			    	
	  				
	  				
	  				try
	  				{
	  	            Gson lista = new Gson();
			        String listagem = lista.toJson(enviar.toArray()).toString();
			        appPrefs.setString("listalinhas", listagem);
	  				}
	  				catch (Exception e)
	  				{
	  					
	  				}
	  				
	  				
	  				
	  				 
	  				 AdapterSearchLinhas adapter = new AdapterSearchLinhas(MostraSearchLinhasActivity.this, enviar);
	  	             adapter.notifyDataSetChanged();         
	  	             setListAdapter(adapter);  
	  	           if(dialog != null)  dialog.dismiss();
	  	             mostra();

			  	 	   
	  	             

	  	             
	  	             
			    	   
			    	   
		  	  
			    	  
			    	 
			    		  
			    	  
			    	  
			    	 
		  
				 
			      
	   
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
	
	
	
	
	
	}

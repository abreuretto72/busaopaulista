package com.abreuretto.busaopaulista;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.abreuretto.busaopaulista.R;
import com.google.android.gms.maps.model.LatLng;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class PegaCidaActivity extends Activity {
    private ProgressDialog dialog;
	  SharedPreferences shared_preferences;
	  SharedPreferences.Editor shared_preferences_editor;
	  
		ListView msgList;
		AdapterView.AdapterContextMenuInfo info;
	  
    String latx = null;
    String lonx = null;
    String passou = null;
    String usertoken = null;
    String msg = null;
    int contaparada = 0;
    int posicaoexcluir = 0;
    
    
    boolean acabou = false;
   ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	HashMap<String, String> listaletreiro = new HashMap<String, String>();
    ArrayList<EventInfo> listaponto = new ArrayList<EventInfo>();
    
 	
	int conta = 0;
	
	public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
	public final static String BUDDY_APPLICATION_NAME     = "busao_sp";       // Get it from Buddy's site
	public final static String BUDDY_APPLICATION_PASSWORD = "31336B44-C31B-4254-87CE-1B61AB69AECE";   // Same as above
	 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_pega_cida);
        msgList = (ListView) findViewById(R.id.MessageList);

        setTitle("Lista dos favoritos");
        
        shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
	    latx      = shared_preferences.getString("lat", "");
	    lonx      = shared_preferences.getString("lon", ""); 
	    usertoken = shared_preferences.getString("UserToken", "");
	    


		cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            alert.showAlertDialog(PegaCidaActivity.this, this.getResources().getString(R.string.interneterror),
            		this.getResources().getString(R.string.interneterrorpede), false);
            return;
        }

	    
        
        PegaFavorito();
        
        
        
        
        
        
	  
       


        
    }
 
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
    {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);		
				
			info = (AdapterContextMenuInfo) menuInfo;
			System.out.println("Reached");
			 
			int id = (int) msgList.getAdapter().getItemId(info.position);			
			System.out.println("id "+msgList.getAdapter().getItem(id));
									
			System.out.println("Msg"+listaponto.get(info.position).getLetreiro());
			
			menu.setHeaderTitle(listaponto.get(info.position).getLetreiro());		
		//	menu.add(Menu.NONE, v.getId(), 0, "Excluir");
			
		//	System.out.println("ID "+info.id);
        //	System.out.println("Pos "+info.position);
        //	System.out.println("Info "+info.toString());
	}
	
	@Override  
    public boolean onContextItemSelected(MenuItem item) 
	{
        if (item.getTitle() == "Excluir") {
        	System.out.println("Id "+info.id);
        	System.out.println("Pos "+info.position);
        	System.out.println("info "+info.toString());
        	}  
        
        else 	{
        	return false;
        	}  
    return true;  
    }
	
	
	
	public class CustomAdapter extends BaseAdapter 
	{

	    private ArrayList<EventInfo> _data;
	    Context _c;
	    
	    CustomAdapter (ArrayList<EventInfo> data, Context c)
	    {
	        _data = data;
	        _c = c;
	    }
	   
	    public int getCount() 
	    {
	        // TODO Auto-generated method stub
	        return _data.size();
	    }
	    
	    public Object getItem(int position) 
	    {
	        // TODO Auto-generated method stub
	        return _data.get(position);
	    }
	
	    public long getItemId(int position) 
	    {
	        // TODO Auto-generated method stub
	        return position;
	    }
	   
	    public View getView(final int position, View convertView, ViewGroup parent) 
	    
	    {
	        // TODO Auto-generated method stub
	         View v = convertView;
	         if (v == null) 
	         {
	            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.mapafavorito, null);
	         }

	         EventInfo app = _data.get(position);
	         if(app != null) 
	         {
	         	
	             TextView titleText = (TextView)v.findViewById(R.id.txtTitulo);
	             LinearLayout ratingCntr = (LinearLayout)v.findViewById(R.id.ratingCntr);
	             TextView edText = (TextView)v.findViewById(R.id.txtPonto);            
	             titleText.setText(app.getLetreiro());              
	             edText.setText(app.getPonto());
	             edText.setTextSize(12);
	                 
	             
	         }              		
	        
	         
	         v.setTag(String.valueOf(position));
	         //add listener to the button also and also to the row view
	         v.setOnClickListener( new View.OnClickListener()
	         {
	                 public void onClick(View v)
	                 {
	                 	int pos = Integer.parseInt(v.getTag().toString());
	                 	EventInfo appw = _data.get(pos);
	     		    	String cod = appw.getPonto();
	     		    	String parada = appw.getParadacod();
	     		    	String sentido = "1";
	     		    	String latparada = Double.toString(appw.getaLat());
	     		    	String lonparada = Double.toString(appw.getaLon());
	     		        Intent nextScreen = new Intent(PegaCidaActivity.this,MostraLinhaActivity.class);
	     		        nextScreen.putExtra("parada", "" + parada);
	      		        nextScreen.putExtra("linha", "" + appw.getId());
	      		        nextScreen.putExtra("carro", "1");
	      		        nextScreen.putExtra("nome", "" + appw.getPonto());
	      		        nextScreen.putExtra("latparada", "" + latparada );
	      		        nextScreen.putExtra("lonparada", "" + lonparada );
	      		        startActivity(nextScreen);
	                    }
	          });
	        
	        
	        v.setOnLongClickListener(new OnLongClickListener() {
				
				public boolean onLongClick(View v) 
				{
					// TODO Auto-generated method stub
					AlertDialog.Builder adb=new AlertDialog.Builder(PegaCidaActivity.this);
			        adb.setMessage("Excluir este favorito?");
			        adb.setNegativeButton("Cancel", null);
			        final int selectedid = position;
			        final String itemname= (String) _data.get(position).getLetreiro();
			        final String linha = (String) _data.get(position).getId();
			        final String parada = (String) _data.get(position).getParadacod();
			        
                    
			        adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
			        	public void onClick(DialogInterface dialog, int which) {
			        				
			        		System.out.println("Select "  + selectedid);
			        		System.out.println("Project " + itemname);
			        		
			        		
			        		RemoveFavorito("favorito_"+linha+"_"+parada, selectedid);
			        		
			    			
			    			
			        		}});
			        
			        adb.show();
			        return false;
				}
				
		        });
	        
	        return v; 
	}
	}

    public void RemoveFavorito(String id, int pos)
    {
    	
    	dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.excluifavoritos));	
    	
	String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_UserMetaDataValue_Delete";             //  API Call 
	  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME); 
	  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
	  finalURLStr = finalURLStr + "&userToken=" + encodeValue(usertoken);                   // Logged-in user’s user-token
	  finalURLStr = finalURLStr + "&MetaKey=" + encodeValue(id);                          // Metadata key
	      
	  //favorito_875M_440016922
	  
	  
  	  String url = finalURLStr.toString();
  	  RemoveFavo pontotask = new RemoveFavo(this);
	  pontotask.execute(url,latx,lonx);
	  posicaoexcluir = pos;
		 
	   
		 
  	}

  
  
  
  
  private class RemoveFavo extends AsyncTask<String, Integer, String> {
	     
		   public RemoveFavo(PegaCidaActivity pegaCidaActivity) {
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

	     protected void onPostExecute(String sJson) 
	     {
	    	 
	    	 if (sJson.indexOf("-1") >= 0) {
	    		 
	    		 Toast.makeText(PegaCidaActivity.this, "Problema na exclusão deste favorito. Tente mais tarde.", Toast.LENGTH_LONG).show();  
	    		 
	    	 } else
	    	 {
	    		 listaponto.remove(posicaoexcluir);	
	    		 
	    		 initView();
	    	 }
	    	 
	    	 dialog.dismiss();
	     }
	     
	     
	     
	     }

	

	     
  public class CustomComparator implements Comparator<EventInfo> {
	    @Override
	    public int compare(EventInfo o1, EventInfo o2) {
	        return o1.getDataHora().compareTo(o2.getDataHora());
	    }
	}

    


    
    private void initView() 
    {
    	
    	if (listaponto.size() == 0) {
    		
    		Toast.makeText(PegaCidaActivity.this, "Nenhum favorito na lista.", Toast.LENGTH_LONG).show(); 
    		finish();
    		
    		return;
    		
    	}
    	
    Collections.sort(listaponto, new CustomComparator());
    Collections.reverse(listaponto);
    	
    	
    	
   	msgList.setAdapter(new CustomAdapter(listaponto , this));
		registerForContextMenu(msgList);		
		msgList.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				   System.out.println("Name: "+listaponto.get(position).getLetreiro());
				   String s =(String) ((TextView) v.findViewById(R.id.txtPonto)).getText();
				   Toast.makeText(PegaCidaActivity.this, s, Toast.LENGTH_LONG).show();  
			   }
	   });	
     
   }
   

    
    
    public void UserAccount_Profile_DeleteAccount(String userID) 
    {
    	  String finalURLStr = BUDDY_SERVICE_URL + "?UserAccount_Profile_DeleteAccount";  // API Call 
    	  finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME); 
    	  finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
    	  finalURLStr = finalURLStr + "&UserProfileID=" + userID;                        // The user ID to be deleted
    	
    }

    public void PegaFavorito() 
    {
    	 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregafavoritos));
     	
	      
	    String finalURLStr = BUDDY_SERVICE_URL + "?MetaData_UserMetaDataValue_Search";//API Call
	    finalURLStr = finalURLStr + "&BuddyApplicationName=" + encodeValue(BUDDY_APPLICATION_NAME);
	    finalURLStr = finalURLStr + "&BuddyApplicationPassword=" + encodeValue(BUDDY_APPLICATION_PASSWORD);
	    finalURLStr = finalURLStr + "&UserToken=" + encodeValue(usertoken);         // Logged-in user’s user-token
	    finalURLStr = finalURLStr + "&SearchDistance=-1";       // Search distance
	    finalURLStr = finalURLStr + "&Latitude=-1" ;       // Location latitude 
	    finalURLStr = finalURLStr + "&Longitude=-1";     // Location longitude 
	    finalURLStr = finalURLStr + "&RecordLimit=" + "500";             // Record limit 
	    finalURLStr = finalURLStr + "&MetaKeySearch=" + encodeValue("%favorito_%");          // Search key
	    finalURLStr = finalURLStr + "&MetaValueSearch=";       // Search value
	    finalURLStr = finalURLStr + "&TimeFilter=-1";            // Time in minutes since last update or -1
	    finalURLStr = finalURLStr + "&SortValueAsFloat=0";             // Sort as numeric value
	    finalURLStr = finalURLStr + "&SortDirection=1";               // Sort direction (1=ascending)
	    finalURLStr = finalURLStr + "&DisableCache=false";         // Cache use flag (@"true" or @"false")
	    String url = finalURLStr.toString();
	    PegaFavorito favorito = new PegaFavorito(this);
	    favorito.execute(url,latx,lonx);
	  }
    
    
    
    private class PegaFavorito extends AsyncTask<String, Integer, String> {
	     
		   public PegaFavorito(PegaCidaActivity pegaCidaActivity) {
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
		            	dialog.dismiss();
		                result1 = null;
		            }
		            InputStream isF = entityF.getContent();
		            String convF = streamToString(isF);
		           	result1 = convF;
		        }
		        catch(IOException e){
		        	System.err.println("erro 33");
		        	dialog.dismiss();
		            result1 = null;
		        }
		        return result1;    
	     }


		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(String sJson) 
	     {
	    	   JSONObject jArray = null;
	    	   JSONArray resultArray = null;
	    	 
	    	 try
		        {   
		        String four =(String) sJson.toString();
		  		jArray = new JSONObject(four);
		  		resultArray = jArray.getJSONArray("data");
		    	  } catch (JSONException e) {
		    		  dialog.dismiss();
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
					  		  
					  		  String linhaparada  = null;
					  		  String linha  = null;
					  		  String parada = null;
					  		  
					  		  int sentidolinha = 0;
					  		  int seq = 0;
					  		  
					  		
					  		  linhaparada = det.getString("metaKey");
					    	  linha       = linhaparada.substring(9, 16);
					    	  parada      = linhaparada.substring(17, linhaparada.length());
					  		  String nome = det.getString("metaValue");
					  		  int achou = nome.indexOf("__");
					  		  String nomeparada = nome.substring(0,achou);
					  		  String nomelinha = nome.substring(achou+2,nome.length());
					  		  
					  		  
					  		  
					  		  Double latm = det.getDouble("metaLatitude");
					  		  Double lonm = det.getDouble("metaLongitude");
					  		  Double dista = det.getDouble("distanceInMeters");
					  		  
					  		  String data = det.getString("lastUpdateDate");
					  		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
					  		  
					  		  Date dataHora = sdf.parse(data);  

					  		  
					  		  
					          NumberFormat nf = NumberFormat.getNumberInstance();
					          EventInfo appM = new EventInfo();
			                  appM.setSentidolinha(sentidolinha);
				              appM.setSeqparada(seq);
				              appM.setDataHora(dataHora);
				      	
			  	        
					            LatLng latLng = new LatLng(latm , lonm);					  		 	
					  		 	appM.setaLat(latm);
					  	        appM.setaLon(lonm);
					  	        appM.setDistyou(dista);
					  	        appM.setId(linha);
					  	        appM.setPonto(nomeparada);
					  	        appM.setLatLong(latLng);        
					  	        appM.setParadacod(parada);
					  	        appM.setSentido(1);
					  	        appM.setSentidolinha(1);
					  	        appM.setLetreiro(nomelinha);
					  	        
				  	        
				  	        
				  	        	
				  	        	listaponto.add(appM);
				  	        	// PegaPontoParadaUrl("http://200.189.189.54/InternetServices/BuscaLinhasSIM?termosBusca="+linha);
				  	    
			  	        
			  	        
			  			  } 
			  			  catch (Exception ex) 
			  			  {
			  				dialog.dismiss();
			  				 System.err.println("erro 3");
			  				//dialog.dismiss();
			  			  }
			  		
			    	  
			    	  
			    	  
				 } // for
		     
	
			    	  
			    	  
			    	  
		     }// if
			      
				  dialog.dismiss();  
		    	  MontaParada();

	}//proc
	
	
	   

    
    
    
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
	

    
    
    
    
    
    
    	public void MontaParada()
    	{
    		
    		 	 
    		 
    		 try {
 				Thread.sleep(1500);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
    		 
    		 
    		 initView();  
    		 
    		 	
    	}
    
    
    	protected void PegaPontoParadaUrl(String url)
		{				
			// dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.carregandoplaces));				 
    		PegaPontoParada pontoparada2 = new PegaPontoParada(PegaCidaActivity.this);
			 pontoparada2.execute(url,latx,lonx);
		}	
    
    	private class PegaPontoParada extends AsyncTask<String, Integer, String> {
		     
			   public PegaPontoParada(PegaCidaActivity pegaCidaActivity) {
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
			  		resultArray = jArray.getJSONArray("BuscaLinhasSIMResult");
			    	  } catch (JSONException e) {
			              System.err.println("erro 1");
			            //  dialog.dismiss();
			              return;
			        } 
	    		 
	    		 
	    		
		  			 try
		  			 {
		  		    
				  	 	   JSONObject det = resultArray.getJSONObject(0);						  		  
				  		   String letreiro1linha =   det.getString("DenominacaoTPTS");
				  		   String letreiro2linha =   det.getString("DenominacaoTSTP");
				    	   String linha =    det.getString("Letreiro");
				    	   
				    	   
				    	   AtualizaLinha(linha, letreiro1linha+" "+letreiro2linha );
				    	   
				    	   
				    	   //listaletreiro.put(linha, letreiro1linha+" "+letreiro2linha);
				    	   
                           
				  		  
		  			 }
		  			 catch (Exception e)
		  			 {
		  				
		             }
		}
		

    
    	}
    
			
    	public void AtualizaLinha(String linha, String letreiro)
		   {
			  
			   for(int h=0; h < listaponto.size() ; h++) 
		    	  {
					EventInfo objeven = listaponto.get(h);				
					
						String chave = objeven.getId();
						if (chave.equals(linha)) 
						   {
							objeven.setLetreiro(letreiro);						
							listaponto.set(h, objeven);
							return;
							}				}
					
		   }		
    	
    }			

    


   
   
    	
    	
    
    	
		
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

	
	     
		getMenuInflater().inflate(R.menu.pega_cida, menu);


		return true;
	}
	
	


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
	
	
	
	
	 @Override
	  protected void onResume() {
	    super.onResume();
	    
	    
	    
	    
	  }

	  
	  @Override
	  protected void onPause() {
	    super.onPause();
	    
	    acabou = true;
	    
	   
	   
	  }
   
	
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

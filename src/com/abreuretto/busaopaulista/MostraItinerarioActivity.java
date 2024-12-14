package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import com.abreuretto.busaopaulista.R;
import com.google.android.gms.maps.GoogleMap;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MostraItinerarioActivity extends Activity   {

	
	 String cod = "";

	
	SharedPreferences shared_preferences;
	SharedPreferences.Editor shared_preferences_editor;
	ConnectionDetector cd;
	ProgressDialog dialog;
	AlertDialogManager alert = new AlertDialogManager();
	GoogleMap googleMap;
	//Button novo;
    
	String latx = null;
    String lonx = null;
    String latxflip = null;
    String lonxflip = null;
    String msg = null;
    String letreiro1linha = null;
    String letreiro2linha = null;
    String sentidoparada = null;    
    String paradacodw = null;
    String paradacod = null;
    String paradanome = null;
    String codparada = null;
    String distanciaw = "500";
    String linha_clicada = null;
	 
    int conta;
	int registros = 0;
	int contaparada = 0;
    int sentido  = 0;
	
    boolean pontolinha = false;
	boolean flip = true;
	boolean terminou = false;
	boolean semonibus_w = false;
	boolean selecionado_w = false;
	double lathx = 0; 
	double lonhy =  0;
	    	 
	String[] listaida = null;
	String[] listavolta = null;

	ArrayList<String> listageral = new ArrayList<String>(); 
	
	


    
	private WebView web; 
	boolean prima = true;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_itinerario);
		 
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
		                	   MostraItinerarioActivity.this.finish();
		                   }
		               });
		             builder.create().show();  
		             
		           return;
		
	        	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		web  = (WebView) findViewById(R.id.webView1);
		 setUpWebView();
		
		 
		 
		 Inicia();
		
		
	}

	
	
	
	
	
	
	
	

	public void PoeTab()
	{
		
		
		
		/*
		
		
		for(int i=0;i < listaida.length; i++) 
        {
			listageral.add(listaida[i]);
        }
		for(int i=0;i < listavolta.length; i++) 
        {
			listageral.add(listavolta[i]);
        }
	    
		
		
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_single_choice, listageral);
	        setListAdapter(adapter);
	        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		
		
		
		
		/*
		
		     AdapterItinerario adapter = new AdapterItinerario(MostraItinerarioActivity.this, listageral);
             adapter.notifyDataSetChanged();         
             setListAdapter(adapter);  
          
             */
             
             
             dialog.dismiss();
            
	
		
		
	
		
		
		
		
			
		
		
	}
	
	
	




	
	
	
	

	
	public void Inicia()
	{		
		
		PegaShared();
		PegaBundle();
	
	    
		 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.itinerario));
        setTitle("Itinerário da linha informada: "+linha_clicada);
  	
	}
	
	public void PegaShared()
	{
		
		shared_preferences = getSharedPreferences("abreuretto", MODE_PRIVATE);
	    latx 			=   shared_preferences.getString("lat", "");
	    lonx 			=   shared_preferences.getString("lon", ""); 
	  
	}
	

	public void PegaBundle()
	{
		
		    Bundle extras = getIntent().getExtras();
		    linha_clicada = extras.getString("cod_linha");
		    prima = true;
		    String URL ="http://200.99.150.170/PlanOperWeb/linhaselecionada.asp?Linha="+linha_clicada+"&PPD=0&endereco=&numero=&numero_fim=";
		    String URLX = URL.replaceAll(" ", "%20");
		    web.loadUrl(URLX);
	}
	
	
	
	
	
	
	
	
	 private void setUpWebView() {
			WebSettings webSettings = web.getSettings();
			webSettings.setJavaScriptEnabled(true);
			web.setWebViewClient(client);
			web.addJavascriptInterface(iface, "droid");
		}
	 
	    JIFace iface = new JIFace();
	    WebViewClient client = new MyClient();
	    class MyClient extends WebViewClient {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
					String ht = "javascript:window.droid.print(document.getElementsByTagName('html')[0].innerHTML);";
					web.loadUrl(ht);
			}
		}
	    class JIFace {
			public void print(String data) {
				
				
				
				if (prima == true)
				{
				int achou = data.indexOf("http://olhovivo.sptrans.com.br/linha/"+linha_clicada);
				String pega = data.substring(achou-300, achou);
				int achou2 = pega.indexOf("CdPjOID=");
				String valor = pega.substring(achou2+8, achou2+20); 
				
			  
				    
			         for(int i=0;i<valor.length(); i++) 
			         {
			           char c = valor.charAt(i);
			           
			           if (c == '\"') 
			           {
			           	break;	
			            }
			           
			           if ((c>='0' && c<='9')) 
			           {
			        	   cod = cod + c;
			           }
			         }
				 data ="<html>"+data+"</html>";
				 System.out.println(data);
			 
				  prima = false;
				 
				 // web.setVisibility(View.VISIBLE);
				  web.loadUrl("http://200.99.150.170/PlanOperWeb/detalheLinha.asp?TpDiaID=0&CdPjOID="+cod);
				 

				}  else
				{
					String dados = null;
					dados = ("<html>"+data+"</html>");
					PegaHtml(dados);
				    }
				}	
			}
		
	
	
	
	    
		public void PegaHtml(String dados)
		{
			
			
			String monta = dados;
			
			 
			
			int ini = monta.indexOf("<li class=\"itemTitulo\">Ponto inicial</li>");
			int fim = monta.indexOf("<li class=\"itemTitulo\">Ponto Final</li>");
			int ult = monta.indexOf("<div id=\"footer\">");
			
			
			String inicial = null;
			String voltando = null;
			
			
			if (fim != -1)
			{
			 inicial = monta.substring(ini, fim-1);
			 voltando = monta.substring(fim, ult-1);
			}
			else
			{
			 inicial = monta.substring(ini, ult-1);
			}
			
			
	        String arr[] = inicial.split("<td width=\"317\">");
	        String arr2[] = inicial.split("<td width=\"140\">");
	        listaida = new String[arr.length];
	        	
	        
	        
	        for(int i = 1; i < arr.length; i++){
	        	
	        	
	        	String ruax = arr[i].trim();
	        	int acha = ruax.indexOf(System.getProperty("line.separator"));
	        	String rua = ruax.substring(0,acha);
	        	
	        	String numx = arr2[i].trim();
	        	int acha2 = numx.indexOf(System.getProperty("line.separator"));
	        	String num = numx.substring(0,acha2);
	        	
	        	listaida[i] = (rua+" "+num);
	            
	        }

	        if (fim != -1)
			{
	        	
	        	
	        	String arrv[] = voltando.split("<td width=\"317\">");
	  	        String arrv2[] = voltando.split("<td width=\"140\">");
	  	        listavolta = new String[arrv.length];
	        	
	  	        
	  	        for(int i = 1; i < arrv.length; i++){
	  	        	
	  	        	
	  	        	String ruax = arrv[i].trim();
	  	        	int acha = ruax.indexOf(System.getProperty("line.separator"));
	  	        	String rua = ruax.substring(0,acha);
	  	        	
	  	        	String numx = arrv2[i].trim();
	  	        	int acha2 = numx.indexOf(System.getProperty("line.separator"));
	  	        	String num = numx.substring(0,acha2);
	  	        	
	  	        	listavolta[i] = (rua+" "+num);
	        	
	        	
	  	        }
	        	
	  	        
	  	        
	  	      
	  	        
	        	
			}
			
			
	        
	        
	        
	        if (listavolta.length > 0)
  	        {
  	        	
  	        	listavolta[0] = "------ PONTO FINAL ------";
  	        	
  	        }
  	        
  	        
  	        if (listaida.length > 0)
  	        {
  	        	
  	        	listaida[0] = "------ PONTO INICIAL ------";
  	        	
  	        }
	        
	        PoeTab();
	        
			
			
		}
	    
	    
	    
	    
	    
	       
	    
	    
	    
	    
	    
  	    
	    
	    
	    
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_itinerario, menu);
		return true;
	}

}

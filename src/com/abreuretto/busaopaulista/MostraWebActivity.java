package com.abreuretto.busaopaulista;

import java.io.File;
import java.util.Locale;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import com.abreuretto.busaopaulista.R;

public class MostraWebActivity extends Activity {

	String tipo = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_web);
		
		
		
		Bundle extras = getIntent().getExtras();
	    tipo = extras.getString("tipo");
		
		
		
		
		 WebView lWebView = (WebView)findViewById(R.id.webView1);
		 // File lFile = new File("assets/politica_privacidade_en-US.html");
		 
		 Locale l = Locale.getDefault();

		 String lang = l.getISO3Language().toString();
		 
		 
		 
		 
		 
		 
		// lWebView.loadUrl("curl --cookie sptrans_cookies http://olhovivo.sptrans.com.br/v0/Parada/Buscar?termosBusca=paulista");

		 
		 
		
		 
		 
		 if (tipo.equals("1"))
			{
     		  lWebView.loadUrl("file:///android_asset/politica_privacidade_pt-BR.html");
			}
		
		 if (tipo.equals("2"))
			{
  		      lWebView.loadUrl("file:///android_asset/ajuda_pt-BR.html");
			}
		
		
		
		
		
		
		 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_web, menu);
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
	

	

}

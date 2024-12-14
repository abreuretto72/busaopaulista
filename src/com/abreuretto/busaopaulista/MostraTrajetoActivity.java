package com.abreuretto.busaopaulista;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.abreuretto.busaopaulista.R;

public class MostraTrajetoActivity extends Activity {

	
	 ConnectionDetector cd;
	  private ProgressDialog dialog;
	  AlertDialogManager alert = new AlertDialogManager();
	  HelperSharedPreferences appPrefs = null;
	
	    String latx = null;
	    String lonx = null;
	    String ende = null;
	    long unixTime = 0;
		   
	    String p_origem = null;
	    String p_destino = null;
	    EditText eorigem;
	    EditText edestino;
	    
	    
	    
	
	    InputMethodManager imm ;
	    
	
	    
	    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_trajeto);
		
		
		 cd             = new ConnectionDetector(getApplicationContext());
		 appPrefs       = new HelperSharedPreferences(getApplicationContext());

		
			imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
	
			
			
			
			
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
			                	   MostraTrajetoActivity.this.finish();
			                   }
			               });
			             builder.create().show();  
			           return;
			           
				   
			   }	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		 
		 
        PegaPreferences();
        Iniciar();
		
		
		
		
		
		
		
		
		
	}

	
	
	
	
	 	
	
	
	 public void Iniciar()
	   {
		   
	      
	       
	      
	  	   eorigem  = (EditText)findViewById(R.id.e_origem);
	  	   edestino = (EditText)findViewById(R.id.e_destino);
	       eorigem.setText(ende);
	       
	       
	  	   
		   
	   }
	
		 public void PegaPreferences()
		   {
			   latx      =     appPrefs.getString("lat", "");
			   lonx      =     appPrefs.getString("lon", "");
			   ende      =     appPrefs.getString("endereco", "");
		   }
		 
		 
		 
		 
		 
		 
		 
		
		
	
	
		 public void esconde()
		   {
				
				
			 imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				     
			   
		   }	 
	
	
			
			public void Clicou(View v)
			{
				
				

				p_origem  = eorigem.getText().toString();
				p_destino = edestino.getText().toString();
				
				
				
				
				
				
				
				if (p_origem.trim().length() > 2)
				{
		
				
				} else
				{
					if ((p_origem.trim().length() > 0))
							{
					Toast.makeText(this, "Informe no mínimo 3 caracteres!", Toast.LENGTH_LONG).show();
							}
					
					return;
					
				}

				if (p_destino.trim().length() > 2)
				{
				
					esconde();
				
				} else
				{
					if ((p_origem.trim().length() > 0))
							{
					Toast.makeText(this, "Informe no mínimo 3 caracteres!", Toast.LENGTH_LONG).show();
							}
					
					return;
				}
				
				
				
				if (p_origem.indexOf("São Paulo") == -1)
				{
					
					p_origem = p_origem + ", São Paulo";
					
				}
				
				if (p_destino.indexOf("São Paulo") == -1)
				{
					
					p_destino = p_destino + ", São Paulo";
					
				}
				
				
				p_origem = p_origem.replaceAll("  ", " ");
				p_destino = p_destino.replaceAll("  ", " ");
				
				
				
				
				String Eo = p_origem.trim().toString();
				String Ed = p_destino.trim().toString();
				
				
		    	Intent nextScreen = new Intent(MostraTrajetoActivity.this, MostraTrajetoListaActivity.class);
		        nextScreen.putExtra("origem", "" + Eo);
		        nextScreen.putExtra("destino", "" + Ed);
		        startActivityForResult(nextScreen, 0);
			
				
				
				
				
				
	
				
				
				
				
			}
			
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_trajeto, menu);
		return true;
	}

}

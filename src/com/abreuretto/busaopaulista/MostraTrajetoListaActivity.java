package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import java.util.List;

import com.abreuretto.busaopaulista.R;
import com.abreuretto.rotas.AdapterTrajetos;
import com.abreuretto.rotas.Leg;
import com.abreuretto.rotas.PegaDadosTrajeto;
import com.abreuretto.rotas.PegaDadosTrajetoListener;
import com.abreuretto.rotas.RotasGoogle;
import com.abreuretto.rotas.Route;
import com.abreuretto.rotas.Step;
import com.abreuretto.rotas.Transit_details;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MostraTrajetoListaActivity extends ListActivity implements PegaDadosTrajetoListener{

	

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
	    
	    
	    PegaDadosTrajeto task;
	    
	
	    InputMethodManager imm ;
	    
	    Trajeto traj3;
	    
	    ArrayList<Trajeto> listatrajeto = new ArrayList<Trajeto>();
	    
	    String Eo;
	    String Ed;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_trajeto_lista);
		
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
			                	   MostraTrajetoListaActivity.this.finish();
			                   }
			               });
			             builder.create().show();  
			           return;
			           
				   
			   }	

			
			
			
			
			
			
			
			
			
			
			
			
		 
		    Bundle extras = getIntent().getExtras();
		    Eo = extras.getString("origem");	
		    Ed = extras.getString("destino");	
		    
			
			
		 
        PegaPreferences();
        Iniciar();
		
		
		
	}

	
	
	public void onPegaDadosTrajetoComplete(List<RotasGoogle> lista) {
	       // dismiss the progress dialog
	     
		
		if (lista.size() == 0)
		{
			
			   Toast.makeText(MostraTrajetoListaActivity.this, "Nenhum dado retornado para esta pesquisa: ", Toast.LENGTH_LONG).show(); 
	           return;
			
		}
		
		
		 
		 RotasGoogle rotap =  new RotasGoogle(); 
				rotap = lista.get(0);
		 List<Route> rotas =  rotap.getRoutes();
		 
		 for (int i = 0; i < rotas.size(); i++)
		{
			Route rota =  rotas.get(i);
		    List<Leg> legs =  rota.getLegs();
		    for (int j = 0; j < legs.size(); j++)
		    {
		    	
		    	Leg leg = legs.get(j);
		    	
		    	
		    	String inicio = leg.getStart_address();
		    	Trajeto traj2 = new Trajeto();
		    	traj2.setOrigem(inicio);
		    	
		    	traj2.setTipo(0);
		    	int imageResource = R.drawable.p_ini;
                traj2.setIcon(imageResource);
 			 
		    	
		    	listatrajeto.add(traj2);
		    	
		    	
		    	    String fim = leg.getEnd_address();
		    	    String dista = leg.getDistance().getText().toString();
		    	    String dura  = leg.getDuration().getText().toString();
		    	    
			    	traj3 = new Trajeto();
			    	traj3.setOrigem(fim);
			    	
			    	traj3.setDuracao(dura);
			    	traj3.setDistancia(dista);
			    	traj3.setTipo(3);
			    	int img = R.drawable.p_fim;
	                traj3.setIcon(img);
		    	
		    	
		    	
		    	List<Step> steps = leg.getSteps();
		    	
		    	for (int k = 0; k < steps.size(); k++)
			    {
		    		
		    		Step step = steps.get(k);
		    		
		    		Transit_details transi = step.getTransit_details();
		    		
		    		
		    		Trajeto traj = new Trajeto();
		    		
		    		traj.setDistancia(step.getDistance().getText().toString());
		    		traj.setDuracao(step.getDuration().getText().toString());
		    		
		    		
		    		String tipo = step.getTravel_mode().toString();
		    		if (tipo.equals("WALKING"))
		    		{
		    			traj.setTipo(1);
		    			traj.setInstrucao(step.getHtml_instructions().toString()); 
		    			int img1 = R.drawable.walking;
                     traj.setIcon(img1);
		    			 
		    			
		    			
		    		}
		    		if (tipo.equals("TRANSIT"))
		    		{
		    			
		    			
		    			String veiculo = transi.getLine().getVehicle().getType().toString();
		    			
		    			int img2 = 0;
		    			
		    			if (veiculo.equals("SUBWAY"))
		    			{
		    				
		    				img2 = R.drawable.metro;
	                        traj.setIcon(img2);
		    				
		    				
		    			}  else
		    				
		    			{
		    				
		    				
		    				img2 = R.drawable.b_busidas;
	                        traj.setIcon(img2);
		    				
		    			}
		    			
		    			
		    			traj.setTipo(2);
		    		    String letreiro = 	transi.getLine().getName();
		    		    String linha    =	transi.getLine().getShort_name();
		    		    traj.setInstrucao(linha+" "+letreiro); 
		    		    double latT =   transi.getDeparture_stop().getLocation().getLat();
		    		    double lonT =   transi.getDeparture_stop().getLocation().getLng();
		    		    String nome =    transi.getDeparture_stop().getName();
		    		    String desce =  transi.getArrival_stop().getName().toString();
		    		    
		    		    traj.setId(desce);
		    		    traj.setNomeparada(nome);
		    		    traj.setOrigem(nome);
		    		    traj.setLat(Double.toString(latT));
		    		    traj.setLon(Double.toString(lonT));
		    		    
		    		    
		    		   
		    		   
		    		   
		    			
		    		}
		    		
		    		
		    		listatrajeto.add(traj);
		    		
		    		
		    		 
		    		
			    }
		    	
		    	
		    	
		    	
		    }
		    
		  
	    	
	    	listatrajeto.add(traj3);
		    
		    
		}
		 
		AdapterTrajetos adapter = new AdapterTrajetos(MostraTrajetoListaActivity.this, listatrajeto);
        adapter.notifyDataSetChanged();         
        setListAdapter(adapter);  
        if(dialog != null)  dialog.dismiss(); 
		 
	 }
	
	
	
	
	

	public void onPegaDadosTrajetoFailure(String msg) {
		 if(dialog != null)  dialog.dismiss();
     
    
		
    	Toast.makeText(this, "Problema na conexão para esta pesquisa. Tente masi tarde!", Toast.LENGTH_LONG).show(); 
    	return;
	   }   
	
	
	
	 public void Iniciar()
	   {
		  
		 dialog = ProgressDialog.show(this, "", this.getResources().getString(R.string.buscando_trajeto));
		 
	      
	       
	      
	  	   Clicou();
	       
	       
	  	   
		   
	   }
	
		 public void PegaPreferences()
		   {
			   latx      =     appPrefs.getString("lat", "");
			   lonx      =     appPrefs.getString("lon", "");
			   ende      =     appPrefs.getString("endereco", "");
		   }
		 
		 
		 
		 
		 
		 
		 
		
		
	
	
	
	
	
	
	
	public void Clicou()
	{
		
		
		
		
		
		
		
		unixTime = System.currentTimeMillis() / 1000L;
		String tempo =  Long.toString(unixTime);
		
		String url = "http://maps.googleapis.com/maps/api/directions/json?origin="+Eo+"&destination="+Ed+"&sensor=true&departure_time="+tempo+"&mode=transit&region=br&language=pt_br";
		
		 task  = new PegaDadosTrajeto(this);
		  
		 url = url.replaceAll(" ", "%20");
		 
		 
		 task.execute(url);
		
		
		
		
		
		
		
		

			
		
		
	}

	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_trajeto_lista, menu);
		return true;
	}

}

package com.abreuretto.rotas;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abreuretto.buddysearchnearby.Datum;
import com.abreuretto.busaopaulista.MostraItinerarioActivity;
import com.abreuretto.busaopaulista.R;
import com.abreuretto.busaopaulista.Trajeto;

public class AdapterTrajetos extends ArrayAdapter<Trajeto> {
    private List<Trajeto> items;
    private int selectedPos = -1;
     
    public AdapterTrajetos(Context context, List<Trajeto> items) {
        super(context, R.layout.linhasitems, items);
        this.items = items;
    }
     
   
    
    
    public void setSelectedPosition(int pos){
    selectedPos = pos;
    // inform the view of this change
    notifyDataSetChanged();
    }

    public int getSelectedPosition(){
    return selectedPos;
    }


    
    
    @Override
    public int getCount() {
        return items.size();
    }
    
    
    
    
    
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
         
        
    
        
        if(v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.trajetoitems, null);    
            v.setClickable(true); 
            
           
        }
         
        Trajeto app = items.get(position);
        if(app != null) {
        	
        	
            
            String chave =  app.getInstrucao();
       	 
            int ima = app.getIcon();
            ImageView img =  (ImageView)v.findViewById(R.id.appIcon);
            img.setImageResource(ima);
            
            img.getResources().getDrawable(ima);
       		TextView titleText = (TextView)v.findViewById(R.id.txtTitulo);
            LinearLayout ratingCntr = (LinearLayout)v.findViewById(R.id.ratingCntr);
            LinearLayout ratingCntr2 = (LinearLayout)v.findViewById(R.id.ratingCntr2);
            TextView edText = (TextView)v.findViewById(R.id.txtPonto);   
            TextView  td =  new TextView(getContext());
          	TextView  tt =  new TextView(getContext());          
          	TextView  tf =  new TextView(getContext());
          	
            ratingCntr.removeAllViewsInLayout();
            ratingCntr2.removeAllViewsInLayout();
            
            if ((app.getTipo() == 0) || (app.getTipo() == 3))
            {
            	 String linha = app.getOrigem();
                 String texto = linha.replace(", São Paulo", ""); 
                 String textox = texto.replace(", República Federativa do Brasil", ""); 
            	 titleText.setText(textox);          
                 edText.setText("São Paulo");
                 edText.setTextSize(12);
            	
            }  
            
            
            if (app.getTipo() == 3)
            {
            	td.setText("Total: "+app.getDistancia()+"  ");
              	td.setTextSize(12);
              	td.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
              	tt.setText("Duração: "+app.getDuracao());
              	tt.setTextSize(12);
              	tt.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
            	ratingCntr.addView(td);
              	ratingCntr.addView(tt);
            	
            }  
            
            
            
            
            
            
            
            if ((app.getTipo() == 1))
            {
            	  
           	    //String linha = app.getInstrucao();
           	    String linha = app.getInstrucao();
           	// String linha = app.getOrigem();
             String texto = linha.replace(", São Paulo", ""); 
             String textox = texto.replace(", República Federativa do Brasil", ""); 
           	    titleText.setText(textox); 
           	    edText.setText("São Paulo");
                //edText.setText(linha);
                edText.setTextSize(12);
                
                
                
              	td.setText("Distância: "+app.getDistancia()+"  ");
              	td.setTextSize(12);
              	td.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
              	tt.setText("Duração: "+app.getDuracao());
              	tt.setTextSize(12);
              	tt.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
              	
              	
              	
          		ratingCntr.addView(td);
              	ratingCntr.addView(tt);
              	//ratingCntr.addView(tf);
              	
            	
            }  
            
            
            
            
            
            
            if ((app.getTipo() == 2))
            {
            	
            
            	
            	   
                
           	    String linha = app.getInstrucao();
           	    String valor = app.getOrigem();
           	    titleText.setText(valor);              
                edText.setText(linha);
                edText.setTextSize(14);
                
              
            	tf.setText("Destino: "+app.getId());
              	tf.setTextSize(14);
              	tf.setTextColor(getContext().getResources().getColor(R.color.textoende));
              	
              	td.setText("Distância: "+app.getDistancia()+"  ");
              	td.setTextSize(12);
              	td.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
              	tt.setText("Duração: "+app.getDuracao());
              	tt.setTextSize(12);
              	tt.setTextColor(getContext().getResources().getColor(R.color.texto));
              	
              	
              
              	
          		ratingCntr.addView(td);
              	ratingCntr.addView(tt);	
              	ratingCntr2.addView(tf);	
              	
              	
            	
            }
            

          	
          	
          	

            
            
            
            
            
            
        }
         
       
        

        v.requestFocus();
        
        v.setTag(String.valueOf(position));
        //add listener to the button also and also to the row view
        v.setOnClickListener( new View.OnClickListener()
        {
                public void onClick(View v)
                {
                	int pos = Integer.parseInt(v.getTag().toString());
                	Trajeto appw = items.get(pos);
                	
                	int tipo = appw.getTipo();
                	
                	if (tipo == 2)
                	{
                	
    		    	String cod = appw.getId();
    		    	String linha = cod;
    		    	String parada = appw.getInstrucao();
    		    	String cod_linha = parada.substring(0, 7);    		    	    		    	
    		    	Intent nextScreen = new Intent(getContext(),MostraItinerarioActivity.class);
    		        nextScreen.putExtra("linha", linha);
    		        nextScreen.putExtra("cod_linha", cod_linha);
    		        
     		        getContext().startActivity(nextScreen);
                	}
                   }
                
                
                
         });  
       
       
                
        
        
        
        return v;
       
        }



    
    



}



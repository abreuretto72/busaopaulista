package com.abreuretto.busaopaulista;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.abreuretto.busaopaulista.R;

public class AdapterParadasLinha extends ArrayAdapter<EventInfo> {
    private List<EventInfo> items;
    private int selectedPos = -1;
     
    public AdapterParadasLinha(Context context, List<EventInfo> items) {
        super(context, R.layout.linhas_paradas, items);
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
            v = li.inflate(R.layout.linhas_paradas, null);    
            v.setClickable(true); 
            
           
        }
         
           EventInfo app = items.get(position);
        if(app != null) {
        	
           
            
            String chave =  app.getPonto();
       	
            
            
                   
       		 TextView titleText = (TextView)v.findViewById(R.id.txtTitulo);
             LinearLayout ratingCntr = (LinearLayout)v.findViewById(R.id.ratingCntr);
             TextView edText = (TextView)v.findViewById(R.id.txtPonto);   
             
       	    //String linha = app.getMetaKey();
       	    String valor = app.getLocalizacao();
       	    titleText.setText(chave);              
            edText.setText(valor);
            edText.setTextSize(12);
        }
         
       
        

        v.requestFocus();
        
        v.setTag(String.valueOf(position));
        //add listener to the button also and also to the row view
        v.setOnClickListener( new View.OnClickListener()
        {
                public void onClick(View v)
                {
                	int pos = Integer.parseInt(v.getTag().toString());
                	EventInfo appw = items.get(pos);
    		    	String cod = appw.getLinha();
    		    	String linha = cod;
    		    	String parada = appw.getPonto();
    		    	String paradacod = appw.getParadacod();
    		    	String nome = appw.getPonto();
    		    	Double lat = appw.getaLat();
    		    	Double lon = appw.getaLon();
    		    	int sentido = appw.getSentido();	    	
    		    	
    		    	
    		    	
    		    	
    		    	
    		    	Intent nextScreen = new Intent(getContext(),MapaTrajetoLinha.class);
    		        nextScreen.putExtra("linha", linha);
    		        nextScreen.putExtra("parada", paradacod);
    		        nextScreen.putExtra("nome", nome);
    		        nextScreen.putExtra("latparada", Double.toString(lat));
    		        nextScreen.putExtra("lonparada", Double.toString(lon));
    		        nextScreen.putExtra("sentido", Integer.toString(sentido));
    		        
    		        
     		        getContext().startActivity(nextScreen);
                   }
                
                
                
         });  
       
       
                
        
        
        
        return v;
       
        }



    
    



}


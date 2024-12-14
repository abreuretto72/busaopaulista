package com.abreuretto.busaopaulista;

import java.util.List;

import com.abreuretto.buddysearchnearby.Datum;
import com.abreuretto.busaopaulista.R;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterSearchLinhas extends ArrayAdapter<Datum> {
    private List<Datum> items;
    private int selectedPos = -1;
     
    public AdapterSearchLinhas(Context context, List<Datum> items) {
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
            v = li.inflate(R.layout.linhasitems, null);    
            v.setClickable(true); 
            
           
        }
         
           Datum app = items.get(position);
        if(app != null) {
        	
           
            
            String chave =  app.getMetaKey();
       	
            
            
                   
       		 TextView titleText = (TextView)v.findViewById(R.id.txtTitulo);
             LinearLayout ratingCntr = (LinearLayout)v.findViewById(R.id.ratingCntr);
             TextView edText = (TextView)v.findViewById(R.id.txtPonto);   
             
       	   String linha = app.getMetaKey();
       	   String valor = app.getMetaValue();
       	    titleText.setText(linha);              
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
                	Datum appw = items.get(pos);
    		    	String cod = appw.getMetaKey();
    		    	String linha = cod;
    		    	String parada = appw.getMetaValue();
    		    	String codlinha = appw.getAppTag();

    		    	
    		    	
    		    	//Intent nextScreen = new Intent(getContext(),MostraItinerarioActivity.class);
    		    	
    		    	Intent nextScreen = new Intent(getContext(),MostraParadaLinhaActivity.class);
    		    	
    		        nextScreen.putExtra("linha", linha);
    		        nextScreen.putExtra("codlinha", codlinha);
    		        
     		        getContext().startActivity(nextScreen);
                   }
                
                
                
         });  
       
       
                
        
        
        
        return v;
       
        }



    
    



}


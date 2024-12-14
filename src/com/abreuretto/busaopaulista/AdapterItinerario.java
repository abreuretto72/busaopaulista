package com.abreuretto.busaopaulista;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.abreuretto.busaopaulista.R;

public class AdapterItinerario extends ArrayAdapter<String> {
    private List<String> items;
    private int selectedPos = -1;
     
    public AdapterItinerario(Context context, List<String> items) {
        super(context, R.layout.itinerario, items);
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
            v = li.inflate(R.layout.itinerario, null);    
            v.setClickable(true); 
            
           
        }
         
           String app = items.get(position);
        if(app != null) {
        	
           
            
           
       	
            
            
                   
       		 TextView titleText = (TextView)v.findViewById(R.id.txtTitulo);

             
       	    titleText.setText(app);              
            
        }
         
       
        

        v.requestFocus();
        
        v.setTag(String.valueOf(position));
        //add listener to the button also and also to the row view
        v.setOnClickListener( new View.OnClickListener()
        {
                public void onClick(View v)
                {
                	int pos = Integer.parseInt(v.getTag().toString());
                	String appw = items.get(pos);
    		    	String linha = appw;
    		    	
    		    	

    		    	
    		    	
    		    	Intent nextScreen = new Intent(getContext(),MapaParadaActivity.class);
    		        nextScreen.putExtra("linha", linha);
     		        getContext().startActivity(nextScreen);
                   }
                
                
                
         });  
       
       
                
        
        
        
        return v;
       
        }



    
    



}


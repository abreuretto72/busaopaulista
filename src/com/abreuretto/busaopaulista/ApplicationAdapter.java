package com.abreuretto.busaopaulista;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.abreuretto.busaopaulista.R;
import com.buddy.sdk.Messages;
import com.loopj.android.image.SmartImageView;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class ApplicationAdapter extends ArrayAdapter<EventInfo> {
    private List<EventInfo> items;
    private int selectedPos = -1;
     
    public ApplicationAdapter(Context context, List<EventInfo> items) {
        super(context, R.layout.mapafavorito, items);
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
            v = li.inflate(R.layout.mapafavorito, null);    
            v.setClickable(true); 
            
           
        }
         
           EventInfo app = items.get(position);
        if(app != null) {
        	
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
                	EventInfo appw = items.get(pos);
    		    	String cod = appw.getPonto();
    		    	String parada = appw.getParadacod();
    		    	String sentido = "1";
    		    	String latparada = Double.toString(appw.getaLat());
    		    	String lonparada = Double.toString(appw.getaLon());
    		        Intent nextScreen = new Intent(getContext(),MapaParadaActivity.class);
    		        nextScreen.putExtra("parada", "" + parada);
     		        nextScreen.putExtra("linha", "" + appw.getId());
     		        nextScreen.putExtra("carro", "1");
     		        nextScreen.putExtra("nomeparada", "" + appw.getPonto());
     		        nextScreen.putExtra("latparada", "" + latparada );
     		        nextScreen.putExtra("lonparada", "" + lonparada );
     		        getContext().startActivity(nextScreen);
                   }
         });
        
        
                 
       
       
                
        
        
        
        return v;
       
        }



    
    



}


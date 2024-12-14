package com.abreuretto.busaopaulista;

import java.util.HashMap;
import java.util.List;
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.abreuretto.busaopaulista.R;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private int sentido;
 
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, int sentidow) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.sentido = sentidow;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
 
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        ImageView image1 = (ImageView)convertView
                .findViewById(R.id.appIcon1);
        
        String texto = childText;
        
        
        
        
        if (texto.indexOf("Cadeirante: SIM") >= 0)
        {
        	
        	image1.setVisibility(View.VISIBLE);	
        	
        } 
        
        else
        	
        {
        	
        image1.setVisibility(View.INVISIBLE);	
        	
        }
        
        String text2 = texto.replace("Cadeirante: SIM", "");
        String text3 = text2.replace("Cadeirante: NÃO", "");
        
        
        txtListChild.setText(text3);
        
       
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @SuppressLint("ResourceAsColor")
	@Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            
            
            if (sentido ==1) {
            	convertView.setBackgroundResource(R.color.onibus1);
                }
                else
                {
                	convertView.setBackgroundResource(R.color.onibus2);
                
                }
            
            
            
        }
 
      
        
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
       // lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextSize(14);
        lblListHeader.setText(headerTitle);   
        
        /*
        if (sentido ==1) {
        lblListHeader.setBackgroundColor(R.color.onibus1_color);
        }
        else
        {
         lblListHeader.setBackgroundColor(R.color.onibus2_color);
        
        }
    */
        
       
        
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


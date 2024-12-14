package com.abreuretto.busaopaulista;

import com.abreuretto.busaopaulista.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MostraPosicaoActivity extends FragmentActivity {

	 
	 
	 
	int op = 0;
	
    private static class LongPressLocationSource implements LocationSource, OnMapLongClickListener {
        private OnLocationChangedListener mListener;

  
        private boolean mPaused;

        @Override
        public void activate(OnLocationChangedListener listener) {
            mListener = listener;
        }

        @Override
        public void deactivate() {
            mListener = null;
        }

        @Override
        public void onMapLongClick(LatLng point) {
            if (mListener != null && !mPaused) {
                Location location = new Location("LongPressLocationProvider");
                location.setLatitude(point.latitude);
                location.setLongitude(point.longitude);
                location.setAccuracy(100);
                mListener.onLocationChanged(location);
            }
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }

    }

    private LongPressLocationSource mLocationSource;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_posicao);
    	RadioGroup rg = (RadioGroup)findViewById(R.id.radioLocal);
        
        mLocationSource = new LongPressLocationSource();
        
       
        



	
		op = rg.getCheckedRadioButtonId(); 	
		
		if(op==R.id.rblocal)
		{
			    
		    
		}
		
		if(op==R.id.rboutro)
		{
			
			

	            setUpMapIfNeeded();
			
		}
        
        
        
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        if(op==R.id.rboutro)
		{
			
			//mLocationSource = new LongPressLocationSource();

	        setUpMapIfNeeded();

		}
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        
    	if(op==R.id.rboutro)
    	{
    	
    	if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    	}
    }

    private void setUpMap() {
        mMap.setLocationSource(mLocationSource);
        mMap.setOnMapLongClickListener(mLocationSource);
        mMap.setMyLocationEnabled(true);
        LatLng latLng = null;
		 latLng = new LatLng(Double.parseDouble("-23.550268") , Double.parseDouble("-46.634325"));
		 mMap.addMarker(new MarkerOptions()
	        .position(latLng)
	        .title("Local")
	        .snippet("Praça da Sé")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.eu)));
		 mMap.getUiSettings().setCompassEnabled(true);
		 mMap.getUiSettings().setZoomControlsEnabled(true);
		 mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		 mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        
        
        
        
        
        
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationSource.onPause();
    }
}

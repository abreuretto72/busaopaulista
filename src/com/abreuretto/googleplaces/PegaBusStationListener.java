package com.abreuretto.googleplaces;

import java.util.List;



public interface PegaBusStationListener {

	  public void onPegaBusStationComplete(List<GooglePlaces> data);
	    public void onPegaBusStationFailure(String msg);
	}

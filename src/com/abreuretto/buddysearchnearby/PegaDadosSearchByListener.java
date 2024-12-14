package com.abreuretto.buddysearchnearby;

import java.util.List;


public interface PegaDadosSearchByListener {
    public void onPegaDadosSearchByComplete(List<BuddySearchNearBy> data);	
    public void onPegaDadosSearchByFailure(String msg); 
}


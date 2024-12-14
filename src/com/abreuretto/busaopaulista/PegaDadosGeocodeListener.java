package com.abreuretto.busaopaulista;

public interface PegaDadosGeocodeListener {
    public void onPegaDadosGeocodeComplete(String data);
    public void onPegaDadosGeocodeFailure(String msg); 
}


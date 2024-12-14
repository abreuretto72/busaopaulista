package com.abreuretto.busaopaulista;

import com.google.android.gms.maps.model.Marker;

import android.app.Application;

class GlobalClass extends Application{
	 
    public static Marker marca_w = null;
    public static boolean semonibus_w = false;
    public static boolean selecionado_w = false;
	public static Marker getMarca_w() {
		return marca_w;
	}
	public static void setMarca_w(Marker marca_w) {
		GlobalClass.marca_w = marca_w;
	}
	public static boolean isSemonibus_w() {
		return semonibus_w;
	}
	public static void setSemonibus_w(boolean semonibus_w) {
		GlobalClass.semonibus_w = semonibus_w;
	}
	public static boolean isSelecionado_w() {
		return selecionado_w;
	}
	public static void setSelecionado_w(boolean selecionado_w) {
		GlobalClass.selecionado_w = selecionado_w;
	}
    
    
	
	
   
	

 
}


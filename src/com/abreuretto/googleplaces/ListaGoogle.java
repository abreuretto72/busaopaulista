package com.abreuretto.googleplaces;

import java.io.Serializable;

public class ListaGoogle implements Serializable {

	private static final long serialVersionUID = 44L;

	
	    private String nomeparada;
		private String lat;
	    private String lon;
	    private double distancia;
	    private String codigoparada;
	    private String endereco;
	    private String tipo; //types=subway_station|bus_station|taxi_stand|train_station|airport
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getEndereco() {
			return endereco;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
		public String getCodigoparada() {
			return codigoparada;
		}
		public void setCodigoparada(String codigoparada) {
			this.codigoparada = codigoparada;
		}
		public double getDistancia() {
			return distancia;
		}
		public void setDistancia(double distancia) {
			this.distancia = distancia;
		}
		public String getNomeparada() {
			return nomeparada;
		}
		public void setNomeparada(String nomeparada) {
			this.nomeparada = nomeparada;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getLon() {
			return lon;
		}
		public void setLon(String lon) {
			this.lon = lon;
		}
	    
	    
	    
}

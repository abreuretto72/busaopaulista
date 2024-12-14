package com.abreuretto.busaopaulista;

import java.io.Serializable;



public class Trajeto implements Serializable {

	private static final long serialVersionUID = 19L;
	
	 private String id;
	 private int icon; 
	 private String origem;
	 private String distancia;
	 private String duracao;
	 private String instrucao;
	 private String lat;
	 private String lon;
	 private int parada;
	 private String nomeparada;
	 private int tipo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDistancia() {
		return distancia;
	}
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getInstrucao() {
		return instrucao;
	}
	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
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
	public int getParada() {
		return parada;
	}
	public void setParada(int parada) {
		this.parada = parada;
	}
	public String getNomeparada() {
		return nomeparada;
	}
	public void setNomeparada(String nomeparada) {
		this.nomeparada = nomeparada;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	 
	 
	 
	 
	 

}

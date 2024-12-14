package com.abreuretto.busaopaulista;


import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class EventInfo implements Serializable {

	private static final long serialVersionUID = 10L;
	
	 private String id;
	 private int parada; //0=sem 1=onibus  2=metro 3=trem
	 private int tipo; // 1=onibus, 2=metro, 3=trem, 4=taxi, 5=aviao, 6=bike
	 private String veiculo; //codigo do veiculo
	 private LatLng latLong;
	 private String letreiro;
	 private int sentido;
	 private String previsao;
	 private double aLat;
	 private double aLon;
	 private String ponto;
	 private double distponto;
	 private double distyou;
	 private Date DataHora;
	 private String cadeirante;
	 private String paradacod;
	 private String localizacao;
	 private double aLatold;
	 private double aLonold;
	 private Marker marca;
	 private int sentidolinha;
	 private int seqparada;
	 private String letreiro1;
	 private String letreiro2;
	 private String linha;
	 private String tipolinha;
	 private String tempo;
	 
	 
	 
	 
	 
	 
	 
	 public String getTempo() {
		return tempo;
	}




	public void setTempo(String tempo) {
		this.tempo = tempo;
	}




	public String getTipolinha() {
		return tipolinha;
	}




	public void setTipolinha(String tipolinha) {
		this.tipolinha = tipolinha;
	}




	public String getLetreiro1() {
		return letreiro1;
	}




	public void setLetreiro1(String letreiro1) {
		this.letreiro1 = letreiro1;
	}




	public String getLetreiro2() {
		return letreiro2;
	}




	public void setLetreiro2(String letreiro2) {
		this.letreiro2 = letreiro2;
	}




	public String getLinha() {
		return linha;
	}




	public void setLinha(String linha) {
		this.linha = linha;
	}




	public int getSeqparada() {
		return seqparada;
	}




	public void setSeqparada(int seqparada) {
		this.seqparada = seqparada;
	}




	public int getSentidolinha() {
		return sentidolinha;
	}




	public void setSentidolinha(int sentidolinha) {
		this.sentidolinha = sentidolinha;
	}




	public Marker getMarca() {
		return marca;
	}




	public void setMarca(Marker marca) {
		this.marca = marca;
	}




	public double getaLatold() {
		return aLatold;
	}




	public void setaLatold(double aLatold) {
		this.aLatold = aLatold;
	}




	public double getaLonold() {
		return aLonold;
	}




	public void setaLonold(double aLonold) {
		this.aLonold = aLonold;
	}




	public String getLocalizacao() {
		return localizacao;
	}




	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}



	public String getParadacod() {
		return paradacod;
	}




	public void setParadacod(String paradacod) {
		this.paradacod = paradacod;
	}




	public String getCadeirante() {
		return cadeirante;
	}




	public void setCadeirante(String cadeirante) {
		this.cadeirante = cadeirante;
	}




	public int getParada() {
		return parada;
	}




	public void setParada(int parada) {
		this.parada = parada;
	}




	public Date getDataHora() {
		return DataHora;
	}




	public void setDataHora(Date dataHora) {
		DataHora = dataHora;
	}


	public double getDistponto() {
		return distponto;
	}




	public void setDistponto(double distponto) {
		this.distponto = distponto;
	}




	public double getDistyou() {
		return distyou;
	}




	public void setDistyou(double distyou) {
		this.distyou = distyou;
	}




	public String getPonto() {
		return ponto;
	}




	public void setPonto(String ponto) {
		this.ponto = ponto;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public int getTipo() {
		return tipo;
	}




	public void setTipo(int tipo) {
		this.tipo = tipo;
	}




	public String getVeiculo() {
		return veiculo;
	}




	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}




	public LatLng getLatLong() {
		return latLong;
	}




	public void setLatLong(LatLng latLong) {
		this.latLong = latLong;
	}




	public String getLetreiro() {
		return letreiro;
	}




	public void setLetreiro(String letreiro) {
		this.letreiro = letreiro;
	}




	public int getSentido() {
		return sentido;
	}




	public void setSentido(int sentido) {
		this.sentido = sentido;
	}




	public String getPrevisao() {
		return previsao;
	}




	public void setPrevisao(String previsao) {
		this.previsao = previsao;
	}




	public double getaLat() {
		return aLat;
	}




	public void setaLat(double aLat) {
		this.aLat = aLat;
	}




	public double getaLon() {
		return aLon;
	}




	public void setaLon(double aLon) {
		this.aLon = aLon;
	}

	 

	 
	}



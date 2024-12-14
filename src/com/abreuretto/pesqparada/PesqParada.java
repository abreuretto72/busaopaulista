package com.abreuretto.pesqparada;


//Pesquisa parada no SPTrans:  curl --cookie sptrans_cookies http://olhovivo.sptrans.com.br/v0/Parada/Buscar?termosBusca=paulista


//GET /Parada/BuscarParadasPorLinha?codigoLinha={codigoLinha} 


public class PesqParada {


	private String CodigoParada;
	private String Nome;
	private String Endereco;
	private Double Latitude;
	private Double Longitude;

	public String getCodigoParada() {
	return CodigoParada;
	}

	public void setCodigoParada(String CodigoParada) {
	this.CodigoParada = CodigoParada;
	}

	public String getNome() {
	return Nome;
	}

	public void setNome(String Nome) {
	this.Nome = Nome;
	}

	public String getEndereco() {
	return Endereco;
	}

	public void setEndereco(String Endereco) {
	this.Endereco = Endereco;
	}

	public Double getLatitude() {
	return Latitude;
	}

	public void setLatitude(Double Latitude) {
	this.Latitude = Latitude;
	}

	public Double getLongitude() {
	return Longitude;
	}

	public void setLongitude(Double Longitude) {
	this.Longitude = Longitude;
	}

	}


/*
 [
  {
    "CodigoParada": 260015039,
    "Nome": "PAULISTA B/C",
    "Endereco": " AV PAULISTA/ AV REBOUCAS ",
    "Latitude": -23.555883,
    "Longitude": -46.66306
  },
  {
    "CodigoParada": 260016855,
    "Nome": "PAULISTA C/B",
    "Endereco": "AV PAULISTA/ R ANTONIO CARLOS",
    "Latitude": -23.555176,
    "Longitude": -46.66237
  }
]
 
 */


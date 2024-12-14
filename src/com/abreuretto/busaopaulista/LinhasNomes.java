package com.abreuretto.busaopaulista;

import java.io.Serializable;


public class LinhasNomes implements Serializable {

	private static final long serialVersionUID = 11L;
	
	 private String linhatipo;
	private String letreiro1;
	 private String letreiro2;
	 private String linha;
	 private String tipo;
	
	 public String getLinhatipo() {
		return linhatipo;
	}
	public void setLinhatipo(String linhatipo) {
		this.linhatipo = linhatipo;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	 
	 
	 
}
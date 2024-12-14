package com.abreuretto.busaopaulista;

public class AppNoMapa {
	
	
	private String icon;
	
	
	private String id;
    private String titulo;
    private String linha;
	private double aLat;
    private double aLon;
    private int tipo; //1=mapaparada, 3=mapaonibus
    private int veiculo; //1=onibus, 2=metro, 3=trem
	
    
    


    
    
    
    
    
    
    public int getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(int veiculo) {
		this.veiculo = veiculo;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
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
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	

}

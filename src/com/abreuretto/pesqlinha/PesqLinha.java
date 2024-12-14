package com.abreuretto.pesqlinha;

import java.util.HashMap;
import java.util.Map;

public class PesqLinha {

private Integer CodigoLinha;
private Boolean Circular;
private String Letreiro;
private Integer Sentido;
private Integer Tipo;
private String DenominacaoTPTS;
private String DenominacaoTSTP;
private Object Informacoes;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getCodigoLinha() {
return CodigoLinha;
}

public void setCodigoLinha(Integer CodigoLinha) {
this.CodigoLinha = CodigoLinha;
}

public Boolean getCircular() {
return Circular;
}

public void setCircular(Boolean Circular) {
this.Circular = Circular;
}

public String getLetreiro() {
return Letreiro;
}

public void setLetreiro(String Letreiro) {
this.Letreiro = Letreiro;
}

public Integer getSentido() {
return Sentido;
}

public void setSentido(Integer Sentido) {
this.Sentido = Sentido;
}

public Integer getTipo() {
return Tipo;
}

public void setTipo(Integer Tipo) {
this.Tipo = Tipo;
}

public String getDenominacaoTPTS() {
return DenominacaoTPTS;
}

public void setDenominacaoTPTS(String DenominacaoTPTS) {
this.DenominacaoTPTS = DenominacaoTPTS;
}

public String getDenominacaoTSTP() {
return DenominacaoTSTP;
}

public void setDenominacaoTSTP(String DenominacaoTSTP) {
this.DenominacaoTSTP = DenominacaoTSTP;
}

public Object getInformacoes() {
return Informacoes;
}

public void setInformacoes(Object Informacoes) {
this.Informacoes = Informacoes;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}


/*
 [
  {
    "CodigoLinha": 568,
    "Circular": false,
    "Letreiro": "967A",
    "Sentido": 1,
    "Tipo": 10,
    "DenominacaoTPTS": "PINHEIROS",
    "DenominacaoTSTP": "IMIRIM",
    "Informacoes": null
  },
  {
    "CodigoLinha": 33336,
    "Circular": false,
    "Letreiro": "967A",
    "Sentido": 2,
    "Tipo": 10,
    "DenominacaoTPTS": "PINHEIROS",
    "DenominacaoTSTP": "IMIRIM",
    "Informacoes": null
  }
]
 
*/
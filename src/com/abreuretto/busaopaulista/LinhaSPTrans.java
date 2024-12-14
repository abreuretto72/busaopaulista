package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LinhaSPTrans {

private List<com.abreuretto.busaopaulista.BuscaLinhasSIMResult> BuscaLinhasSIMResult = new ArrayList<com.abreuretto.busaopaulista.BuscaLinhasSIMResult>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<com.abreuretto.busaopaulista.BuscaLinhasSIMResult> getBuscaLinhasSIMResult() {
return BuscaLinhasSIMResult;
}

public void setBuscaLinhasSIMResult(List<com.abreuretto.busaopaulista.BuscaLinhasSIMResult> BuscaLinhasSIMResult) {
this.BuscaLinhasSIMResult = BuscaLinhasSIMResult;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

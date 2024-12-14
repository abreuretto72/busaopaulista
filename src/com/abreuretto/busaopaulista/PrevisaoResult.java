package com.abreuretto.busaopaulista;

import java.util.HashMap;
import java.util.Map;

public class PrevisaoResult {

private String hr;
private P p;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getHr() {
return hr;
}

public void setHr(String hr) {
this.hr = hr;
}

public PrevisaoResult withHr(String hr) {
this.hr = hr;
return this;
}

public P getP() {
return p;
}

public void setP(P p) {
this.p = p;
}

public PrevisaoResult withP(P p) {
this.p = p;
return this;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
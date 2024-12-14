package com.abreuretto.busaopaulista;

import java.util.HashMap;
import java.util.Map;



public class V {

private Boolean a;
private String p;
private Double px;
private Double py;
private String t;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Boolean getA() {
return a;
}

public void setA(Boolean a) {
this.a = a;
}

public V withA(Boolean a) {
this.a = a;
return this;
}

public String getP() {
return p;
}

public void setP(String p) {
this.p = p;
}

public V withP(String p) {
this.p = p;
return this;
}

public Double getPx() {
return px;
}

public void setPx(Double px) {
this.px = px;
}

public V withPx(Double px) {
this.px = px;
return this;
}

public Double getPy() {
return py;
}

public void setPy(Double py) {
this.py = py;
}

public V withPy(Double py) {
this.py = py;
return this;
}

public String getT() {
return t;
}

public void setT(String t) {
this.t = t;
}

public V withT(String t) {
this.t = t;
return this;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}





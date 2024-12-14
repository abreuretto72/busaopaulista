package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class P {

private Integer cp;
private List<L> l = new ArrayList<L>();
private String np;
private Double px;
private Double py;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getCp() {
return cp;
}

public void setCp(Integer cp) {
this.cp = cp;
}

public P withCp(Integer cp) {
this.cp = cp;
return this;
}

public List<L> getL() {
return l;
}

public void setL(List<L> l) {
this.l = l;
}

public P withL(List<L> l) {
this.l = l;
return this;
}

public String getNp() {
return np;
}

public void setNp(String np) {
this.np = np;
}

public P withNp(String np) {
this.np = np;
return this;
}

public Double getPx() {
return px;
}

public void setPx(Double px) {
this.px = px;
}

public P withPx(Double px) {
this.px = px;
return this;
}

public Double getPy() {
return py;
}

public void setPy(Double py) {
this.py = py;
}

public P withPy(Double py) {
this.py = py;
return this;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}


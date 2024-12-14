package com.abreuretto.busmoni;

import java.util.HashMap;
import java.util.Map;

public class V {

private String p;
private String t;
private Boolean a;
private Double py;
private Double px;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getP() {
return p;
}

public void setP(String p) {
this.p = p;
}

public String getT() {
return t;
}

public void setT(String t) {
this.t = t;
}

public Boolean getA() {
return a;
}

public void setA(Boolean a) {
this.a = a;
}

public Double getPy() {
return py;
}

public void setPy(Double py) {
this.py = py;
}

public Double getPx() {
return px;
}

public void setPx(Double px) {
this.px = px;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

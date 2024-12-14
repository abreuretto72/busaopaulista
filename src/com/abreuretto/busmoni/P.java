package com.abreuretto.busmoni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P {

private Integer cp;
private String np;
private Double py;
private Double px;
private List<L> l = new ArrayList<L>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getCp() {
return cp;
}

public void setCp(Integer cp) {
this.cp = cp;
}

public String getNp() {
return np;
}

public void setNp(String np) {
this.np = np;
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

public List<L> getL() {
return l;
}

public void setL(List<L> l) {
this.l = l;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

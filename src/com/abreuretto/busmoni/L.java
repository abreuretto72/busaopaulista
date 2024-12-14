
package com.abreuretto.busmoni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L {

private String c;
private Integer cl;
private Integer sl;
private String lt0;
private String lt1;
private Integer qv;
private List<V> vs = new ArrayList<V>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getC() {
return c;
}

public void setC(String c) {
this.c = c;
}

public Integer getCl() {
return cl;
}

public void setCl(Integer cl) {
this.cl = cl;
}

public Integer getSl() {
return sl;
}

public void setSl(Integer sl) {
this.sl = sl;
}

public String getLt0() {
return lt0;
}

public void setLt0(String lt0) {
this.lt0 = lt0;
}

public String getLt1() {
return lt1;
}

public void setLt1(String lt1) {
this.lt1 = lt1;
}

public Integer getQv() {
return qv;
}

public void setQv(Integer qv) {
this.qv = qv;
}

public List<V> getVs() {
return vs;
}

public void setVs(List<V> vs) {
this.vs = vs;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
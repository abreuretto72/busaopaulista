package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L {

private String c;
private Integer cl;
private String lt0;
private String lt1;
private Integer qv;
private Integer sl;
private List<V> vs = new ArrayList<V>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getC() {
return c;
}

public void setC(String c) {
this.c = c;
}

public L withC(String c) {
this.c = c;
return this;
}

public Integer getCl() {
return cl;
}

public void setCl(Integer cl) {
this.cl = cl;
}

public L withCl(Integer cl) {
this.cl = cl;
return this;
}

public String getLt0() {
return lt0;
}

public void setLt0(String lt0) {
this.lt0 = lt0;
}

public L withLt0(String lt0) {
this.lt0 = lt0;
return this;
}

public String getLt1() {
return lt1;
}

public void setLt1(String lt1) {
this.lt1 = lt1;
}

public L withLt1(String lt1) {
this.lt1 = lt1;
return this;
}

public Integer getQv() {
return qv;
}

public void setQv(Integer qv) {
this.qv = qv;
}

public L withQv(Integer qv) {
this.qv = qv;
return this;
}

public Integer getSl() {
return sl;
}

public void setSl(Integer sl) {
this.sl = sl;
}

public L withSl(Integer sl) {
this.sl = sl;
return this;
}

public List<V> getVs() {
return vs;
}

public void setVs(List<V> vs) {
this.vs = vs;
}

public L withVs(List<V> vs) {
this.vs = vs;
return this;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

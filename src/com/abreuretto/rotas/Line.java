package com.abreuretto.rotas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Line {

private List<Agency> agencies = new ArrayList<Agency>();
private String name;
private String short_name;
private Vehicle vehicle;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<Agency> getAgencies() {
return agencies;
}

public void setAgencies(List<Agency> agencies) {
this.agencies = agencies;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getShort_name() {
return short_name;
}

public void setShort_name(String short_name) {
this.short_name = short_name;
}

public Vehicle getVehicle() {
return vehicle;
}

public void setVehicle(Vehicle vehicle) {
this.vehicle = vehicle;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
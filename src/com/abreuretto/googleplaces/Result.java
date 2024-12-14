package com.abreuretto.googleplaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

private Geometry geometry;
private String icon;
private String id;
private String name;
private String reference;
private List<String> types = new ArrayList<String>();
private String vicinity;
private Opening_hours opening_hours;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Geometry getGeometry() {
return geometry;
}

public void setGeometry(Geometry geometry) {
this.geometry = geometry;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getReference() {
return reference;
}

public void setReference(String reference) {
this.reference = reference;
}

public List<String> getTypes() {
return types;
}

public void setTypes(List<String> types) {
this.types = types;
}

public String getVicinity() {
return vicinity;
}

public void setVicinity(String vicinity) {
this.vicinity = vicinity;
}

public Opening_hours getOpening_hours() {
return opening_hours;
}

public void setOpening_hours(Opening_hours opening_hours) {
this.opening_hours = opening_hours;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}


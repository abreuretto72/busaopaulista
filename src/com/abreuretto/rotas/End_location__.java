package com.abreuretto.rotas;

import java.util.HashMap;
import java.util.Map;

public class End_location__ {

private Double lat;
private Double lng;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Double getLat() {
return lat;
}

public void setLat(Double lat) {
this.lat = lat;
}

public Double getLng() {
return lng;
}

public void setLng(Double lng) {
this.lng = lng;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

package com.abreuretto.googleplaces;

import java.util.HashMap;
import java.util.Map;

public class Geometry {

private Location location;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Location getLocation() {
return location;
}

public void setLocation(Location location) {
this.location = location;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
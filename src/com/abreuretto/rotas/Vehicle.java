package com.abreuretto.rotas;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {

private String icon;
private String name;
private String type;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

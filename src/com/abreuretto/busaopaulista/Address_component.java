package com.abreuretto.busaopaulista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Address_component {

private String long_name;
private String short_name;
private List<String> types = new ArrayList<String>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getLong_name() {
return long_name;
}

public void setLong_name(String long_name) {
this.long_name = long_name;
}

public String getShort_name() {
return short_name;
}

public void setShort_name(String short_name) {
this.short_name = short_name;
}

public List<String> getTypes() {
return types;
}

public void setTypes(List<String> types) {
this.types = types;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

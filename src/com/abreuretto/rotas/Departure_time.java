package com.abreuretto.rotas;

import java.util.HashMap;
import java.util.Map;
public class Departure_time {

private String text;
private String time_zone;
private Integer value;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

public String getTime_zone() {
return time_zone;
}

public void setTime_zone(String time_zone) {
this.time_zone = time_zone;
}

public Integer getValue() {
return value;
}

public void setValue(Integer value) {
this.value = value;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

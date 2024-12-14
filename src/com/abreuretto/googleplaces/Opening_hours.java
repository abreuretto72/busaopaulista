package com.abreuretto.googleplaces;

import java.util.HashMap;
import java.util.Map;

public class Opening_hours {

private Boolean open_now;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Boolean getOpen_now() {
return open_now;
}

public void setOpen_now(Boolean open_now) {
this.open_now = open_now;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

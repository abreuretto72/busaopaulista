package com.abreuretto.rotas;


import java.util.HashMap;
import java.util.Map;
public class Agency {

private String name;
private String url;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

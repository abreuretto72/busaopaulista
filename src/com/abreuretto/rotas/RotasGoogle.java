package com.abreuretto.rotas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RotasGoogle {

private List<Route> routes = new ArrayList<Route>();
private String status;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<Route> getRoutes() {
return routes;
}

public void setRoutes(List<Route> routes) {
this.routes = routes;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

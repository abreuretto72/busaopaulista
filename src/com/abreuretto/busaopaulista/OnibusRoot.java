package com.abreuretto.busaopaulista;

import java.util.HashMap;
import java.util.Map;

public class OnibusRoot {

private com.abreuretto.busaopaulista.PrevisaoResult PrevisaoResult;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public com.abreuretto.busaopaulista.PrevisaoResult getPrevisaoResult() {
return PrevisaoResult;
}

public void setPrevisaoResult(com.abreuretto.busaopaulista.PrevisaoResult PrevisaoResult) {
this.PrevisaoResult = PrevisaoResult;
}

public OnibusRoot withPrevisaoResult(com.abreuretto.busaopaulista.PrevisaoResult PrevisaoResult) {
this.PrevisaoResult = PrevisaoResult;
return this;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

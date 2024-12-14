
package com.abreuretto.buddysearchnearby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BuddySearchNearBy {

private List<Datum> data = new ArrayList<Datum>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
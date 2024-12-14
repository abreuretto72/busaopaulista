package com.abreuretto.buddysearchnearby;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Datum implements Serializable {

	private static final long serialVersionUID = 25L;
	
	
private String metaKey;
private String metaValue;
private String lastUpdateDate;
private String metaLatitude;
private String metaLongitude;
private String distanceInMiles;
private String distanceInKilometers;
private String distanceInMeters;
private String distanceInYards;
private String appTag;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getMetaKey() {
return metaKey;
}

public void setMetaKey(String metaKey) {
this.metaKey = metaKey;
}

public String getMetaValue() {
return metaValue;
}

public void setMetaValue(String metaValue) {
this.metaValue = metaValue;
}

public String getLastUpdateDate() {
return lastUpdateDate;
}

public void setLastUpdateDate(String lastUpdateDate) {
this.lastUpdateDate = lastUpdateDate;
}

public String getMetaLatitude() {
return metaLatitude;
}

public void setMetaLatitude(String metaLatitude) {
this.metaLatitude = metaLatitude;
}

public String getMetaLongitude() {
return metaLongitude;
}

public void setMetaLongitude(String metaLongitude) {
this.metaLongitude = metaLongitude;
}

public String getDistanceInMiles() {
return distanceInMiles;
}

public void setDistanceInMiles(String distanceInMiles) {
this.distanceInMiles = distanceInMiles;
}

public String getDistanceInKilometers() {
return distanceInKilometers;
}

public void setDistanceInKilometers(String distanceInKilometers) {
this.distanceInKilometers = distanceInKilometers;
}

public String getDistanceInMeters() {
return distanceInMeters;
}

public void setDistanceInMeters(String distanceInMeters) {
this.distanceInMeters = distanceInMeters;
}

public String getDistanceInYards() {
return distanceInYards;
}

public void setDistanceInYards(String distanceInYards) {
this.distanceInYards = distanceInYards;
}

public String getAppTag() {
return appTag;
}

public void setAppTag(String appTag) {
this.appTag = appTag;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

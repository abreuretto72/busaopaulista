package com.abreuretto.rotas;

import java.util.HashMap;
import java.util.Map;

public class Step_ {

private Distance__ distance;
private Duration__ duration;
private End_location__ end_location;
private String html_instructions;
private Polyline_ polyline;
private Start_location__ start_location;
private String travel_mode;
private String maneuver;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Distance__ getDistance() {
return distance;
}

public void setDistance(Distance__ distance) {
this.distance = distance;
}

public Duration__ getDuration() {
return duration;
}

public void setDuration(Duration__ duration) {
this.duration = duration;
}

public End_location__ getEnd_location() {
return end_location;
}

public void setEnd_location(End_location__ end_location) {
this.end_location = end_location;
}

public String getHtml_instructions() {
return html_instructions;
}

public void setHtml_instructions(String html_instructions) {
this.html_instructions = html_instructions;
}

public Polyline_ getPolyline() {
return polyline;
}

public void setPolyline(Polyline_ polyline) {
this.polyline = polyline;
}

public Start_location__ getStart_location() {
return start_location;
}

public void setStart_location(Start_location__ start_location) {
this.start_location = start_location;
}

public String getTravel_mode() {
return travel_mode;
}

public void setTravel_mode(String travel_mode) {
this.travel_mode = travel_mode;
}

public String getManeuver() {
return maneuver;
}

public void setManeuver(String maneuver) {
this.maneuver = maneuver;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

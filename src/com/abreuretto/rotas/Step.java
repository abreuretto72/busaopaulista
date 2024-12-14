package com.abreuretto.rotas;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step {

private Distance_ distance;
private Duration_ duration;
private End_location_ end_location;
private String html_instructions;
private Polyline polyline;
private Start_location_ start_location;
private List<Step_> steps = new ArrayList<Step_>();
private String travel_mode;
private Transit_details transit_details;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Distance_ getDistance() {
return distance;
}

public void setDistance(Distance_ distance) {
this.distance = distance;
}

public Duration_ getDuration() {
return duration;
}

public void setDuration(Duration_ duration) {
this.duration = duration;
}

public End_location_ getEnd_location() {
return end_location;
}

public void setEnd_location(End_location_ end_location) {
this.end_location = end_location;
}

public String getHtml_instructions() {
return html_instructions;
}

public void setHtml_instructions(String html_instructions) {
this.html_instructions = html_instructions;
}

public Polyline getPolyline() {
return polyline;
}

public void setPolyline(Polyline polyline) {
this.polyline = polyline;
}

public Start_location_ getStart_location() {
return start_location;
}

public void setStart_location(Start_location_ start_location) {
this.start_location = start_location;
}

public List<Step_> getSteps() {
return steps;
}

public void setSteps(List<Step_> steps) {
this.steps = steps;
}

public String getTravel_mode() {
return travel_mode;
}

public void setTravel_mode(String travel_mode) {
this.travel_mode = travel_mode;
}

public Transit_details getTransit_details() {
return transit_details;
}

public void setTransit_details(Transit_details transit_details) {
this.transit_details = transit_details;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
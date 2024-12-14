package com.abreuretto.rotas;

import java.util.HashMap;
import java.util.Map;

public class Transit_details {

private Arrival_stop arrival_stop;
private Arrival_time_ arrival_time;
private Departure_stop departure_stop;
private Departure_time_ departure_time;
private String headsign;
private Integer headway;
private Line line;
private Integer num_stops;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Arrival_stop getArrival_stop() {
return arrival_stop;
}

public void setArrival_stop(Arrival_stop arrival_stop) {
this.arrival_stop = arrival_stop;
}

public Arrival_time_ getArrival_time() {
return arrival_time;
}

public void setArrival_time(Arrival_time_ arrival_time) {
this.arrival_time = arrival_time;
}

public Departure_stop getDeparture_stop() {
return departure_stop;
}

public void setDeparture_stop(Departure_stop departure_stop) {
this.departure_stop = departure_stop;
}

public Departure_time_ getDeparture_time() {
return departure_time;
}

public void setDeparture_time(Departure_time_ departure_time) {
this.departure_time = departure_time;
}

public String getHeadsign() {
return headsign;
}

public void setHeadsign(String headsign) {
this.headsign = headsign;
}

public Integer getHeadway() {
return headway;
}

public void setHeadway(Integer headway) {
this.headway = headway;
}

public Line getLine() {
return line;
}

public void setLine(Line line) {
this.line = line;
}

public Integer getNum_stops() {
return num_stops;
}

public void setNum_stops(Integer num_stops) {
this.num_stops = num_stops;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

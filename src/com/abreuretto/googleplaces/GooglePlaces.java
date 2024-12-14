package com.abreuretto.googleplaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GooglePlaces {

private List<Object> debug_info = new ArrayList<Object>();
private List<Object> html_attributions = new ArrayList<Object>();
private String next_page_token;
private List<Result> results = new ArrayList<Result>();
private String status;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<Object> getDebug_info() {
return debug_info;
}

public void setDebug_info(List<Object> debug_info) {
this.debug_info = debug_info;
}

public List<Object> getHtml_attributions() {
return html_attributions;
}

public void setHtml_attributions(List<Object> html_attributions) {
this.html_attributions = html_attributions;
}

public String getNext_page_token() {
return next_page_token;
}

public void setNext_page_token(String next_page_token) {
this.next_page_token = next_page_token;
}

public List<Result> getResults() {
return results;
}

public void setResults(List<Result> results) {
this.results = results;
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




/*
 {
debug_info: [ ],
html_attributions: [ ],
results: [
{
geometry: {
location: {
lat: -23.603717,
lng: -46.662385
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "37e67b67e2fc9a270fc4dd25e89530d00806d09a",
name: "Moema C/B",
reference: "CnRoAAAAF6qUnzKqL93muovhDeF_2wSzGioN6mLOgjdbU0_q2IjD-TO2P8PcaaQ_LHHovhckSTY5aEudYW7I7upYRQzRjld52ESS6-AYPFYsboiBJmsj_QTog9Idx-p4kVHLEV5FTCIo_n5n-nPZtwcZ5yjwohIQB_8Hc1E7yyA8GJUKkyYWARoUbVr_VsIsLMh6lPGvIgJRg0c4o_E",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.606847,
lng: -46.665149
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "25bb1fce166f029ac19ca882888a9b7f510256a1",
name: "Juruce B/C",
reference: "CnRpAAAA0c3jaeLMWwYLrfwf6U45It5CSfOmb4L2I3DrVurlfYLrCesm-wJ3cCUXXxz3URkOuOxjJ3wPsVbzWTrvETTP2-WECMkFze35v7i12Z2gQ0KRv6x9-wmt6SpgtJiQVSUTNrpJ7qpOVq8XIZcC5BQPbBIQNHVV5BIOYpZ2-L8olblR0xoUsytISKeZOryLfMwo5YXuXl92oTk",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.599782,
lng: -46.668051
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "e648705d29b5d923d557e9692810186f9deb6f81",
name: "Av. Lavandisca, 124",
reference: "CoQBcQAAAHk92tX5wZndb8zjl04DDCYMbSNPNQVtrOxnyExWc8T-LuMdvK8718olORsoEvZ9NakcF2wSY1c6ooS8_RjaPmK7klp7a27k9dYOceHt2ywOdJOzF--Vx1dIZ00WsoZ0xFSGtwAcsGCCXs_ayVNc1GQ1QdW4YkXwabS_nWCaWRUjEhA5NFvLLwmETlnYMI4_sl0lGhRPrFSF9U8NZ7ZpIzE-u-nXTbSN9Q",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.600642,
lng: -46.666959
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "b88d9a199926663ee92e35cb5de4d69ac35f4d56",
name: "Av. Lavandisca, 286",
reference: "CoQBcQAAADdAJtX7x2jjPAcdPIztlMjqIOCQ0H1EdZIm75-StArVUV8wQ59TehB8xgLaIgL2thoazUt-w92jIqBFXmmBPyo_3-Hg-lTzmCTlnkEknUl6YsNCdE11NglIR3gjaAxGUp8DjJwGDdmATSnLCoBdBXq_IM9RcBWSg-P_7XWJtGShEhDCAOIgLVCq9TRjTUCmoSgsGhT54xx3qYFc6aBsW6bW6SgwK4cp-w",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.607431,
lng: -46.670472
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "359760e79c51f23a61cc912d4e8e931ac9caca7d",
name: "Av. Cotovia, 605",
reference: "CnRuAAAA9poZ1wzM7A7RtbZbkvLsvWU-oq0HErq_xLtzudAXz_BWLeoIg2-QnQe9D___QkJ6ZpGRtFOI7I5pgp1D2n6S7x1MKvxnuhHBAijRISv_h9Rhl82e4KT9X1-6rEK2r7S1ckltlhEu8h9KdEYm24yKBRIQpmeyelQin6YU6uX6I2A5PxoUUxNvoCMqGECt5-Ftrm4N81pGygk",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.604504,
lng: -46.669075
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "27bc4e4ac463c81d90df49310220fbec969fbef5",
name: "Av. Rouxinol, 630",
reference: "CnRwAAAAgKWt0_No21aZAij2GhToLoEFVK5u6MLXX3BLrUuXA1kFO9PLfDD1--fj-ZI2SKgjf_VC034YIKyVTlX9MOILVYMBIgVkCLYvHFQbNMLbV7FQdVe70i6Dpm78ht4kjX7q5cDTHPnV5E5O4c5fhii74hIQhv0lpSxtLJkRVc1rn6xGkhoUxOtBSPW3slfaE0h6NB9ASXE8T78",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.600268,
lng: -46.662638
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "81faf3cd2a34ec35ff28f540212da35b00efdbf2",
name: "Al. Jauaperi, 271",
reference: "CnRvAAAAVthAMB50_xhtMCp16JyOZjGqh141PiNOHrAb3p9VzZyvADXOltjlzkX-dDmkqeJLRq97MQba0Y4d_uT-0ewjXsDArayjskvK0agDe8hDupmhTKugHMoNrCEJPJPPrNR5uiVVX6JG4tyRZNeeVkI-yxIQFwbZkAxueJdN5HP1FQ8ZTRoUJQ4Agju2gAkGOFXS3wmUwAdrOGU",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.601102,
lng: -46.661784
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "a251ee0c21434c5563e76bfe30aa33b4bfb07453",
name: "Al. Dos Arapanes, 276",
reference: "CoQBdAAAANF8zuwfC5xEbPRT6wUPmYYgKmwsTHurQ5K0arse62hV_WQ8d3oANadYrO9E38cvWWF4BeDvkpms48V-Y3xdiWjN4hE-zl999k4uWKos8afukax5vZ4lDPTRmlbU5psx8jYmC4D0vGqTPlu2livaJuL4NSNboiQFZND7OtXbt3M7EhC3RmeVjWwVY5jvYnT9Lel-GhSry-fzgunwRHumAMSMGIl-0fXWAQ",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.605169,
lng: -46.66132
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "dc26d18154efdf95a15d4bec2fccf5c998f8b7c1",
name: "Av. Jamaris",
reference: "CnRpAAAAkOHKMbP07ew4isoCdUVF8-t7iN2BVuaA2Y_yNX-EJ1hDwF98w8PtBRCHGsFNPMmW8DWxl5-mAnAA-rourftJns0UglS3MMn-RRpBldlXYWAfu5647iXOn4vInZ_MnXRkg6G2rgeeBNEHFnpf-iUTpxIQnRdrAM5lqMUExNs5WMxwGBoUxLLLxDiF-Ttje9hLUNHYrzvHGJ4",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.603016,
lng: -46.661581
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "d80c492b46ed2ce4ba36343c217594f38a52c7be",
name: "Moema B/C",
reference: "CnRoAAAA-dUl15NjiWNVAJxaP__d7yWY3Kn0k3N4d_L0rMHt8th0Pu99PqqZC_hJPHonJ32-1AvJ9XW8BgGVNsF-AQv486FiFNA_TRhwDQdDAPWRa_od--vvVhsEZhBl_RBZkw6XiHC9w_NEJvIY5gGKeHnrJhIQbpluJy3iuhoeLvWBv8FS1hoUvneGfD8KnzTMrTU-9INVVjbVzUU",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.605952,
lng: -46.664513
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "71bb3e6b68519b7b9359ef39a5d7e2cad05ff526",
name: "Av. Ibirapuera, 2496",
reference: "CoQBcwAAAMT_aHGUSp710EffzoldGxx2AjbDUCYhxiEMYvKi5douOCPs8QXthmxtCpbyrUI9PveWp38JbZsUKmny_p635SmhPQ6rrvf_oNYXOCugRkwcIL7kpHgFE_YAuErlyaLfi5moYDa-fWycjhmDOk4gc8OasctFQ1ev-hT9uLgsj6eQEhCJt94m7e5Z3hxR0zj5e4zvGhQ2oKm2aZP76H4Sw_m3dB2hh-76UQ",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.600872,
lng: -46.663094
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "5a1e7121e9e05def3096bd51cf716387387d0c6c",
name: "Av. Juriti, 505",
reference: "CnRuAAAA12nwFhIJHDgIcKMpN7xoy_RvHQ9S96dffelA3U5EaqmVrA6wCt-9oFUwzMU-ndqF8di_EcaQfwdzl8QXzkjHGYiADX4SVi4GdGBdRuK5tqkP8cUePEE_UsPyUgfWG6SRe3WGJyGAHwF8W0iZzsS2fBIQLB5V66qzJ08lcZgfepl1choUM-IHM_9uxC7k-sEvPIpjzcprNZA",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.603453,
lng: -46.6622
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "6827e7c1fb6fce68c6ee23667e4f590d9f3a6095",
name: "Av. Ibirapuera, 2206",
reference: "CoQBcgAAAGG7v81Fo7Xw_Sn8UkodBhkQwUXNjbXZdWFvVnusEp-evHLHGlIamJR1unVMWkC_gSpky48ucs-97Hm8euOyrW1jcUYIy8k-iWxgPnB0JD7Vib7W34mjagiiotSg4VUnAkdyH_FhDzvtyVG5Okfq638OvDAAfKBKi2m9PAAxJDaGEhAIxyRnpJuN2sdaxQNwqo_HGhR08w8Mdk-rUE5nnNG02pmlsIHrmA",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.601839,
lng: -46.665444
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "7d39f164ae85fd9607437da33711f354516ed40d",
name: "Av. Lavandisca, 464",
reference: "CoQBcQAAAHCoo6eECFwCEploFAJk8hOZcXrYrdXsVVJVPD2DW8C-a2CSnZdzqGTAzPS5RhX7LUes-4osMnafnEenfyJw0o3KuRq81pyMid-66stksvoARm_x35DQ9V3bvUzFmFUp7udof00aHZ05TEsB4yW1ntzjH5kd2AXJWdTlmD1Qs2VzEhAg0TsL3vxoKvOhzOBVsUNvGhTFI_M0-T0I32PxcTmA3wxxXABRWg",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
},
{
geometry: {
location: {
lat: -23.603809,
lng: -46.663038
}
},
icon: "http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
id: "1e270744c3adea8ac688ab2572613d562303f50f",
name: "Av. Lavandisca",
reference: "CnRsAAAAWfi3M0u3Zz0KFoCbesjfV0l41lbX2jMpTIkroueZ6OqESQgkCC3oK6t4oLYZvudNPIFbDX0aZelyJRRR3cVrdJ768Ly2Sm-dWYc9wdV9V64tHcS2WTiBCjVCshBssPpJwuY8w4HCwg_RZSXi8kroMBIQ1CJ1mPxg11cGsk7G_ZTpdhoUO8TBgc3Vs35EP8ww3aBwnz3yzGQ",
types: [
"bus_station",
"transit_station",
"establishment"
],
vicinity: "Brazil"
}
],
status: "OK"
}*/




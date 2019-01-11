package net.octacomm.sample.domain.weather;

import java.util.List;

import lombok.Data;

@Data
public class Weatherbit {
	
	
	private List<net.octacomm.sample.domain.weather.Data> data;

	private String city_name;
	private String lon;
	private String timezone;
	private String lat;
	private String country_code;
	private String state_code;
	private String district;

}

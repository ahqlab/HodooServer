package net.octacomm.sample.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import net.octacomm.sample.domain.google.AddressComponents;
import net.octacomm.sample.domain.google.Location;
import net.octacomm.sample.domain.weather.Data;
import net.octacomm.sample.domain.weather.Weatherbit;
import net.octacomm.sample.utils.DateUtil;

@RequestMapping(value= {"/weather", "/location"})
@Controller
public class WeatherController {
	
	// API KEY : 6d7dbc31ba3f4db9a278fc57fe54c82
	@SuppressWarnings("unlikely-arg-type")
	@ResponseBody
	@RequestMapping(value = "/forecast/for/weatherbit", method = RequestMethod.GET)
	private Weatherbit forecastForWeatherbit(@RequestParam("lat") String lat, @RequestParam("lon") String lon) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("https://api.weatherbit.io/v2.0/forecast/hourly?&lat=" + lat + "&lon=" + lon +"&key=6d7dbc31ba3f4db9a278fc57fe54c82f&hours=48", String.class);
		Gson gson = new Gson();
		Weatherbit weatherbit = gson.fromJson(result, Weatherbit.class);
		List<Data> data = weatherbit.getData();
		Iterator<Data> iter = data.iterator();
		while (iter.hasNext()) {
			Data inData = iter.next();
			if (!inData.getDatetime().equals(DateUtil.getOnlyCurrentDateAndHour())) {
				iter.remove();
			}
		}
		AddressComponents addressComponents = getGeoPointFromAddress(lat, lon) ;
		weatherbit.setDistrict(addressComponents.getShort_name());
		return weatherbit;
	}
	
	//API KEY : AIzaSyAdxQVj7aiuBlZfHpspOcHgGesQ6Vs5cos
	public AddressComponents  getGeoPointFromAddress(String lat, String lon) {
		//https://maps.googleapis.com/maps/api/geocode/json?sensor=false&language=en&latlng=36.3584839,127.3406404&key=AIzaSyA61YJVIWhysxkF1GEZo_aFV5sZo4fBZQI
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&language=en&latlng=" + lat + "," + lon + "&key=AIzaSyA61YJVIWhysxkF1GEZo_aFV5sZo4fBZQI",String.class);
		Gson gson = new Gson();
		Location location = gson.fromJson(result, Location.class);
		AddressComponents components = location.getResults().get(0).getAddress_components().get(1);
		return components;
	}
}

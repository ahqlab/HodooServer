package net.octacomm.sample.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.CountryMapper;
import net.octacomm.sample.domain.Country;
import net.octacomm.sample.service.CountryService;

@RequestMapping("/country")
@Controller
public class CountryController {
	@Autowired
	CountryService service;
	
	private final int KO_CODE = 1;
	private final int EN_CODE = 2;
	private final int JA_CODE = 3;
	private final int CH_CODE = 4;
	
	@ResponseBody
	@RequestMapping(value = "/{language}/getAllCountry", method = RequestMethod.GET)
	public JSONArray getAllCountry (
			@PathVariable("language") int language) {
		
		String columnName = "";
		switch( language ) {
			case KO_CODE:
				columnName = "ko_name";
				break;
			case EN_CODE:
				columnName = "en_name";
			case JA_CODE :
				columnName = "en_name";
		}
		
		List<Country> country = service.getAllCountry();
		
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < country.size(); i++) {
			JSONObject obj = new JSONObject();
			String name = "";
			obj.put("id", country.get(i).getId());
			
			switch( language ) {
				case 1:
					name = country.get(i).getKoName();
					break;
				case 2:
					name = country.get(i).getEnName();
					break;
				case 3:
					name = country.get(i).getJaName();
					break;
				case 4:
					name = country.get(i).getChName();
					break;
			}
			obj.put("name", name);
			array.add(obj);
		}
		
		return array;
	}
}

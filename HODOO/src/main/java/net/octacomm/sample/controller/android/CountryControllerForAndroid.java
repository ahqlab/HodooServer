package net.octacomm.sample.controller.android;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.CountryMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Country;
import net.octacomm.sample.service.CountryService;

@RequestMapping("/android/country")
@Controller
public class CountryControllerForAndroid {
	
	@Autowired
	CountryService service;
	
	@ResponseBody
	@RequestMapping(value = "/{language}/getAllCountry.do", method = RequestMethod.GET)
	public CommonResponce<List<Country>> getAllCountry (@PathVariable("language") int language) {
		CommonResponce<List<Country>> responce = new CommonResponce<List<Country>>();
		String columnName = "";
		switch( language ) {
			case HodooConstant.KO_CODE:
				columnName = "ko_name";
				break;
			case HodooConstant.EN_CODE:
				columnName = "en_name";
				break;
			case HodooConstant.JA_CODE :
				columnName = "ja_name";
				break;
			case HodooConstant.CH_CODE :
				columnName = "ch_name";
				break;
			default :
				columnName = "ko_name";
				break;
		}
		List<Country> list = service.getAllCountry( columnName );
		responce.setDomain(list);
		if(service.getAllCountry( columnName ) != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
}




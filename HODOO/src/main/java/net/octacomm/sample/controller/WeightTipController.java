package net.octacomm.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.WeightTipMapper;
import net.octacomm.sample.domain.WeightTip;

@RequestMapping(value= {"/tip"})
@Controller
public class WeightTipController {
	
	@Autowired
	private WeightTipMapper weightTipMapper; 
	
	@ResponseBody
	@RequestMapping(value = "/get/county/message", method = RequestMethod.POST)
	public WeightTip getCountryMessage(@RequestBody WeightTip weightTip ) {
		return weightTipMapper.getCountryMessage(weightTip);
	}
}

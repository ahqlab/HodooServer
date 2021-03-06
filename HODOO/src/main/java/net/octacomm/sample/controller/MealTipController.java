package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.MealTipMapper;
import net.octacomm.sample.dao.mapper.WeightTipMapper;
import net.octacomm.sample.domain.MealTip;
import net.octacomm.sample.domain.WeightTip;

@RequestMapping(value= {"/tip/meal"})
@Controller
public class MealTipController {
	
	@Autowired
	private MealTipMapper mealTipMapper; 
	
	
	/**
	 * language 에 맞는 식사 TIP 을 리턴한다.
	 * @param mealTip 
	 * @return MealTip
	 */
	@ResponseBody
	@RequestMapping(value = "/get/county/message.do", method = RequestMethod.POST)
	public MealTip getCountryMessage(@RequestBody MealTip mealTip) {
		return mealTipMapper.getCountryMessage(mealTip);
	}
}

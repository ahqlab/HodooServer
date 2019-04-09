package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.MealTipMapper;
import net.octacomm.sample.dao.mapper.WeightTipMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.MealTip;
import net.octacomm.sample.domain.WeightTip;

@RequestMapping(value= {"/android/tip/meal"})
@Controller
public class MealTipControllerForAndroid {
	
	@Autowired
	private MealTipMapper mealTipMapper; 
	
	@ResponseBody
	@RequestMapping(value = "/get/county/message.do", method = RequestMethod.POST)
	public CommonResponce<MealTip> getCountryMessage(@RequestBody MealTip mealTip) {
		CommonResponce<MealTip> response = new CommonResponce<MealTip>();
		MealTip obj =  mealTipMapper.getCountryMessage(mealTip);
		if(obj != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(obj);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
	}
}

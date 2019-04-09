package net.octacomm.sample.controller.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.WeightTipMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.WeightTip;

@RequestMapping("/android/tip")
@Controller
public class WeightTipControllerForAndroid {
	
	@Autowired
	private WeightTipMapper weightTipMapper; 
	
	@ResponseBody
	@RequestMapping(value = "/get/county/message.do", method = RequestMethod.POST)
	public CommonResponce<WeightTip> getCountryMessage(@RequestBody WeightTip weightTip ) {
		CommonResponce<WeightTip> response = new CommonResponce<WeightTip>();
		WeightTip obj = weightTipMapper.getCountryMessage(weightTip);
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

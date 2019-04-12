package net.octacomm.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.AppVersionMapper;
import net.octacomm.sample.domain.AppVersion;
import net.octacomm.sample.domain.CommonResponce;

@RequestMapping("/app/version")
@Controller
public class AppVersionController {
	
	@Autowired
	private AppVersionMapper AppVersionMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/import.do", method = RequestMethod.POST)
	public CommonResponce<AppVersion> doGet() {
		CommonResponce<AppVersion> response = new CommonResponce<AppVersion>();
		AppVersion obj = AppVersionMapper.get(1);
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

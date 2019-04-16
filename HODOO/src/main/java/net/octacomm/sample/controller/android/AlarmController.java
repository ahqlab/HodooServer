package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.AlarmMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.AlarmItem;

@RequestMapping("/android/alarm")
@Controller
public class AlarmController {
	
	@Autowired
	private AlarmMapper alarmMapper;
	
	@ResponseBody
	@RequestMapping(value = "/list.do", method = RequestMethod.POST)
	public CommonResponce<List<AlarmItem>> searchList(@RequestParam("language") String language) {
		CommonResponce<List<AlarmItem>> responce  = new CommonResponce<List<AlarmItem>>(); 
		List<AlarmItem> list =  alarmMapper.getAlarmList(language);
		responce.setDomain(list);
		if(list.size() > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
}

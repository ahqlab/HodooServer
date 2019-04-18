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
import net.octacomm.sample.dao.mapper.AlarmObjectMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.AlarmItem;
import net.octacomm.sample.domain.AlarmObject;

@RequestMapping("/android/alarm")
@Controller
public class AlarmController {
	
	@Autowired
	private AlarmMapper alarmMapper;
	
	@Autowired
	private AlarmObjectMapper alarmObjectMapper;
	
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
	@ResponseBody
	@RequestMapping(value="/get/alarm.do", method = RequestMethod.POST)
	public CommonResponce<Integer> getAlarm( @RequestParam("userIdx") int userIdx ) {
		CommonResponce<Integer> response = new CommonResponce<>();
		int result = alarmObjectMapper.getAlarm(userIdx);
		response.setDomain(result);
		response.setStatus(HodooConstant.OK_RESPONSE);
		return response;
	}
	@ResponseBody
	@RequestMapping(value="/save/alarm.do", method = RequestMethod.POST)
	public CommonResponce<Integer> saveAlarm( @RequestParam("userIdx") int userIdx, @RequestParam("number") int number ) {
		
		int editState = alarmObjectMapper.checkAlarmMapper(userIdx);
//		int editState = 0;
		CommonResponce<Integer> response = new CommonResponce<>();
		AlarmObject obj = new AlarmObject();
		obj.setUserIdx(userIdx);
		obj.setNumber(number);
		
		if ( editState > 0 )
			response.setDomain( alarmObjectMapper.updateAlarmMapper(userIdx, number) );
		else
			response.setDomain( alarmObjectMapper.saveAlarmMapper(obj) );
		
		if ( response.domain > 0 )
			response.setStatus(HodooConstant.OK_RESPONSE);
		else 
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		return response;
	}
}

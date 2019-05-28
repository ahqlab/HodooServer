package net.octacomm.sample.controller.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.Statistics;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.utils.FcmUtil;

@RequestMapping("/android/weight")
@Controller
public class RealTimeWeightControllerForAndroid {

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private net.octacomm.sample.dao.mapper.RealTimeWeightMapper RealTimeWeightMapper;

	
	/**
	 * 디바이스에서 실제 데이터를 보내는 URL 
	 * @param realTimeWeight
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/realtime/get.do")
	public String regist(RealTimeWeight realTimeWeight) {
		RealTimeWeightMapper.insert(realTimeWeight);
		//PUSH 를 보낸다.
		RealTimeThred thred = new RealTimeThred(realTimeWeight);
		thred.start();
		return "HELLO";
	}
	
	/**
	 * 사용안함.
	 * @param mac String
	 * @return CommonResponce<Float>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/lately/data.do")
	public CommonResponce<Float> getLatelyData(@RequestParam("mac") String mac) {
		CommonResponce<Float> response = new CommonResponce<Float>();
		float result = RealTimeWeightMapper.getLatelyData(mac);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
	}

	/**
	 * 사용안함.
	 * @param mac String
	 * @return CommonResponce<List<Float>>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/list/of/group.do")
	public CommonResponce<List<Float>> getRealTimeList(@RequestParam("mac") String mac) {
		CommonResponce<List<Float>> response = new CommonResponce<List<Float>>();
		List<Float> list = RealTimeWeightMapper.getRealTimeList(mac);
		if(list != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(list);
		}else if(list.size() == 0) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
	}
	
	
	/**
	 * 마지막 측정 데이터 ( 평균 )
	 * @param date yyyy-MM-dd
	 * @param groupCode String
	 * @param type 0 ? 1
	 * @param petIdx int
	 * @return CommonResponce<RealTimeWeight>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/last/collection/data.do", method = RequestMethod.POST)
	public CommonResponce<RealTimeWeight> getLastCollectionData(@RequestParam("date") String date, @RequestParam("groupCode") String groupCode, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		CommonResponce<RealTimeWeight> response = new CommonResponce<RealTimeWeight>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			RealTimeWeight weights = RealTimeWeightMapper.getListofDeviceList(date, deviceList, type, petIdx);
			if(weights != null) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(weights);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}	
		return response;
	}

	/**
	 * 시간대별 평균데이터.	
	 * 그래프에 사용
	 * 현제 사용안함.
	 * @param groupCode String
	 * @param today  yyyy-MM-dd
	 * @param type 0 ? 1
	 * @param petIdx int
	 * @return CommonResponce<List<Statistics>>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/time.do")
	public CommonResponce<List<Statistics>> getStatisticsOfTime(@RequestParam("groupCode") String groupCode, @RequestParam("today") String today, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		CommonResponce<List<Statistics>> response = new CommonResponce<List<Statistics>>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceList", deviceList);
			map.put("today", today);
			map.put("type", type);
			map.put("petIdx", petIdx);
			List<Statistics> list = RealTimeWeightMapper.getStatisticsOfTime(map);
			if(list.size() > 0) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(list);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}
		return response;
	}
	
	/**
	 * 일별 마지막 데이터.
	 * 그래프에 사용
	 * 
	 * @param groupCode String
	 * @param type 0 ? 1 
	 * @param date yyyy-MM-dd
	 * @param petIdx int
	 * @return CommonResponce<List<Statistics>>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/day.do")
	public CommonResponce<List<Statistics>> getStatisticsOfDay(
			@RequestParam("groupCode") String groupCode, 
			@RequestParam("type") int type,
			@RequestParam("date") String date,
			@RequestParam("petIdx") int petIdx) {
		CommonResponce<List<Statistics>> response = new CommonResponce<List<Statistics>>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceList", deviceList);
			map.put("type", type);
			map.put("petIdx", petIdx);
			map.put("date", date);
			map.put("startDate", net.octacomm.sample.utils.DateUtil.getCurMonday(date));
			map.put("endDate", net.octacomm.sample.utils.DateUtil.getCurSunday(net.octacomm.sample.utils.DateUtil.getCurMonday(date)));
			List<Statistics> list =  RealTimeWeightMapper.getStatisticsOfDay(map);
			if(list.size() > 0) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(list);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}
		return response;
	}
	
	
	/**
	 * 주별 평균 데이터
	 * 그래프에 사용
	 * 
	 * @param groupCode
	 * @param year yyyy
	 * @param month mm
	 * @param type 0 ? 1
	 * @param petIdx int
	 * @return CommonResponce<List<Statistics>>
	 */
	//using 주별 그래프
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/week.do")
	public CommonResponce<List<Statistics>> getStatisticsOfWeek(
			@RequestParam("groupCode") String groupCode,
			@RequestParam("year") String year , 
			@RequestParam("month") String month,
			@RequestParam("type") int type,
			@RequestParam("petIdx") int petIdx) {
		CommonResponce<List<Statistics>> response = new CommonResponce<List<Statistics>>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceList", deviceList);
			map.put("year", year);
			map.put("month", month);
			map.put("type", type);
			map.put("petIdx", petIdx);
			List<Statistics> list = RealTimeWeightMapper.getStatisticsOfWeek(map);
			if(list.size() > 0) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(list);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}
		return response;
	}
	
	/**
	 * 월별 평균 데이터
	 * 그래프에 사용
	 * 
	 * @param groupCode
	 * @param year yyyy
	 * @param month mm 
	 * @param type 0 ? 1
	 * @param petIdx int
	 * @return CommonResponce<List<Statistics>>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/month.do")
	public CommonResponce<List<Statistics>> getStatisticsOfMonth(
			@RequestParam("groupCode") String groupCode,
			@RequestParam("year") String year , 
			@RequestParam("month") String month, 
			@RequestParam("type") int type, 
			@RequestParam("petIdx") int petIdx) {
		CommonResponce<List<Statistics>> response = new CommonResponce<List<Statistics>>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			List<Statistics> list = RealTimeWeightMapper.getStatisticsOfMonth(deviceList, year, (Integer.parseInt(month) > 6 ? "up" : "down"), type, petIdx);
			if(list.size() > 0) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(list);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}
		return response;
	}
	
	
	/**
	 * 연도별 평균데이터 
	 * 그래프에 사용
	 * 현재 사용안함.
	 * 
	 * @param groupCode
	 * @param type
	 * @param petIdx
	 * @return CommonResponce<List<Statistics>>
	 */
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/year.do")
	public CommonResponce<List<Statistics>> getStatisticsOfYear(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		CommonResponce<List<Statistics>> response = new CommonResponce<List<Statistics>>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		if(deviceList.isEmpty()) {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			response.setResultMessage(ResultMessage.NOT_FOUND_DEVICE);
		}else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceList", deviceList);
			map.put("type", type);
			map.put("petIdx", petIdx);
			List<Statistics> list = RealTimeWeightMapper.getStatisticsOfYear(map);
			if(list.size() > 0) {
				response.setStatus(HodooConstant.OK_RESPONSE);
				response.setDomain(list);
			}else {
				response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
				response.setDomain(null);
			}
		}
		return response;
		
	}
	
	//체중 측정 PUSH Thread
	public class RealTimeThred extends Thread {
		private RealTimeWeight realTimeWeight;
		RealTimeThred ( RealTimeWeight realTimeWeight ) {
			this.realTimeWeight = realTimeWeight;
		}
		@Override
		public void run() {
			super.run();
			List<User> userList = RealTimeWeightMapper.getUserList(realTimeWeight.getMac());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < userList.size(); i++) {
				if ( userList.get(i).getPushToken() != null ) {
					Message message = new Message();
					message.setTo( userList.get(i).getPushToken() );
					Map<String, Object> data = new HashMap<>();
					data.put("notiType", HodooConstant.FIREBASE_WEIGHT_TYPE);
					data.put("title", "체중감지");
					data.put("content", "새로운 체중이 감지되었습니다. 측정체중 : " + realTimeWeight.getValue() + "kg");
					//data.put("title", "Weight detection");
					//data.put("content", "A new weight has been detected. Measured weight : " + realTimeWeight.getValue() + "kg");
					message.setData(data);
					
					FcmUtil.requestFCM(message);
				}
				//data.put("content", net.octacomm.sample.utils.DateUtil.getOnlyCurrentDateAndHour() + " 시 측정 결과 입니다.\n" + realTimeWeight.getType() + " : " + realTimeWeight.getValue() + "kg");
				//data.put("content", net.octacomm.sample.utils.DateUtil.getOnlyCurrentDateAndHour() + " 시 측정 결과 입니다.\n" + realTimeWeight.getType() + " : " + realTimeWeight.getValue() + "kg");
			}
		}
		
	}
	
}

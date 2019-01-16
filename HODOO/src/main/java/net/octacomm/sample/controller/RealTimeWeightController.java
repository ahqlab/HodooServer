package net.octacomm.sample.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.Statistics;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.FcmUtil;

@RequestMapping("/weight")
@Controller
public class RealTimeWeightController {

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private net.octacomm.sample.dao.mapper.RealTimeWeightMapper RealTimeWeightMapper;

	@ResponseBody
	@RequestMapping(value = "/realtime/get")
	public String regist(RealTimeWeight realTimeWeight) {
		RealTimeWeightMapper.insert(realTimeWeight);
		List<User> userList = RealTimeWeightMapper.getUserList(realTimeWeight.getMac());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < userList.size(); i++) {
			if ( userList.get(i).getPushToken() != null ) {
				Message message = new Message();
				message.setTo( userList.get(i).getPushToken() );
				
				Map<String, Object> data = new HashMap<>();
				data.put("notiType", HodooConstant.FIREBASE_NORMAL_TYPE);
				data.put("title", "측정결과");
				data.put("content", sdf.format(new Date()) + "일 측정 결과 입니다.\n" + realTimeWeight.getType() + " : " + realTimeWeight.getValue());
				message.setData(data);
				FcmUtil.requestFCM(message);
			}
			
		}

		return "HELLO";
	}
	
	// getLatelyData
	@ResponseBody
	@RequestMapping(value = "/get/lately/data")
	public Float getLatelyData(@RequestParam("mac") String mac) {
		return RealTimeWeightMapper.getLatelyData(mac);
	}

	@ResponseBody
	@RequestMapping(value = "/get/list/of/group")
	public List<Float> getRealTimeList(@RequestParam("mac") String mac) {
		return RealTimeWeightMapper.getRealTimeList(mac);
	}

	@ResponseBody
	@RequestMapping(value = "/get/last/collection/data")
	public RealTimeWeight getLastCollectionData(@RequestParam("date") String date, @RequestParam("groupCode") String groupCode, @RequestParam("type") int type) {
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		RealTimeWeight weights = RealTimeWeightMapper.getListofDeviceList(date, deviceList, type);
		return weights;
	}

	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/time")
	public List<Statistics> getStatisticsOfTime(@RequestParam("groupCode") String groupCode, @RequestParam("today") String today, @RequestParam("type") int type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("today", today);
		map.put("type", type);
		return RealTimeWeightMapper.getStatisticsOfTime(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/day")
	public List<Statistics> getStatisticsOfDay(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("type", type);
		return RealTimeWeightMapper.getStatisticsOfDay(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/week")
	public List<Statistics> getStatisticsOfWeek(@RequestParam("groupCode") String groupCode, @RequestParam("month") String month, @RequestParam("type") int type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("month", month);
		map.put("type", type);
		return RealTimeWeightMapper.getStatisticsOfWeek(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/month")
	public List<Statistics> getStatisticsOfMonth(@RequestParam("groupCode") String groupCode, @RequestParam("year") String year, @RequestParam("type") int type) {
		/*HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("deviceList", deviceList);
		map.put("year", year);*/
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		return RealTimeWeightMapper.getStatisticsOfMonth(deviceList, year, type);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/year")
	public List<Statistics> getStatisticsOfYear(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("type", type);
		return RealTimeWeightMapper.getStatisticsOfYear(map);
	}
	
}

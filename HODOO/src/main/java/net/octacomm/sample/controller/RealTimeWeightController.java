package net.octacomm.sample.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.Statistics;
import net.octacomm.sample.domain.Weight;

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
	public RealTimeWeight getLastCollectionData(@RequestParam("groupCode") String groupCode) {
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		RealTimeWeight weights = RealTimeWeightMapper.getListofDeviceList(deviceList);
		return weights;
	}

	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/time")
	public List<Statistics> getStatisticsOfTime(@RequestParam("groupCode") String groupCode, @RequestParam("today") String today) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("today", today);
		return RealTimeWeightMapper.getStatisticsOfTime(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/day")
	public List<Statistics> getStatisticsOfDay(@RequestParam("groupCode") String groupCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		return RealTimeWeightMapper.getStatisticsOfDay(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/week")
	public List<Statistics> getStatisticsOfWeek(@RequestParam("groupCode") String groupCode, @RequestParam("month") String month) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("month", month);
		return RealTimeWeightMapper.getStatisticsOfWeek(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/month")
	public List<Statistics> getStatisticsOfMonth(@RequestParam("groupCode") String groupCode, @RequestParam("year") String year) {
		/*HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("deviceList", deviceList);
		map.put("year", year);*/
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		return RealTimeWeightMapper.getStatisticsOfMonth(deviceList, year);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/year")
	public List<Statistics> getStatisticsOfYear(@RequestParam("groupCode") String groupCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		return RealTimeWeightMapper.getStatisticsOfYear(map);
	}
	
}

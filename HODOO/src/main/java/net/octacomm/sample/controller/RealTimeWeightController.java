package net.octacomm.sample.controller;

import java.text.SimpleDateFormat;
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
	@RequestMapping(value = "/realtime/get.do")
	public String regist(RealTimeWeight realTimeWeight) {
		RealTimeWeightMapper.insert(realTimeWeight);
		RealTimeThred thred = new RealTimeThred(realTimeWeight);
		thred.start();
		return "HELLO";
	}
	
	// getLatelyData
	@ResponseBody
	@RequestMapping(value = "/get/lately/data.do")
	public Float getLatelyData(@RequestParam("mac") String mac) {
		return RealTimeWeightMapper.getLatelyData(mac);
	}

	@ResponseBody
	@RequestMapping(value = "/get/list/of/group.do")
	public List<Float> getRealTimeList(@RequestParam("mac") String mac) {
		return RealTimeWeightMapper.getRealTimeList(mac);
	}

	@ResponseBody
	@RequestMapping(value = "/get/last/collection/data.do", method = RequestMethod.POST)
	public RealTimeWeight getLastCollectionData(@RequestParam("date") String date, @RequestParam("groupCode") String groupCode, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		RealTimeWeight weights = RealTimeWeightMapper.getListofDeviceList(date, deviceList, type, petIdx);
		return weights;
	}

	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/time.do")
	public List<Statistics> getStatisticsOfTime(@RequestParam("groupCode") String groupCode, @RequestParam("today") String today, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("today", today);
		map.put("type", type);
		map.put("petIdx", petIdx);
		return RealTimeWeightMapper.getStatisticsOfTime(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/day.do")
	public List<Statistics> getStatisticsOfDay(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("type", type);
		map.put("petIdx", petIdx);
		return RealTimeWeightMapper.getStatisticsOfDay(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/week.do")
	public List<Statistics> getStatisticsOfWeek(@RequestParam("groupCode") String groupCode, @RequestParam("month") String month, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("month", month);
		map.put("type", type);
		map.put("petIdx", petIdx);
		return RealTimeWeightMapper.getStatisticsOfWeek(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/month.do")
	public List<Statistics> getStatisticsOfMonth(@RequestParam("groupCode") String groupCode, @RequestParam("year") String year, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		/*HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("deviceList", deviceList);
		map.put("year", year);*/
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		return RealTimeWeightMapper.getStatisticsOfMonth(deviceList, year, type, petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/year.do")
	public List<Statistics> getStatisticsOfYear(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("type", type);
		map.put("petIdx", petIdx);
		return RealTimeWeightMapper.getStatisticsOfYear(map);
	}
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

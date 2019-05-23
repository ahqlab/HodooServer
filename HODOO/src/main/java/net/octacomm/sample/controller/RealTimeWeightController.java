package net.octacomm.sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.search.DateUtil;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.AlarmObjectMapper;
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
	
	@Autowired
	AlarmObjectMapper alarmObjectMapper;

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
	public List<Statistics> getStatisticsOfDay(@RequestParam("groupCode") String groupCode, @RequestParam("type") int type,  @RequestParam("date") String date,  @RequestParam("petIdx") int petIdx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("type", type);
		map.put("petIdx", petIdx);
		map.put("date", date);
		map.put("startDate", net.octacomm.sample.utils.DateUtil.getCurMonday(date));
		map.put("endDate", net.octacomm.sample.utils.DateUtil.getCurSunday(net.octacomm.sample.utils.DateUtil.getCurMonday(date)));
		return RealTimeWeightMapper.getStatisticsOfDay(map);
	}
	
	public void test() {
		Calendar calendar = Calendar.getInstance();
		System.out.println("이 달의 현재 주 : "+calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("이 달의 마지막 날 : "+calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.YEAR, Calendar.MONTH+1, calendar.getActualMaximum(Calendar.DATE));
		System.out.println("이 달의 마지막 주 : "+calendar.get(Calendar.WEEK_OF_MONTH));
	}
	
	
	//using 주별 그래프
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/week.do")
	public List<Statistics> getStatisticsOfWeek(@RequestParam("groupCode") String groupCode, @RequestParam("year") String year , @RequestParam("month") String month, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) throws ParseException {
		//List<Statistics> list = new ArrayList<Statistics>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		map.put("deviceList", deviceList);
		map.put("year", year);
		map.put("month", month);
		map.put("type", type);
		map.put("petIdx", petIdx);
		List<Statistics> result = RealTimeWeightMapper.getStatisticsOfWeek(map);
		for (Statistics statistics : result) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(statistics.getToday());
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
	        statistics.setTheWeek(week);
		}
		return result;
		/*Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Calendar.MONTH+1, calendar.getActualMaximum(Calendar.DATE));
		String thisYear = new SimpleDateFormat("yyyy").format(new Date());
		System.err.println("calendar.get(Calendar.DAY_OF_MONTH) : " + calendar.get(Calendar.WEEK_OF_MONTH));
		String week = String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
		System.err.println("week : " + week);
		
		for (int i = 1; i <= calendar.get(Calendar.WEEK_OF_MONTH) - 1; i++) {
			if(i == calendar.get(Calendar.WEEK_OF_MONTH)) {
				//이번주
				map.put("startDate", DateUtil.getMonday(thisYear, month, String.valueOf(i), 0));
				map.put("endDate", thisYear + "-" + month + "-" + Calendar.getInstance().getActualMaximum(Calendar.DATE));
				Statistics statistics = RealTimeWeightMapper.getAvgOfWeek(map);
				System.err.println("statistics 1 : " + statistics);
				if(statistics == null) {
					statistics = new Statistics();
					statistics.setAverage(0);
					
				}
				statistics.setTheWeek(String.valueOf(i));
				list.add(statistics);
			}else {
				if(i == 1) {
					//첫주
					map.put("startDate", thisYear + "-" + month + "-01");
					map.put("endDate", DateUtil.getMonday(thisYear, month, String.valueOf(i + 1) , 1));
					Statistics statistics = RealTimeWeightMapper.getAvgOfWeek(map);
					System.err.println("statistics 2 : " + statistics);
					if(statistics == null) {
						statistics = new Statistics();
						statistics.setAverage(0);
						
					}
					statistics.setTheWeek(String.valueOf(i));
					list.add(statistics);
					
					
				}else if(i > 1 && i < calendar.get(Calendar.WEEK_OF_MONTH)){
					//중간
					map.put("startDate", DateUtil.getMonday(thisYear, month, String.valueOf(i), 0));
					map.put("endDate", DateUtil.getMonday(thisYear, month, String.valueOf(i + 1) , 1));
					Statistics statistics = RealTimeWeightMapper.getAvgOfWeek(map);
					System.err.println("statistics 3 : " + statistics);
					if(statistics == null) {
						statistics = new Statistics();
						statistics.setAverage(0);
						
					}
					statistics.setTheWeek(String.valueOf(i));
					list.add(statistics);
				}
			}
		}
		for (Statistics st : list) {
			System.err.println(st);
		}*/
		
	/*	list.add(new Statistics("1", 0));
		list.add(new Statistics("2", 0));
		list.add(new Statistics("3", 0));
		list.add(new Statistics("4", 0));
		list.add(new Statistics("5", 0));
		list.add(new Statistics("6", 5));
		list.add(new Statistics("5", 5));
		list.add(new Statistics("6", 6));
		list.add(new Statistics("7", 7));
		list.add(new Statistics("8", 8));
		list.add(new Statistics("9", 9));
		return list;*/
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/statistics/list/of/month.do")
	public List<Statistics> getStatisticsOfMonth(@RequestParam("groupCode") String groupCode, @RequestParam("year") String year, @RequestParam("month") String month, @RequestParam("type") int type, @RequestParam("petIdx") int petIdx) {
		List<Device> deviceList = deviceMapper.myDeviceList(groupCode);
		return RealTimeWeightMapper.getStatisticsOfMonth(deviceList, year, (Integer.parseInt(month) > 6 ? "up" : "down"), type, petIdx);
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
					
					int number = alarmObjectMapper.getAlarm(userList.get(i).getUserIdx());
					if ( number != 1 && (number & (0x01 << HodooConstant.WEIGNT_ALARM)) == 0 )
						continue;
					
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

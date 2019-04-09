package net.octacomm.sample.controller.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.FeedMapper;
import net.octacomm.sample.dao.mapper.MealHistoryMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.MealHistoryContent;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.FcmUtil;

@RequestMapping("/android/history/meal")
@Controller
public class MealHistoryControllerForAndroid {

	@Autowired
	private MealHistoryMapper mealHistoryMapper;
	
	@Autowired PetMapper petMapper;
	
	@Autowired UserMapper userMapper;
	
	@Autowired FeedMapper feedMapper;

	@ResponseBody
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public CommonResponce<Integer> insert(@RequestBody MealHistory mealHistory) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = mealHistoryMapper.insert(mealHistory);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			FCMThead thead = new FCMThead(mealHistory);
			thead.start();
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public CommonResponce<Integer> update(@RequestBody MealHistory mealHistory) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = mealHistoryMapper.update(mealHistory);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/list.do")
	public CommonResponce<List<MealHistoryContent>> getList(@RequestParam("date") String date, @RequestParam("petIdx") int petIdx) {
		CommonResponce<List<MealHistoryContent>> response = new CommonResponce<List<MealHistoryContent>>();
		List<MealHistoryContent> contents = new ArrayList<MealHistoryContent>();
		List<MealHistory> result = mealHistoryMapper.getContentList(date, petIdx);
		if(result != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			for (MealHistory mealHistory : result) {
				MealHistoryContent content = new MealHistoryContent();
				content.setPetAllInfos(petMapper.allInfoOnThePet(mealHistory.getPetIdx()));
				content.setUser(userMapper.get(mealHistory.getUserIdx()));
				content.setFeed(feedMapper.get(mealHistory.getFeedIdx()));
				content.setMealHistory(mealHistory);
				contents.add(content);
			}
			response.setDomain(contents);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/today/sum/calorie.do", method = RequestMethod.POST)
	public CommonResponce<MealHistory> getTodatSumCalorie(@RequestParam("petIdx") int petIdx, @RequestParam("date") String date) {
		CommonResponce<MealHistory> response = new CommonResponce<MealHistory>();
		MealHistory obj = mealHistoryMapper.getTodatSumCalorie(petIdx, date);
		if(obj != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(obj);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/this/history.do", method = RequestMethod.POST)
	public CommonResponce<MealHistory> getThisHistory(@RequestParam("historyIdx") int historyIdx) {
		CommonResponce<MealHistory> response = new CommonResponce<MealHistory>();
		MealHistory obj =  mealHistoryMapper.get(historyIdx);
		if(obj != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(obj);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
		
		
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public CommonResponce<Integer> delete(@RequestParam("historyIdx") int historyIdx) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = mealHistoryMapper.delete(historyIdx);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
		}
		return response;
		
		
	}
	
	public class FCMThead extends Thread {
		private MealHistory mealHistory;
		FCMThead ( MealHistory mealHistory ){
			this.mealHistory = mealHistory;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			User user = userMapper.get(mealHistory.getUserIdx());
			
			List<User> groupUsers = userMapper.getGroupMemner(mealHistory.getGroupId());
			
			Feed feed = feedMapper.get(mealHistory.getFeedIdx());
			PetAllInfos pet = petMapper.allInfoOnThePet(mealHistory.getPetIdx());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < groupUsers.size(); i++) {
				if ( groupUsers.get(i).getPushToken() != null ) {
					Message message = new Message();
					message.setTo( groupUsers.get(i).getPushToken() );
					
					Map<String, Object> data = new HashMap<>();
					data.put("notiType", HodooConstant.FIREBASE_FEED_TYPE);
					data.put("title", "급식 알림");
					data.put("content", 
							sdf.format(new Date()) + "\n" 
							+ user.getNickname() + "님께서 " 
							+ feed.getBrand() + "의 " 
							+ feed.getName() + "을(를) " 
							+ pet.getPetBasicInfo().getPetName() + "에게 "
							+ String.valueOf(mealHistory.getCalorie()) + mealHistory.getUnitString() 
							+ "을 주었습니다." );
					
					/*data.put("title", "feeding notification");
					data.put("content", 
							sdf.format(new Date()) + "\n" 
									+ user.getNickname() + " gave " + feed.getBrand() + "'s '" + feed.getName() + "' to '" + pet.getPetBasicInfo().getPetName() + "' ("
									+ String.valueOf(mealHistory.getCalorie()) + mealHistory.getUnitString() 
									+ ")" );*/
					message.setData(data);
					FcmUtil.requestFCM(message);
				}
			}
			
		}
		
	}
}

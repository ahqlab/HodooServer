package net.octacomm.sample.controller;

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
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.MealHistoryContent;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.FcmUtil;

@RequestMapping("/history/meal")
@Controller
public class MealHistoryController {

	@Autowired
	private MealHistoryMapper mealHistoryMapper;
	
	@Autowired PetMapper petMapper;
	
	@Autowired UserMapper userMapper;
	
	@Autowired FeedMapper feedMapper;

	@ResponseBody
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public int insert(@RequestBody MealHistory mealHistory) {
		FCMThead thead = new FCMThead(mealHistory);
		thead.start();
		return mealHistoryMapper.insert(mealHistory);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public int update(@RequestBody MealHistory mealHistory) {
		return mealHistoryMapper.update(mealHistory);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/list.do")
	public List<MealHistoryContent> getList(@RequestParam("date") String date, @RequestParam("petIdx") int petIdx) {
		List<MealHistoryContent> contents = new ArrayList<MealHistoryContent>();
		for (MealHistory mealHistory : mealHistoryMapper.getContentList(date, petIdx)) {
			MealHistoryContent content = new MealHistoryContent();
			content.setPetAllInfos(petMapper.allInfoOnThePet(mealHistory.getPetIdx()));
			content.setUser(userMapper.get(mealHistory.getUserIdx()));
			content.setFeed(feedMapper.get(mealHistory.getFeedIdx()));
			content.setMealHistory(mealHistory);
			contents.add(content);
		}
		return contents;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/today/sum/calorie.do", method = RequestMethod.POST)
	public MealHistory getTodatSumCalorie(@RequestParam("petIdx") int petIdx, @RequestParam("date") String date, @RequestParam("language") String language) {
		return mealHistoryMapper.getTodatSumCalorie(petIdx, date, language);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/this/history.do", method = RequestMethod.POST)
	public MealHistory getThisHistory(@RequestParam("historyIdx") int historyIdx) {
		return mealHistoryMapper.get(historyIdx);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public int delete(@RequestParam("historyIdx") int historyIdx) {
		return mealHistoryMapper.delete(historyIdx);
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
				/*	data.put("title", "급식 알림");
					data.put("content", 
							sdf.format(new Date()) + "\n" 
							+ user.getNickname() + "님께서 " 
							+ feed.getBrand() + "의 " 
							+ feed.getName() + "을(를) " 
							+ pet.getPetBasicInfo().getPetName() + "에게 "
							+ String.valueOf(mealHistory.getCalorie()) + mealHistory.getUnitString() 
							+ "을 주었습니다." );*/
					data.put("title", "feeding notification");
					data.put("content", 
							sdf.format(new Date()) + "\n" 
									+ user.getNickname() + " gave " + feed.getBrand() + "'s '" + feed.getName() + "' to '" + pet.getPetBasicInfo().getPetName() + "' ("
									+ String.valueOf(mealHistory.getCalorie()) + mealHistory.getUnitString() 
									+ ")" );
					message.setData(data);
					FcmUtil.requestFCM(message);
				}
			}
			
		}
		
	}
}

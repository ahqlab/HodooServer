package net.octacomm.sample.controller.ios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.FeedMapper;
import net.octacomm.sample.dao.mapper.MealHistoryMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.MealHistoryContent;

@RequestMapping("/ios/history/meal")
@Controller
public class IOSMealHistoryController {

	@Autowired
	private MealHistoryMapper mealHistoryMapper;
	
	@Autowired PetMapper petMapper;
	
	@Autowired UserMapper userMapper;
	
	@Autowired FeedMapper feedMapper;

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public int insert(@RequestBody MealHistory mealHistory) {
		return mealHistoryMapper.insert(mealHistory);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public int update(@RequestBody MealHistory mealHistory) {
		return mealHistoryMapper.update(mealHistory);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/list", method = RequestMethod.POST)
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
	@RequestMapping(value = "/get/today/sum/calorie", method = RequestMethod.POST)
	public MealHistory getTodatSumCalorie(@RequestParam("petIdx") int petIdx) {
		return mealHistoryMapper.getTodatSumCalorie(petIdx);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/this/history", method = RequestMethod.POST)
	public MealHistory getThisHistory(@RequestParam("historyIdx") int historyIdx) {
		return mealHistoryMapper.get(historyIdx);
	}
}

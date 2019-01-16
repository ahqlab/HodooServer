package net.octacomm.sample.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.FeederOrderMapper;
import net.octacomm.sample.dao.mapper.MealHistoryMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.FeedOrders;
import net.octacomm.sample.domain.MealHistory;

@RequestMapping("/feeder")
@Controller
public class FeederController {

	@Autowired
	private MealHistoryMapper mealHistoryMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FeederOrderMapper feederOrderMapper;

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public int insert(@RequestBody FeedOrders feedOrders) {
		if (feederOrderMapper.getList().size() > 0) {
			feedOrders.setOrderIdx(feederOrderMapper.getList().get(0).getOrderIdx());
			return feederOrderMapper.update(feedOrders);
		}
		return feederOrderMapper.insert(feedOrders);
		
	}
	//Calories
	//OrderIdx
	//rer
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check() {
		if (feederOrderMapper.getList().size() > 0) {
			// 있다.
			FeedOrders order = feederOrderMapper.get(feederOrderMapper.getList().get(0).getOrderIdx());
			return "calories:" + order.getCalories() + "/orderIdx:" + order.getOrderIdx() + "/rer:" + order.getRer();
		} else {
			// 없다.
			return null;
		}
	}
	//Calories
	//OrderIdx
	@ResponseBody
	@RequestMapping(value = "/regist/meal", method = RequestMethod.GET)
	public int insertMealHistory(FeedOrders feedOrders) {
		MealHistory history = new MealHistory();
		FeedOrders orders = feederOrderMapper.get(feedOrders.getOrderIdx());
		history.setCalorie(feedOrders.getCalories());
		history.setFeedIdx(orders.getFeedIdx());
		history.setGroupId(orders.getGroupCode());
		history.setPetIdx(0); //없어도 되나??
		history.setUserIdx(orders.getUserIdx());
		history.setUnitIndex(1);
		history.setUnitString("g");
		if(mealHistoryMapper.insert(history) > 0) {
			return feederOrderMapper.delete(orders.getOrderIdx());
		}
		return 0;
	}
}

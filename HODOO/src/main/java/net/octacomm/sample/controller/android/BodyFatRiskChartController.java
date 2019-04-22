package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.BfiMapper;
import net.octacomm.sample.dao.mapper.WeightGoalChartMapper;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.WeightGoalChart;


@RequestMapping("/android/body/fat/chart")
@Controller
public class BodyFatRiskChartController {

	@Autowired
	private BfiMapper bfiMapper;
	
	@Autowired
	private WeightGoalChartMapper weightGoalChartMapper;

	// 1: 강아지 2 : 고양이, and location
	// do) 항문 가져온다
	@ResponseBody
	@RequestMapping(value = "/get/bfi.do", method = RequestMethod.POST)
	public CommonResponce<List<BfiModel>> getMyBfi(@RequestParam("location") String location,
			@RequestParam("type") int type) {
		CommonResponce<List<BfiModel>> responce = new CommonResponce<List<BfiModel>>();
		List<BfiModel> bfiModel = bfiMapper.getBfi(location, type);
		for (int i = 0; i < bfiModel.size(); i++) {
			bfiModel.get(i).setAnswers(bfiMapper.getBfiAnswer(bfiModel.get(i).getId()));
		}
		if (bfiModel.size() > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(bfiModel);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(bfiModel);
		}
		return responce;
	}

	@ResponseBody
	@RequestMapping(value = "/get/weight/goal.do", method = RequestMethod.POST)
	public CommonResponce<WeightGoalChart> createGoalKg(@RequestParam("currentWeight") float currentWeight,
			@RequestParam("bodyFat") int bodyFat, @RequestParam("petType") int petType) {
		CommonResponce<WeightGoalChart> responce = new CommonResponce<WeightGoalChart>();
		WeightGoalChart obj = weightGoalChartMapper.getWeightGoal(currentWeight, bodyFat, petType);
		if (obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
}

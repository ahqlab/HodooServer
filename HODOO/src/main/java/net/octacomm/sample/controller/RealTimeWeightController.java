package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.Weight;


@RequestMapping("/weight")
@Controller
public class RealTimeWeightController {
	
	@Autowired 
	private net.octacomm.sample.dao.mapper.RealTimeWeightMapper RealTimeWeightMapper;
	
	@ResponseBody
	@RequestMapping(value = "/realtime/get")
	public String regist(RealTimeWeight realTimeWeight) {
		RealTimeWeightMapper.insert(realTimeWeight);
		return "HELLO";
	}
	
	//getLatelyData
	@ResponseBody
	@RequestMapping(value = "/get/lately/data")
	public Float getLatelyData(@RequestParam ("mac") String mac) {
		return RealTimeWeightMapper.getLatelyData(mac);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/list/of/group")
	public List<Float> getRealTimeList(@RequestParam ("mac") String mac) {
		return RealTimeWeightMapper.getRealTimeList(mac);
	}
}

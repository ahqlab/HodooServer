package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.FeedMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Feed;

@RequestMapping("/feed")
@Controller
public class FeedController extends AbstractCRUDController<FeedMapper, Feed, DefaultParam, Integer>{
	
	@Autowired
	@Override
	public void setCRUDMapper(FeedMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected Class<Feed> getDomainClass() {
		return Feed.class;
	}

	@Override
	protected String getRedirectUrl() {
		return "redirect:/feed/list.do";
	}
		
	@ResponseBody
	@RequestMapping(value = "/all/list.do", method = RequestMethod.POST)
	public List<Feed> allList() {
		return mapper.getList();
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/list.do", method = RequestMethod.POST)
	public List<Feed> searchList(@RequestBody DefaultParam defaultParam, @RequestParam("language") String language) {
		return mapper.getSearchList(defaultParam.getSearchWord(), language);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get/info.do", method = RequestMethod.POST)
	public Feed getFeedInfo(@RequestParam("feedId") int id) {
		return  mapper.get(id);
	}
		
	
	@ResponseBody
	@RequestMapping(value = "/get/radar/chart/data.do", method = RequestMethod.POST)
	public Feed getRadarChartData(@RequestParam("date")  String date, @RequestParam("petIdx") int petIdx) {
		System.err.println("mapper.getRadarChartData(date, petIdx) : " + mapper.getRadarChartData(date, petIdx));
		return mapper.getRadarChartData(date, petIdx);
	}
	
	
}

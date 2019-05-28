package net.octacomm.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.protocol.ResponseContentEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import net.octacomm.sample.dao.mapper.FeedMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.User;

@RequestMapping("/feed")
@Controller
public class FeedController extends AbstractCRUDController<FeedMapper, Feed, DefaultParam, Integer> {

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
	
	/**
	 * 사료를 등록한다.
	 * @param feed
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/regist2.do", method = RequestMethod.POST)
	public int regist(@RequestBody Feed feed) {
		return mapper.insert(feed);
	}
	
	
	/**
	 * 모든 사료 리스트를 리턴한다.
	 * @return List<Feed>
	 */
	@ResponseBody
	@RequestMapping(value = "/all/list.do", method = RequestMethod.POST)
	public List<Feed> allList() {
		return mapper.getList();
	}
	
	
	/**
	 * 인자로 받은 이름으로 검색한 사료를 리턴한다.
	 * @param defaultParam 검색어
	 * @param language 언어
	 * @return List<Feed>
	 */ 
	@ResponseBody
	@RequestMapping(value = "/search/list.do", method = RequestMethod.POST)
	public List<Feed> searchList(@RequestBody DefaultParam defaultParam, @RequestParam("language") String language) {
		return mapper.getSearchList(defaultParam.getSearchWord(), language);
	}

	
	/* 사용 안함 */
	/*@ResponseBody
	@RequestMapping(value = "/search/listStr.do", method = RequestMethod.POST)
	public String searchIds(@RequestBody DefaultParam defaultParam, @RequestParam("language") String language) {
		List<Feed> list = mapper.getSearchList(defaultParam.getSearchWord(), language);
		Gson gson = new Gson();
		String convert = gson.toJson(list);
		System.out.println("==============================================");
		System.out.println("convert : " + convert);
		System.out.println("==============================================");
		return convert;
	}*/
	
	/**
	 * 인자로 받은 아이디에 해당하는 사료의 정보를 가져온다.
	 * @param idx
	 * @return Feed
	 */
	@ResponseBody
	@RequestMapping(value = "/search/get.do", method = RequestMethod.POST)
	public Feed searchIds(@RequestParam("idx") int idx) {
		return mapper.get(idx);
	}

	/**
	 * 인자로 받은 아이디에 해당하는 사료의 정보를 가져온다.
	 * @param idx
	 * @return Feed
	 */
	@ResponseBody
	@RequestMapping(value = "/get/info.do", method = RequestMethod.POST)
	public Feed getFeedInfo(@RequestParam("feedId") int id) {
		return mapper.get(id);
	}
	
	
	/**
	 * 섭취한 사료의 칼로리 퍼센테이지를 리턴한다.
	 * @param date (날짜)
	 * @param petIdx (펫 아이디)
	 * @return Feed
	 */
	@ResponseBody
	@RequestMapping(value = "/get/radar/chart/data.do", method = RequestMethod.POST)
	public Feed getRadarChartData(@RequestParam("date") String date, @RequestParam("petIdx") int petIdx) {
		return mapper.getRadarChartData(date, petIdx);
	}
	
	/**
	 * 사용안함.
	 * @param servletResponse
	 * @param date
	 * @param petIdx
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tetest.do")
	public ResponseEntity<User> test(HttpServletResponse servletResponse, @RequestParam("date") String date,
			@RequestParam("petIdx") int petIdx, BindingResult bindingResult) {

		try {
			/*
			 * if (bindingResult.hasErrors()) { System.err.println("servletResponse 1 : " +
			 * servletResponse.getStatus()); return new User(); } return new User();
			 */
		} catch (Exception e) {
			System.err.println("servletResponse : " + servletResponse.getStatus());

		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 사용안함.
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() { // Nothing to do

	}

}

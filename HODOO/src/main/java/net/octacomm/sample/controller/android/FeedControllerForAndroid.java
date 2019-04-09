package net.octacomm.sample.controller.android;

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

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.controller.AbstractCRUDController;
import net.octacomm.sample.dao.mapper.FeedMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.User;

@RequestMapping("/android/feed")
@Controller
public class FeedControllerForAndroid extends AbstractCRUDController<FeedMapper, Feed, DefaultParam, Integer> {

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
	
	/* Used at the 호두야 밥먹자 App */
	@ResponseBody
	@RequestMapping(value = "/regist2.do", method = RequestMethod.POST)
	public int regist(@RequestBody Feed feed) {
		return mapper.insert(feed);
	}

	/* Used at the 호두야 밥먹자 App */
	@ResponseBody
	@RequestMapping(value = "/all/list.do", method = RequestMethod.POST)
	public List<Feed> allList() {
		return mapper.getList();
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/search/list.do", method = RequestMethod.POST)
	public CommonResponce<List<Feed>> searchList(@RequestBody DefaultParam defaultParam, @RequestParam("language") String language) {
		CommonResponce<List<Feed>> responce  = new CommonResponce<>(); 
		List<Feed> list =  mapper.getSearchList(defaultParam.getSearchWord(), language);
		if(list == null) {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(null);	
		}else if(list.size() == 0) {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(null);
		}else {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(list);
		}
		return responce;
	}

	
	/* ??????? */
	@ResponseBody
	@RequestMapping(value = "/search/listStr.do", method = RequestMethod.POST)
	public String searchIds(@RequestBody DefaultParam defaultParam, @RequestParam("language") String language) {
		List<Feed> list = mapper.getSearchList(defaultParam.getSearchWord(), language);
		Gson gson = new Gson();
		String convert = gson.toJson(list);
		System.out.println("==============================================");
		System.out.println("convert : " + convert);
		System.out.println("==============================================");
		return convert;
	}

	@ResponseBody
	@RequestMapping(value = "/search/get.do", method = RequestMethod.POST)
	public CommonResponce<Feed> searchIds(@RequestParam("idx") int idx) {
		CommonResponce<Feed> response = new CommonResponce<Feed>();
		Feed obj = mapper.get(idx);
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
	@RequestMapping(value = "/get/info.do", method = RequestMethod.POST)
	public CommonResponce<Feed> getFeedInfo(@RequestParam("feedId") int id) {
		CommonResponce<Feed> response = new CommonResponce<Feed>();
		Feed obj = mapper.get(id);
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
	@RequestMapping(value = "/get/radar/chart/data.do", method = RequestMethod.POST)
	public CommonResponce<Feed> getRadarChartData(@RequestParam("date") String date, @RequestParam("petIdx") int petIdx) {
		CommonResponce<Feed> response = new CommonResponce<Feed>();
		Feed obj = mapper.getRadarChartData(date, petIdx);
		if(obj != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(obj);	
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);	
		}
		return response;
	}

}

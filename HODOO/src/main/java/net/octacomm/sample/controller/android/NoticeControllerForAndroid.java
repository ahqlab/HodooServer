package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.controller.AbstractCRUDController;
import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.dao.mapper.AlarmMapper;
import net.octacomm.sample.dao.mapper.NoticeMapper;
import net.octacomm.sample.domain.AlarmItem;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Notice;
import net.octacomm.sample.domain.param.NoticeParam;


@RequestMapping("/android/notice")
@Controller
public class NoticeControllerForAndroid {
	
	@Autowired
	private NoticeMapper noticeMapper;

	
	@ResponseBody
	@RequestMapping(value = "/list.do", method = RequestMethod.POST)
	public CommonResponce<List<Notice>> searchList(@RequestParam("language") String language) {
		CommonResponce<List<Notice>> responce  = new CommonResponce<List<Notice>>(); 
		List<Notice> list =  noticeMapper.geNoticetList(language);
		responce.setDomain(list);
		if(list.size() > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}

}

package net.octacomm.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.dao.mapper.NoticeMapper;
import net.octacomm.sample.domain.Notice;
import net.octacomm.sample.domain.param.NoticeParam;


@RequestMapping("/notice")
@Controller
public class NoticeController
/*extends AbstractCRUDController<NoticeMapper, Notice, NoticeParam, Integer>*/
{
	
	/*@Autowired
	@Override
	public void setCRUDMapper(NoticeMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected Class<Notice> getDomainClass() {
		return Notice.class;
	}

	@Override
	protected String getRedirectUrl() {
		return "redirect:/notice/list.do";
	}
	
	
	@RequestMapping("ex.do")
	public void test() {
		System.err.println("asdasdlkajsdlkajsd");
	}*/

}

package net.octacomm.sample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.domain.User;


@RequestMapping("/error")
@Controller
public class ErrorController {

	@ResponseBody
	@RequestMapping(value = "/pageNotFound.do")
	public User test() {
		return new User();
	}
}

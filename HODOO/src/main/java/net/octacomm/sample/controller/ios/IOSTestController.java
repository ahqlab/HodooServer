package net.octacomm.sample.controller.ios;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.domain.User;

@RequestMapping("/ios/test")
@Controller
public class IOSTestController {
	
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public int invitation2 (@RequestBody User user, @RequestParam ("name") String name) {
		System.err.println("userIdx : " + user.getUserIdx());
		System.err.println("name : " + name);
		return 0;
	}

}

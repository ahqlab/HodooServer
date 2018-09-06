package net.octacomm.sample.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.ResultMessage;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(@RequestBody User user) {
		ResultMessageGroup group = new ResultMessageGroup();
		user.setGroupId(MathUtil.getGroupId());
		int result = userMapper.insert(user);
		if(result != 0) {
			group.setResultMessage(ResultMessage.SUCCESS);
			group.setDomain(result);
		}else {
			group.setResultMessage(ResultMessage.FAILED);
			group.setDomain(result);
		}
		return group;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody User user) {
		User result = userMapper.login(user);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/group/member", method = RequestMethod.POST)
	public List<User> login(@RequestParam("groupId") String groupId) {
		return userMapper.getGroupMemner(groupId);
	}
	
}

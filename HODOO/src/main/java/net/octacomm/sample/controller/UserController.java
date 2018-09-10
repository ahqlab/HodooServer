package net.octacomm.sample.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.ResultMessage;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupMappingMapper userGroupMappingMapper; 
	
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(@RequestBody User param) {
		//그룹을 만든다 (그룹아이디를 가져온다)
		Groups groups = groupsService.createGroups();
		User user = userService.createGroups(param);
		UserGroupMapping groupMapping = new UserGroupMapping();
		groupMapping.setGroupId(groups.getId());
		groupMapping.setUserId(user.getId());
		
		int result = userGroupMappingMapper.insert(groupMapping);
		ResultMessageGroup group = new ResultMessageGroup();
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

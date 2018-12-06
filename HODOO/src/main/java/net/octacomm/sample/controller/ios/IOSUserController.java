package net.octacomm.sample.controller.ios;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.repackaged.com.google.api.client.http.HttpHeaders;

import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/ios/user")
@Controller
public class IOSUserController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GroupsService groupsService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupMappingMapper userGroupMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/regist")
	public ResultMessageGroup regist(User param) {
		System.err.println("param : " + param);
		// 그룹을 만든다 (그룹아이디를 가져온다)
		/*
		 * User user = userService.createGroups(param); UserGroupMapping groupMapping =
		 * new UserGroupMapping(); groupMapping.setUserIdx(user.getUserIdx());
		 * groupMapping.setGroupCode(MathUtil.getGroupId()); int result =
		 * userGroupMappingMapper.insert(groupMapping); ResultMessageGroup group = new
		 * ResultMessageGroup(); if(result != 0) {
		 * group.setResultMessage(ResultMessage.SUCCESS); group.setDomain(result); }else
		 * { group.setResultMessage(ResultMessage.FAILED); group.setDomain(result); }
		 * return group;
		 */

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/test1", produces = "application/json")
	public User test1(User param, @RequestHeader HttpHeaders headers) {
		System.err.println("headers : " + headers.toString());
		return param;
	}

	@ResponseBody
	@RequestMapping(value = "/test2",  method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public User test2(@RequestBody User param, HttpServletResponse response) {
		response.setContentType("application/json");
		System.err.println("param : " + param);
		return param;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(User user) {
		User result = userMapper.login(user);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/get/group/member", method = RequestMethod.POST)
	public List<User> login(@RequestParam("groupId") String groupId) {
		return userMapper.getGroupMemner(groupId);
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public User get(@RequestParam("userIdx") int userIdx) {
		return userMapper.get(userIdx);
	}

	@ResponseBody
	@RequestMapping(value = "/update/basic/info", method = RequestMethod.POST)
	public Integer updateBasic(User user) {
		return userMapper.updateBasic(user);
	}
}

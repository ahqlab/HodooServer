package net.octacomm.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.LoginService;
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

	@Autowired
	private LoginService loginService;


	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(@RequestBody User param) {
		// 그룹을 만든다 (그룹아이디를 가져온다)
		String grougCode = MathUtil.getGroupId();
		List<User> findUser = userMapper.getUserList(param);
		ResultMessageGroup group = new ResultMessageGroup();
		User user = null;
		int result = 0;
		if (findUser.size() != 0) {
			group.setResultMessage(ResultMessage.DUPLICATE_EMAIL);
			group.setDomain(null);
		} else {
			user = userService.createUser(param);
			user.setGroupCode(grougCode);
			UserGroupMapping groupMapping = new UserGroupMapping();
			groupMapping.setUserIdx(user.getUserIdx());
			groupMapping.setGroupCode(grougCode);
			result = userGroupMappingMapper.insert(groupMapping);
			if (result != 0) {
				group.setResultMessage(ResultMessage.SUCCESS);
				group.setDomain(user);
			} else {
				group.setResultMessage(ResultMessage.FAILED);
				group.setDomain(null);
			}
		}
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResultMessageGroup login(@RequestBody User user) {
		System.err.println("user : " + user);
		ResultMessageGroup group = loginService.login(user);
		return group;
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
	public Integer updateBasic(@RequestBody User user) {
		System.err.println("user L " + user);
		return userMapper.updateBasic(user);
	}
}

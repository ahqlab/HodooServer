package net.octacomm.sample.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.gson.Gson;

import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.LoginService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.AES256Util;
import net.octacomm.sample.utils.MathUtil;
import net.octacomm.sample.utils.RSA;

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
	public ResultMessageGroup regist(@RequestBody User param) throws FirebaseMessagingException {
		// 그룹을 만든다 (그룹아이디를 가져온다)
		
	/*	if (!FirebaseApp.getApps().isEmpty()) {
	        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
		}
		List<String> registrationTokens = Arrays.asList(param.getPushToken());
		TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, "ALL");
		System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");*/
		
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
	@RequestMapping(value = "/regist2", method = RequestMethod.POST)
	public SessionMaintenance regist2(@RequestBody User param) throws FirebaseMessagingException {
		String grougCode = MathUtil.getGroupId();
		List<User> findUser = userMapper.getUserList(param);
		SessionMaintenance group;
		User user = null;
		int result = 0;
		if (findUser.size() != 0) {
			group = new SessionMaintenance();
			group.setResultMessage(ResultMessage.DUPLICATE_EMAIL);
			//group.setDomain(null);
		} else {
			user = userService.createUser(param);
			user.setGroupCode(grougCode);
			UserGroupMapping groupMapping = new UserGroupMapping();
			groupMapping.setUserIdx(user.getUserIdx());
			groupMapping.setGroupCode(grougCode);
			result = userGroupMappingMapper.insert(groupMapping);
			if (result != 0) {
				group = loginService.getAllInfoLogin(user);
				group.setResultMessage(ResultMessage.SUCCESS);
			} else {
				group = loginService.getAllInfoLogin(user);
				group.setResultMessage(ResultMessage.FAILED);
			}
		}
		return group;
	}

	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public SessionMaintenance login(@RequestBody User user) {
		SessionMaintenance group = loginService.login(user);
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public ResultMessageGroup login2(@RequestBody User user) {
		ResultMessageGroup group = loginService.login2(user);
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/get/group/member", method = RequestMethod.POST)
	public List<User> login(@RequestParam("groupCode") String groupCode) {
		return userMapper.getGroupMemner(groupCode);
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public User get(@RequestParam("userIdx") int userIdx) {
		return userMapper.get(userIdx);
	}

	@ResponseBody
	@RequestMapping(value = "/update/basic/info", method = RequestMethod.POST)
	public int updateBasic(@RequestBody User user) {
		System.err.println("user L " + user);
		return userMapper.updateBasic(user);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update/user/password", method = RequestMethod.POST)
	public int updateUsetPassword(@RequestBody User user) {
		return userMapper.updateUsetPassowrd(user);
	}
	@RequestMapping( value="/checkUserCertifiedMail", method=RequestMethod.GET )
	public ModelAndView checkUserCertifiedMail(
			@RequestParam("code") String code) {
		String codeD = "";
		try {
			codeD = new AES256Util().decrypt(code);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String split[] = codeD.split("day");
		User tempUser = new User();
		tempUser.setEmail(split[0].toString());
		User user = userMapper.getUser(tempUser);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long now = System.currentTimeMillis();
		long compare = 0;
		try {
			
			compare = sdf.parse(split[1]).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int state = 0;
		if ( user.getUserCode() == 0 ) { //검증 안됨
			/* 날짜 검증 */
			if ( now - compare > 24 * 60 * 60 * 1000 ) {
				state = -2;
			} else {
				user.setUserCode(1);
				int result = userMapper.updateForUsercode(user);
				state = result;
			}
			
		} else {
			state = -1;
		}
		
		
 		ModelAndView mav = new ModelAndView("signup_confirm");
		mav.addObject("state", state);
		return mav;
	}
	@RequestMapping( value="/welcomeSignup", method=RequestMethod.GET )
	public ModelAndView welcomeSignup() {
		ModelAndView mav = new ModelAndView("welcome_signup");
		return mav;
	}
}

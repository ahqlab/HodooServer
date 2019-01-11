/*<<<<<<< HEAD
package net.octacomm.sample.controller;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.firebase.messaging.FirebaseMessagingException;
import net.octacomm.sample.dao.mapper.FirebaseMapper;
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.LoginService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.AES256Util;
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
	FirebaseMapper firebaseMapper;
	@Autowired
	private LoginService loginService;
	@Autowired
	private JavaMailSender mailSender;
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(@RequestBody User param) throws FirebaseMessagingException {
		
		// 그룹을 만든다 (그룹아이디를 가져온다)
		
		 * if (!FirebaseApp.getApps().isEmpty()) {
		 * FirebaseDatabase.getInstance().setPersistenceEnabled(true); } List<String>
		 * registrationTokens = Arrays.asList(param.getPushToken());
		 * TopicManagementResponse response =
		 * FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, "ALL");
		 * System.out.println(response.getSuccessCount() +
		 * " tokens were subscribed successfully");
		 
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
			// group.setDomain(null);
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
		System.err.println("user : " + user);
		SessionMaintenance group = loginService.login(user);
		return group;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login3", method = RequestMethod.GET)
	public SessionMaintenance login3(User user) {
		SessionMaintenance group = loginService.login(user);
		return group;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public CommonResponce<User> login2(@RequestBody User user) {
		CommonResponce<User> group = loginService.login2(user);
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
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password2", method = RequestMethod.POST)
	public int findUserPasswordTest() {
		return 0;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password", method = RequestMethod.POST)
	public CommonResponce<User> changeUserPassword(@RequestParam("email") String email) {
		CommonResponce<User> responce = new CommonResponce<User>();
		User returnUser = userMapper.getByUserEmail(email);
		if (returnUser == null) {
			// not found user;
			// 존재하지 않는 유저입니다.
			return null;
		} else {
			// 난수 발생
			String tempPassword = createTempPassword();
			// 이전 비밀번호 저장
			String oldPassword = userMapper.get(returnUser.getUserIdx()).getPassword();
			// 난수로 비밀번호 업데이트
			returnUser.setPassword(tempPassword);
			int result = userMapper.updateUsetPassowrd(returnUser);
			if (result != 0) {
				// 비밀번호 업데이트 성공
				boolean mailResult = sendEmail(email, "[호두 스케일]임시비밀번호 발급", "고객님의 임시비밀번호는 [" + tempPassword + " ] 입니다.");
				if (mailResult) {
					// 메일 전송 성공
					responce.setDomain(returnUser);
					responce.setResultMessage(ResultMessage.SUCCESS);
					System.err.println("메일 전송 성공");
					System.err.println("responce : " + responce);
					System.err.println("mailResult : " + mailResult);
					return responce;
				} else {
					// 리턴 메일 전송 실패
					responce.setDomain(returnUser);
					responce.setResultMessage(ResultMessage.FAILED);
					return responce;
				}
			} else {
				// 비밀번호 업데이트 실패
				// 이전 비밀번호로 다시 업데이트
				returnUser.setPassword(oldPassword);
				userMapper.updateUsetPassowrd(returnUser);
				responce.setDomain(returnUser);
				responce.setResultMessage(ResultMessage.PASSWORD_UPDATE_FAILED);
				return responce;
				// 리턴 비밀번호 변경 오류
			}
		}
	}
	public String createTempPassword() {
		Random rnd = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			if (rnd.nextBoolean()) {
				buf.append((char) ((int) (rnd.nextInt(26)) + 97));
			} else {
				buf.append((rnd.nextInt(10)));
			}
		}
		return buf.toString();
	}
	public boolean sendEmail(String toMail, String title, String content) {
		String setfrom = "hellomyhodoo@gmail.com";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom, "호두스케일"); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(toMail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	
	}
	@RequestMapping(value = "/checkUserCertifiedMail", method = RequestMethod.GET)
	public ModelAndView checkUserCertifiedMail(@RequestParam("code") String code) {
		String codeD = "";
		try {
			codeD = new AES256Util().decrypt(code);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String split[] = codeD.split("day");
		User tempUser = new User();
		tempUser.setEmail(split[0].toString());
		User user = userMapper.getUser(tempUser);
		int state = 0;
		if (user.getUserCode() == 0) { // 검증 안됨
			user.setUserCode(1);
			int result = userMapper.updateForUsercode(user);
			state = result;
		} else {
			state = -1;
		}
		ModelAndView mav = new ModelAndView("signup_confirm");
		mav.addObject("state", state);
		return mav;
	}
	@RequestMapping(value = "/welcomeSignup", method = RequestMethod.GET)
	public ModelAndView welcomeSignup() {
		ModelAndView mav = new ModelAndView("welcome_signup");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "/update/fcmToken", method = RequestMethod.POST)
	public int saveFCMToken( @RequestBody User user ) {
		return userService.saveFCMToken(user);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/approval", method = RequestMethod.POST)
	public int invitationApproval( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return userGroupMappingMapper.invitationApproval(toUserIdx, fromUserIdx);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/refusal", method = RequestMethod.POST)
	public int invitationRefusal( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return firebaseMapper.invitationRefusal(toUserIdx, fromUserIdx);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/getInvitationUser", method = RequestMethod.POST)
	public List<InvitationRequest> getInvitationList( 
			@RequestParam("userIdx") int userIdx) {
		return firebaseMapper.getInvitationList(userIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/invitation/setInvitationType", method = RequestMethod.POST)
	public int setInvitationType( 
			@RequestParam("type") int type,
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return firebaseMapper.setInvitationType(type, toUserIdx, fromUserIdx);
	}
}
=======*/
package net.octacomm.sample.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.firebase.messaging.FirebaseMessagingException;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.FirebaseMapper;
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.LoginService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.AES256Util;
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
	FirebaseMapper firebaseMapper;

	@Autowired
	private LoginService loginService;

	@Autowired
	private JavaMailSender mailSender;

	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(@RequestBody User param) throws FirebaseMessagingException {
		
		// 그룹을 만든다 (그룹아이디를 가져온다)
		/*
		 * if (!FirebaseApp.getApps().isEmpty()) {
		 * FirebaseDatabase.getInstance().setPersistenceEnabled(true); } List<String>
		 * registrationTokens = Arrays.asList(param.getPushToken());
		 * TopicManagementResponse response =
		 * FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, "ALL");
		 * System.out.println(response.getSuccessCount() +
		 * " tokens were subscribed successfully");
		 */

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
			// group.setDomain(null);
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
	@RequestMapping(value = "/login3", method = RequestMethod.GET)
	public SessionMaintenance login3(User user) {
		SessionMaintenance group = loginService.login(user);
		return group;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public CommonResponce<User> login2(@RequestBody User user) {
		CommonResponce<User> group = loginService.login2(user);
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
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password2", method = RequestMethod.POST)
	public int findUserPasswordTest() {
		return 0;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password", method = RequestMethod.POST)
	public CommonResponce<User> changeUserPassword(@RequestParam("email") String email) {
		CommonResponce<User> responce = new CommonResponce<User>();
		User returnUser = userMapper.getByUserEmail(email);
		if (returnUser == null) {
			// not found user;
			// 존재하지 않는 유저입니다.
			return null;
		} else {
			// 난수 발생
			String tempPassword = createTempPassword();
			// 이전 비밀번호 저장
			String oldPassword = userMapper.get(returnUser.getUserIdx()).getPassword();
			// 난수로 비밀번호 업데이트
			returnUser.setPassword(tempPassword);
			int result = userMapper.updateUsetPassowrd(returnUser);
			if (result != 0) {
				// 비밀번호 업데이트 성공
				boolean mailResult = sendEmail(email, "[호두 스케일]임시비밀번호 발급", "고객님의 임시비밀번호는 [" + tempPassword + " ] 입니다.");
				if (mailResult) {
					// 메일 전송 성공
					responce.setDomain(returnUser);
					responce.setResultMessage(ResultMessage.SUCCESS);
					/*System.err.println("메일 전송 성공");
					System.err.println("responce : " + responce);
					System.err.println("mailResult : " + mailResult);*/
					return responce;
				} else {
					// 리턴 메일 전송 실패
					responce.setDomain(returnUser);
					responce.setResultMessage(ResultMessage.FAILED);
					return responce;
				}
			} else {
				// 비밀번호 업데이트 실패
				// 이전 비밀번호로 다시 업데이트
				returnUser.setPassword(oldPassword);
				userMapper.updateUsetPassowrd(returnUser);
				responce.setDomain(returnUser);
				responce.setResultMessage(ResultMessage.PASSWORD_UPDATE_FAILED);
				return responce;
				// 리턴 비밀번호 변경 오류
			}
		}
	}

	public String createTempPassword() {
		Random rnd = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			if (rnd.nextBoolean()) {
				buf.append((char) ((int) (rnd.nextInt(26)) + 97));
			} else {
				buf.append((rnd.nextInt(10)));
			}
		}
		return buf.toString();
	}

	public boolean sendEmail(String toMail, String title, String content) {
		String setfrom = "hellomyhodoo@gmail.com";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom, "호두스케일"); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(toMail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	
	}

	@RequestMapping(value = "/checkUserCertifiedMail", method = RequestMethod.GET)
	public ModelAndView checkUserCertifiedMail(@RequestParam("code") String code) {
		String codeD = "";
		try {
			codeD = new AES256Util().decrypt(code);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String split[] = codeD.split("day");
		User tempUser = new User();
		tempUser.setEmail(split[0].toString());
		User user = userMapper.getUser(tempUser);

		int state = 0;
		if (user.getUserCode() == 0) { // 검증 안됨
			user.setUserCode(1);
			int result = userMapper.updateForUsercode(user);
			state = result;
		} else {
			state = -1;
		}
		ModelAndView mav = new ModelAndView("signup_confirm");
		mav.addObject("state", state);
		return mav;
	}

	@RequestMapping(value = "/welcomeSignup", method = RequestMethod.GET)
	public ModelAndView welcomeSignup() {
		ModelAndView mav = new ModelAndView("welcome_signup");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "/update/fcmToken", method = RequestMethod.POST)
	public int saveFCMToken( @RequestBody User user ) {
		return userService.saveFCMToken(user);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/approval", method = RequestMethod.POST)
	public int invitationApproval( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return userGroupMappingMapper.invitationApproval(toUserIdx, fromUserIdx);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/refusal", method = RequestMethod.POST)
	public int invitationRefusal( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return firebaseMapper.invitationRefusal(toUserIdx, fromUserIdx);
	}
	@ResponseBody
	@RequestMapping(value = "/invitation/getInvitationUser", method = RequestMethod.POST)
	public List<InvitationRequest> getInvitationList( 
			@RequestParam("userIdx") int userIdx) {
		return firebaseMapper.getInvitationList(userIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/invitation/setInvitationType", method = RequestMethod.POST)
	public int setInvitationType( 
			@RequestParam("type") int type,
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		return firebaseMapper.setInvitationType(type, toUserIdx, fromUserIdx);
	}
	@ResponseBody
	@RequestMapping(value = "/setUserCode", method = RequestMethod.POST)
	public int setInvitationType(
			@RequestParam("idx") int idx,
			@RequestParam("type") int code) {
		User user = userMapper.get(idx);
		user.setUserCode(code);
		int result = 0;
		if ( userGroupMappingMapper.delete( userGroupMappingMapper.getUserGroupId(idx) ) > 0 ) {
			if ( userMapper.updateForUsercode(user) > 0 ) {
				result = 1;
			}
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/withdrawGroup", method = RequestMethod.POST)
	public int withdrawGroup(
			@RequestParam("to") int to,
			@RequestParam("from") int from
			) {
		String grougCode = MathUtil.getGroupId();
		int result = userGroupMappingMapper.withdrawGroup(grougCode, from);
		if ( result > 0 ) {
			result = firebaseMapper.invitationRefusal(to, from);
		} else {
			result = 0;
		}
		
		return result;
	}

}
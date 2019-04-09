package net.octacomm.sample.controller.android;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.FirebaseMapper;
import net.octacomm.sample.dao.mapper.GroupPetMappingMapper;
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Feed;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.service.GroupsService;
import net.octacomm.sample.service.LoginService;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.utils.AES256Util;
import net.octacomm.sample.utils.FcmUtil;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/android/user")
@Controller
public class UserControllerForAndroid {

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
	
	@Autowired
	private DeviceMapper deviceNapper;
	
	@Autowired
	private GroupPetMappingMapper groupPetMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public CommonResponce<User> regist(@RequestBody User user) throws FirebaseMessagingException {
		CommonResponce<User> response = new CommonResponce<User>();	
		//GroupId를 유니크 한 값으로 변경한다.
		String grougCode = UUID.randomUUID().toString().replace("-", "");
		if (userMapper.getUserList(user).size() != 0) {
			response.setResultMessage(ResultMessage.DUPLICATE_EMAIL);
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);
			return response;
		} else {
			if(userService.createUser(user) != null) {
				user.setGroupCode(grougCode);
				UserGroupMapping groupMapping = new UserGroupMapping();
				groupMapping.setUserIdx(user.getUserIdx());
				groupMapping.setGroupCode(grougCode);
				
				if (userGroupMappingMapper.insert(groupMapping) != 0) {
					//여기서 더미 DEVICE 를 등록한다.
					//신규 그룹 생성
					GroupPetMapping groupPetMapping = new GroupPetMapping();
					groupPetMapping.setGroupCode(grougCode);
					groupPetMapping.setPetGroupCode(MathUtil.artificialPetGroupCode());
					
					if(groupPetMappingMapper.insert(groupPetMapping) != 0) {
						Device device = new Device();
						device.setGroupCode(grougCode);
						device.setSerialNumber(MathUtil.artificialSerialNumber());
						//디바이스 모드 자동 설정
						device.setMode(1);
						
						if(deviceNapper.artificialDeviceInsert(device) != 0) {
							response.setResultMessage(ResultMessage.SUCCESS);
							response.setStatus(HodooConstant.OK_RESPONSE);
							response.setDomain(user);
							return response;
						}
						
					}
					
				}
			}
		}
		response.setStatus(HodooConstant.SQL_ERROR_RESPONSE);
		response.setResultMessage(ResultMessage.FAILED);
		response.setDomain(null);
		return response;
	}

	/*@ResponseBody
	@RequestMapping(value = "/regist2.do", method = RequestMethod.POST)
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
	}*/

	/*@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public SessionMaintenance login(@RequestBody User user) {
		SessionMaintenance group = loginService.login(user);
		return group;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login3.do", method = RequestMethod.GET)
	public SessionMaintenance login3(User user) {
		SessionMaintenance group = loginService.login(user);
		return group;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/login2.do", method = RequestMethod.POST)
	public CommonResponce<User> login2(@RequestBody User user) {
		CommonResponce<User> group = loginService.login2(user);
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/get/group/member.do", method = RequestMethod.POST)
	public CommonResponce<List<User>> login(@RequestParam("groupCode") String groupCode) {
		CommonResponce<List<User>> response = new CommonResponce<List<User>>();
		List<User> list = userMapper.getGroupMemner(groupCode);
		if(list.size() > 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(list);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(null);	
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public CommonResponce<User> get(@RequestParam("userIdx") int userIdx) {
		CommonResponce<User> response = new CommonResponce<User>();
		User obj =  userMapper.get(userIdx);
		if(obj != null) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(obj);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(obj);
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/update/basic/info.do", method = RequestMethod.POST)
	public CommonResponce<Integer> updateBasic(@RequestBody User user) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = userMapper.updateBasic(user);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(result);
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/update/user/password.do", method = RequestMethod.POST)
	public CommonResponce<Integer> updateUsetPassword(@RequestBody User user) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = userMapper.updateUsetPassowrd(user);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(result);
		}
		return response;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password2.do", method = RequestMethod.POST)
	public int findUserPasswordTest() {
		return 0;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/find/user/password.do", method = RequestMethod.POST)
	public CommonResponce<User> changeUserPassword(@RequestParam("email") String email) {
		CommonResponce<User> responce = new CommonResponce<User>();
		User returnUser = userMapper.getByUserEmail(email);
		if (returnUser == null) {
			// not found user;
			// 존재하지 않는 유저입니다.
			responce.setDomain(returnUser);
			responce.setResultMessage(ResultMessage.NOT_FOUND_USER);
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			return responce;
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
					responce.setStatus(HodooConstant.OK_RESPONSE);
					return responce;
				} else {
					// 리턴 메일 전송 실패
					responce.setDomain(returnUser);
					responce.setResultMessage(ResultMessage.FAILED_TO_SEND_MAIL);
					responce.setStatus(HodooConstant.SERVER_ERROR);
					return responce;
				}
			} else {
				// 비밀번호 업데이트 실패
				// 이전 비밀번호로 다시 업데이트
				returnUser.setPassword(oldPassword);
				userMapper.updateUsetPassowrd(returnUser);
				responce.setDomain(returnUser);
				responce.setResultMessage(ResultMessage.PASSWORD_UPDATE_FAILED);
				responce.setStatus(HodooConstant.SERVER_ERROR);
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

	@RequestMapping(value = "/checkUserCertifiedMail.do", method = RequestMethod.GET)
	public ModelAndView checkUserCertifiedMail(
			HttpServletRequest request,
			@RequestParam("code") String code) {
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

	@RequestMapping(value = "/welcomeSignup.do", method = RequestMethod.GET)
	public ModelAndView welcomeSignup() {
		ModelAndView mav = new ModelAndView("welcome_signup");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "/update/fcmToken.do", method = RequestMethod.POST)
	public CommonResponce<Integer> saveFCMToken( @RequestBody User user ) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		if ( user.getUserIdx() == 0 ) {
			user = userMapper.getByUserEmail(user.getEmail());
		}
		int result = userService.saveFCMToken(user);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(result);
		}
		return response;
	}
	
	
	/* 여기부터 설명이 에매모호함....*/
	@ResponseBody
	@RequestMapping(value = "/invitation/approval.do", method = RequestMethod.POST)
	public CommonResponce<Integer> invitationApproval( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = userGroupMappingMapper.invitationApproval(toUserIdx, fromUserIdx);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(result);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/invitation/refusal.do", method = RequestMethod.POST)
	public CommonResponce<Integer> invitationRefusal( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		CommonResponce<Integer> response = new CommonResponce<Integer>();
		int result = firebaseMapper.invitationRefusal(toUserIdx, fromUserIdx);
		if(result != 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(result);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(result);
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/invitation/getInvitationUser.do", method = RequestMethod.POST)
	public CommonResponce<List<InvitationRequest>> getInvitationList( 
			@RequestParam("userIdx") int userIdx) {
		CommonResponce<List<InvitationRequest>> response = new CommonResponce<List<InvitationRequest>>();
		List<InvitationRequest> list = firebaseMapper.getInvitationList(userIdx);
		if(list.size() > 0) {
			response.setStatus(HodooConstant.OK_RESPONSE);
			response.setDomain(list);
		}else {
			response.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			response.setDomain(list);
		}
		return response;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/invitation/setInvitationType.do", method = RequestMethod.POST)
	public int setInvitationType( 
			@RequestParam("type") int type,
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int result = firebaseMapper.setInvitationType(type, toUserIdx, fromUserIdx);
		if ( result > 0 && type == HodooConstant.ACCEPT_TYPE ) {
			firebaseMapper.updateInvitationDate(toUserIdx, fromUserIdx, sdf.format(date));
			User toUser = userMapper.get(toUserIdx);
			
			User user = userMapper.get(fromUserIdx);
			Message message = new Message();
			
			Map<String, Object> data = new HashMap<>();
			data.put("notiType", HodooConstant.FIREBASE_INVITATION_ACCEPT);
			data.put("title", "Invitation Approval Notice");
			data.put("content", toUser.getNickname() + ". You have approved the invitation.");
			/* 커스텀 Notification을 위한 데이터 처리(e) */
			
			message.setTo(user.getPushToken());
			message.setData(data);
			new FCMThead(message).start();
			
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/setUserCode.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/withdrawGroup.do", method = RequestMethod.POST)
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
	@ResponseBody
	@RequestMapping(value = "/invitation/cancelInvitation.do", method = RequestMethod.POST)
	public int cancelInvitation (
			@RequestParam("toUserEmail") String toUserEmail,
			@RequestParam("from") int from
			) {
		int checkState = firebaseMapper.checkInvitationState(from);
		User toUser = userMapper.getByUserEmail(toUserEmail);
		if ( checkState == 0 )
			return -1;
		return firebaseMapper.invitationRefusal(toUser.getUserIdx(), from);
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkGroupCount.do", method = RequestMethod.POST)
	public int checkGroupCount ( @RequestParam("idx") int idx ) {
		User user = userMapper.get(idx);
		User loginUser = userMapper.login(user);
		if ( loginUser.getAccessType() == HodooConstant.GROUP_NORMAL_MEMBER )
			return HodooConstant.NOT_GROUP_MASTER;
		
		int groupCount = userGroupMappingMapper.getGroupCount(loginUser.getGroupCode());
		if ( groupCount > 0 )
			return HodooConstant.MEMBER_EXIST;
		
		return HodooConstant.SUCCESS_CODE;
	}
	
	public class FCMThead extends Thread {
		private Message message;
		FCMThead ( Message message ){
			this.message = message;
		}
		@Override
		public void run() {
			FcmUtil.requestFCM(message);
		}
	}

}
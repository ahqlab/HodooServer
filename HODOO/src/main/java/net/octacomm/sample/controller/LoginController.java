package net.octacomm.sample.controller;

import javax.servlet.http.HttpSession;

import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;
import net.octacomm.sample.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.appidentity.AppIdentityServicePb.AppIdentityServiceError.ErrorCode;

/**
 * 사용안함.
 * @author silve
 *
 */
@Controller
public class LoginController {
	
	public static final String LOGIN_URL = "/login.do";
	public static final String DEFAULT_TARGET_URL = "/notice/list.do";

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String index() {
		return "redirect:" + DEFAULT_TARGET_URL;
	}
	
	/**
	 * 로그인
	 * 
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("adminUser", new User());
		return LOGIN_URL;
	}*/
	/**
	 * 로그인 진행
	 * 
	 * @param model
	 * @param session
	 * @param user
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(Model model, HttpSession session, User user, Errors errors) {
		/*ResultMessageGroup group = null;
		try {
			User result = loginService.login(user);
			group = new ResultMessageGroup();
			if(result != null) {
				group.setResultMessage(ResultMessage.SUCCESS);
				group.setDomain(user);
			}else {
				group.setResultMessage(ResultMessage.FAILED);
				group.setDomain(user);
			}
		} catch (NotFoundUserException nfe) {
			group.setDomain(null);
			group.setResultMessage(ResultMessage.NOT_FOUND_EMAIL);
			//errors.reject("test", "아이디가 존재하지 않습니다.");
		} catch (InvalidPasswordException ipe) {
			group.setDomain(null);
			group.setResultMessage(ResultMessage.PASSWORD_DO_NOT_MATCH);
			//errors.reject("test", "비밀번호가 일치하지 않습니다.");
		}*/
		return LOGIN_URL;
	}

	/**
	 * 로그아웃
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:" + LOGIN_URL;
	}
}

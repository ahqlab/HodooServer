package net.octacomm.sample.controller;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.AES256Util;
import net.octacomm.sample.utils.RSA;

@RequestMapping("/mail")
@Controller
public class MailController {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired 
	private ResourceLoader resourceLoader;


	@RequestMapping(value = "/mail/mailSending")
	public void mailSending() {
		String setfrom = "hellomyhodoo@gmail.com";
		String tomail = "silverlight2017@ahqlab.com"; // 받는 사람 이메일
		String title = "안녕하세요"; // 제목
		String content = "아큐랩입니다."; // 내용
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom, "호두스케일"); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/user/certified", method = RequestMethod.POST)
	public int userCertifiedMailSend(
			HttpServletRequest request,
			@RequestParam("toMailAddr")  String toMailAddr) {
		
		
		String setfrom = "hellomyhodoo@gmail.com";
		String tomail = toMailAddr; // 받는 사람 이메일
		String title = "안녕하세요"; // 제목
		String content = "아큐랩입니다."; // 내용
		try {
			User tempUser = new User();
			tempUser.setEmail(tomail);
			User user = userMapper.getUser(tempUser);
			String mixStr = user.getEmail() + "day" + user.getCreateDate();
			String encodingStr = new AES256Util().encrypt(mixStr);
			encodingStr = encodingStr.replace("+", "%2B");
			
			/* 보내는 url 생성 (s)
			 * 실제 서버 또는 도메인 연결시 포트는 생략
			 *  */
			String url = request.getRequestURL().toString().replace(request.getRequestURI(),"") + "/user/checkUserCertifiedMail?code=" + encodingStr;
			/* 보내는 url 생성 (e) */
			String htmlStr = "<html>" + 
					"<body style='font-size: 12px; color: #e388a1'>" + 
					"<div id='wrap' style='margin: 40px auto 0; width: 680px; text-align: center;'>" + 
					"<h1 style='margin: 0 auto 40px; width: 175px; height: 40px; '><img src='cid:logo'></h1>" + 
					"<img src='cid:illust'>" + 
					"<h2 style='color: #d46a87; font-size: 24px;'>회원가입 인증 메일입니다</h2>" + 
					"<p>아래 주소로 접속해서 이메일 인증을 완료해주세요.</p>" + 
					"<p>" + 
					"<a href='" + url + "' target='_blank'>[회원 인증 하기]</a>" + 
					"</p>" + 
					"<p class='sub_script' style='font-size: 9px;'>" + 
					"위 주소는 보안을 위해 한번만 사용하실 수 있으며, 인증 완료 후 다시 사용하실 수 없습니다." + 
					"<br/>" +
					"그 외에 궁금하신 사항이 잇으시면 고객센터로 문의 바랍니다. 감사합니다. 	" + 
					"</p>" + 
					"</div>" + 
					"</body>" + 
					"</html>";

			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			MimeMultipart multipart = new MimeMultipart("related");
	
			messageHelper.setFrom(setfrom, "호두스케일"); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(htmlStr, true); // 메일 내용
			
			/* 이미지 로드 */
			File logo = resourceLoader.getResource("resources/images/logo.gif").getFile();
			File img = resourceLoader.getResource("resources/images/illust.png").getFile();
			
			FileSystemResource res = new FileSystemResource(logo);
			
			messageHelper.addInline("logo", res);
			res = new FileSystemResource(img);
			messageHelper.addInline("illust", res);
			mailSender.send(message);
			return 1;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
}

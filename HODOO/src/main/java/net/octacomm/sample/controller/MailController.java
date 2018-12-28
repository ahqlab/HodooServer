package net.octacomm.sample.controller;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mail")
@Controller
public class MailController {

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "/mail/mailSending")
	public void mailSending() {
		String setfrom = "silverlight2017@gmail.com";
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
}

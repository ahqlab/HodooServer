/*<<<<<<< HEAD
package net.octacomm.sample.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.octacomm.sample.domain.Notification;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.FirebaseMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.Message;

@RequestMapping("/fcm")
@Controller
public class GoogleFCMTest {
	
	public int NOT_TO_DEVICE = -2;
	public int NOT_TO_USER = -1;
	public int ERROR = 0;
	public int SUCESS = 1;
	public int EXISTENCE_USER = 2;
	
	@Autowired
	UserMapper mapper;
	
	@Autowired
	FirebaseMapper firebaseMapper;

	@ResponseBody
	@RequestMapping(value = "/mobile/send.do")
	public String index() throws Exception {

		//final String apiKey = "AAAAEs65_CY:APA91bHK9ZVb0UP616OHPy5ZZiLu_1ogkrypPM5ahfOxSgyk0laN5NhOjBRf75k_mZzEgjJg3jgaWyQbT2SEsB9spuNOfgV9v4yMpPC79zrk5ESc5mm51N8yAV2Buk0ksWZ6jXYAzmidtQamnbZcf5qHhm5P6O0fnQ";
		//호두
		final String apiKey = "AAAAfhtaYsk:APA91bEgKSbdUUKWISstd-k2uDvzCla8anBmDQhibr114NYN7tfpwTI8QTaqamqZSpPwa2746TVIuUYlVGqGbUIH6oUjHI9zz6pzwDdvMt4yPmw492zfc6sAaJpAmukLO8B4fJngr4D_";
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + apiKey);

		conn.setDoOutput(true);

		// 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
		//String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
		
		Message message = new Message();
//		message.setTo("ciNz3DLykWk:APA91bFvTng4XsSpmNHU5VqGGZeUkJJgIIdABYZN68zoMdcHFlzN7pyPHLmkrnXHdA434fN06cNq0eKpofexKj3SfNKKUnxfdHqBKpoiQ8kWH8LgWzsfNjg2IYHhfXe317o1dLhDxQnu");
		//message.setTo("f9XWGDbDesM:APA91bEBbnE9Pth5gCBT4vpFHmXKK-TvBriYObS3XPUNfpgxfl7eWnClDkK7cT0jPnHIwDwJrNsKVTA9AlAVcDc4DOBTIVGo0UJ4zHpu-DdREcmPTbnzeyKhGgIllcw8J5nAtIITmTGo");
		message.setTo("fERn6W3DLgU:APA91bF7z5lLYAcEfgex4U4XkMCJ3c2AAUlCDeirV74171XASQr2FIRHJOzduOB5H3jxx_v58kGYp4afYF8cRkawDI9RUrIK6nQI2xgY6MXnVv3HRA5yGPaUbIUuwD1G4dhnL-nsNWj2");
		
		
		//message.setTo("fYUgwb9M2oU:APA91bFXb9BDIsOvbqewBCQWeuhPw7khWUmsN25KkPw1N5Ng5RfhzLcFmYJBSBiWtY_6Xbvbjc-XMO7U1xRLcodgOeTTfT1qLqcF4pNgN7bsN7mTfWKOhlDuDsFnpsleEl_zsgLoJz2P");
		//message.setTo("/topics/news");
		Notification content = new net.octacomm.sample.domain.Notification();
		content.setBody("측정결과입니다.");
		content.setTitle("Hoodoo scale");
		content.setSound("default");
		content.setPriority("high");
	
		
		message.setNotification(content);
		Gson gson = new Gson();
		String json = gson.toJson(message);
		
		
		OutputStream os = conn.getOutputStream();

		// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
		os.write(json.getBytes("UTF-8"));
		os.flush();
		os.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + json);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print result
		System.out.println(response.toString());

		return response.toString();
	}
	@ResponseBody
	@RequestMapping(value = "/mobile/send/invitation", method = RequestMethod.POST)
	public int invitation (
			@RequestParam("toUserEmail") String toUserEmail,
			@RequestParam("fromUserEmail") String fromUserEmail) {
		int result = 0;
		User toUser = mapper.getByUserEmail(toUserEmail);
		User fromUser = mapper.getByUserEmail(fromUserEmail);
		
		Message message = new Message();
		message.setTo(toUser.getPushToken());

		
		 커스텀 Notification을 위한 데이터 처리(s) 
		Map<String, Object> data = new HashMap<>();
		data.put("notiType", HodooConstant.FIREBASE_INVITATION_TYPE);
		data.put("fromUserEmail", fromUser.getEmail());
		data.put("fromUserIdx", fromUser.getUserIdx());
		data.put("toUserIdx", toUser.getUserIdx());
		data.put("host", "invitation");
		data.put("title", "회원 초대 알림");
		data.put("content", fromUser.getEmail());
		 커스텀 Notification을 위한 데이터 처리(e) 
		
		message.setTo(toUser.getPushToken());
		message.setData(data);
		
		InvitationRequest request = new InvitationRequest();
		request.setToUserIdx(toUser.getUserIdx());
		request.setFromUserIdx(fromUser.getUserIdx());
		request.setCreated(new Date().getTime());
		
		if ( firebaseMapper.getCount(request) > 0 ) {
			InvitationRequest invitationUser = firebaseMapper.getInvitationUser(request);
			if ( invitationUser.getState() == 1 ) {
				result = EXISTENCE_USER;
				return result;
			}
		}
		
		result = requestFCM(message);
		if ( result == SUCESS ) {
			if ( firebaseMapper.getCount(request) > 0 ) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				request.setCreated( sdf.format(date) );
				firebaseMapper.updateUser(request);
			} else {
				firebaseMapper.insert(request);
			}
		}
		
		return result;
		
	}
	@ResponseBody
	@RequestMapping("/register")
	public int register ( 
			@RequestParam("toUserIdx") int toUserIdx,
			@RequestParam("fromUserIdx") int fromUserIdx
			) {
		
		InvitationRequest request = new InvitationRequest();
		request.setToUserIdx(toUserIdx);
		request.setFromUserIdx(fromUserIdx);
		return firebaseMapper.insert(request);
	}
	
	private int requestFCM ( Message message ) {
		int result = 0;
		try {
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + HodooConstant.FCM_APIKEY);
	
			conn.setDoOutput(true);
			
			Gson gson = new Gson();
			String json = gson.toJson(message);
			
			
			OutputStream os = conn.getOutputStream();
	
			// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
			os.write(json.getBytes("UTF-8"));
			os.flush();
			os.close();
	
			result = conn.getResponseCode();
			if ( result == 200 )
				result = SUCESS;
			
			System.out.println("Response Code : " + result);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return result;
		
		}  catch( Exception e ) {
			result = ERROR;
		}
		return result;
	}
}
=======*/
package net.octacomm.sample.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.octacomm.sample.domain.Notification;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.service.UserService;
import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.AlarmMapper;
import net.octacomm.sample.dao.mapper.AlarmObjectMapper;
import net.octacomm.sample.dao.mapper.FirebaseMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.AlarmItem;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.Message;

@RequestMapping("/fcm")
@Controller
public class GoogleFCMTest {
	
	public int NOT_TO_DEVICE = -2;
	public int NOT_TO_USER = -1;
	public int ERROR = 0;
	public int SUCESS = 1;
	
	public int EXISTENCE_USER = 2;
	public int OVERLAB_INVITATION = 3;
	public int ALARM_DISABLE = 4;
	
	@Autowired
	UserMapper mapper;
	
	@Autowired
	FirebaseMapper firebaseMapper;
	
	@Autowired
	AlarmObjectMapper alarmMapper;

	@ResponseBody
	@RequestMapping(value = "/mobile/send.do")
	public String index() throws Exception {

		//final String apiKey = "AAAAEs65_CY:APA91bHK9ZVb0UP616OHPy5ZZiLu_1ogkrypPM5ahfOxSgyk0laN5NhOjBRf75k_mZzEgjJg3jgaWyQbT2SEsB9spuNOfgV9v4yMpPC79zrk5ESc5mm51N8yAV2Buk0ksWZ6jXYAzmidtQamnbZcf5qHhm5P6O0fnQ";
		//호두
		final String apiKey = "AAAAfhtaYsk:APA91bEgKSbdUUKWISstd-k2uDvzCla8anBmDQhibr114NYN7tfpwTI8QTaqamqZSpPwa2746TVIuUYlVGqGbUIH6oUjHI9zz6pzwDdvMt4yPmw492zfc6sAaJpAmukLO8B4fJngr4D_";
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + apiKey);

		conn.setDoOutput(true);

		// 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
		//String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
		
		Message message = new Message();
//		message.setTo("ciNz3DLykWk:APA91bFvTng4XsSpmNHU5VqGGZeUkJJgIIdABYZN68zoMdcHFlzN7pyPHLmkrnXHdA434fN06cNq0eKpofexKj3SfNKKUnxfdHqBKpoiQ8kWH8LgWzsfNjg2IYHhfXe317o1dLhDxQnu");
		message.setTo("f9XWGDbDesM:APA91bEBbnE9Pth5gCBT4vpFHmXKK-TvBriYObS3XPUNfpgxfl7eWnClDkK7cT0jPnHIwDwJrNsKVTA9AlAVcDc4DOBTIVGo0UJ4zHpu-DdREcmPTbnzeyKhGgIllcw8J5nAtIITmTGo");
		
		
		//message.setTo("fYUgwb9M2oU:APA91bFXb9BDIsOvbqewBCQWeuhPw7khWUmsN25KkPw1N5Ng5RfhzLcFmYJBSBiWtY_6Xbvbjc-XMO7U1xRLcodgOeTTfT1qLqcF4pNgN7bsN7mTfWKOhlDuDsFnpsleEl_zsgLoJz2P");
		//message.setTo("/topics/news");
		Notification content = new net.octacomm.sample.domain.Notification();
		content.setBody("측정결과입니다.");
		content.setTitle("Hoodoo scale");
		content.setSound("default");
		content.setPriority("high");
	
		
		message.setNotification(content);
		Gson gson = new Gson();
		String json = gson.toJson(message);
		
		
		OutputStream os = conn.getOutputStream();

		// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
		os.write(json.getBytes("UTF-8"));
		os.flush();
		os.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + json);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print result
		System.out.println(response.toString());

		return response.toString();
	}
	
	/* 유저 초대 관련 api
	 * 데이터베이스처리와 노티요청을 한다. 
     */
	@ResponseBody
	@RequestMapping(value = "/mobile/send/invitation.do", method = RequestMethod.POST)
	public int invitation (
			@RequestParam("toUserEmail") String toUserEmail,
			@RequestParam("fromUserEmail") String fromUserEmail
			) {
		int result = 0;
		User toUser = mapper.getByUserEmail(toUserEmail);
		User fromUser = mapper.getByUserEmail(fromUserEmail);
		
		toUser.setGroupCode( mapper.getGroupCode(toUser.getUserIdx()) );
		
		Message message = new Message();
		message.setTo(toUser.getPushToken());

		
		/* 커스텀 Notification을 위한 데이터 처리(s) */
		Map<String, Object> data = new HashMap<>();
		data.put("notiType", HodooConstant.FIREBASE_INVITATION_TYPE);
		data.put("fromUserEmail", fromUser.getEmail());
		data.put("fromUserIdx", fromUser.getUserIdx());
		data.put("toUserIdx", toUser.getUserIdx());
		data.put("host", "invitation");
		data.put("title", "그룹 참여 요청");
		//data.put("title", "Group Participation Request");
		data.put("content", fromUser.getEmail() + " request.");
		/* 커스텀 Notification을 위한 데이터 처리(e) */
		
		message.setTo(toUser.getPushToken());
		message.setData(data);
		
		InvitationRequest request = new InvitationRequest();
		request.setToUserIdx(toUser.getUserIdx());
		request.setFromUserIdx(fromUser.getUserIdx());
		request.setCreated(new Date().getTime());
		int count = firebaseMapper.getAcceptCount(request);
		if ( count > 0 ) {
			InvitationRequest invitationUser = firebaseMapper.getInvitationUser(request);
			if ( invitationUser != null && invitationUser.getState() == 1 ) {
				return EXISTENCE_USER;
			} else {
				return OVERLAB_INVITATION;
			}
		}
		
		result = 1;
		
		int number = alarmMapper.getAlarm(toUser.getUserIdx());
		if ( number != 1 && (number & (0x01 << HodooConstant.GROUP_ALARM)) == 0 )
			return ALARM_DISABLE;
		
		new InvitationFCM(request, message).start(); 
		
		return result;
		
	}
	
	/*
	 * 일반 노티를 보내는 api
	 * 초대 관련 노티와 다르게 타이틀과 컨텐츠로 노티를 생성한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/mobile/send/normal.do", method = RequestMethod.POST)
	public int normalPush (
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("toUserEmail") String toUserEmail
			) {
		User toUser = mapper.getByUserEmail(toUserEmail);
		
		int result = 0;
		Message message = new Message();
		message.setTo(toUser.getPushToken());

		
		/* 커스텀 Notification을 위한 데이터 처리(s) */
		Map<String, Object> data = new HashMap<>();
		data.put("notiType", HodooConstant.FIREBASE_NORMAL_TYPE);
		data.put("title", title);
		data.put("content", content);
		/* 커스텀 Notification을 위한 데이터 처리(e) */
		
		message.setTo(toUser.getPushToken());
		message.setData(data);
		
		result = requestFCM(message);
		
		return result;
	}
	
	
	//실제 노티를 요청하는 메서드
	private int requestFCM ( Message message ) {
		int result = 0;
		try {
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + HodooConstant.FCM_APIKEY);
	
			conn.setDoOutput(true);
			
			Gson gson = new Gson();
			String json = gson.toJson(message);
			
			
			OutputStream os = conn.getOutputStream();
	
			// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
			os.write(json.getBytes("UTF-8"));
			os.flush();
			os.close();
	
			result = conn.getResponseCode();
			if ( result == 200 )
				result = SUCESS;
			
			System.out.println("Response Code : " + result);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return result;
		
		}  catch( Exception e ) {
			result = ERROR;
		}
		return result;
	}
	
	/* 초대관련 노티를 보내는 스레드 */
	public class InvitationFCM extends Thread {
		private Message message;
		InvitationRequest request;
		public InvitationFCM(InvitationRequest request, Message message) {
			// TODO Auto-generated constructor stub
			this.message = message;
			this.request = request;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			int result = requestFCM(message);
			if ( result == SUCESS ) {
				if ( firebaseMapper.getCount(request) > 0 ) {
					request = firebaseMapper.getInvitationUserFrom(request);
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					request.setCreated( sdf.format(date) );
					firebaseMapper.updateUser(request);
				} else {
					firebaseMapper.insert(request);
				}
			}
		}
		
	}
}

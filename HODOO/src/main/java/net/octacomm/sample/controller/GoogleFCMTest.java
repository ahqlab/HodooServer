package net.octacomm.sample.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.octacomm.sample.domain.Notification;
import net.octacomm.sample.domain.Message;

@RequestMapping("/fcm")
@Controller
public class GoogleFCMTest {

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
		//message.setTo("fLVSoRV4lrw:APA91bGnpE1c5LDplPUskWpjpIByuARW97PM3fylotRVABeCH_0LL-TeFZerwh7aN7gS2R7q0ZGlXrNJXiIAdBpuvS2kuzJZ0lTiGnRkoU4tlKeoCxmuGjlu3Y4usLSjvjY8W2kKNXcCtqXZYftyplIqSASVKj86fQ");
		//message.setTo("fYUgwb9M2oU:APA91bFXb9BDIsOvbqewBCQWeuhPw7khWUmsN25KkPw1N5Ng5RfhzLcFmYJBSBiWtY_6Xbvbjc-XMO7U1xRLcodgOeTTfT1qLqcF4pNgN7bsN7mTfWKOhlDuDsFnpsleEl_zsgLoJz2P");
		message.setTo("topics/news");
		Notification content = new net.octacomm.sample.domain.Notification();
		content.setBody("측정결과입니다.");
		content.setTitle("Hoodoo scale");
		content.setSound("default");
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
}

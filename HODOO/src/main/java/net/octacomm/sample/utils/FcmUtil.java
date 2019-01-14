package net.octacomm.sample.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.domain.Message;

public class FcmUtil {
	public static int NOT_TO_DEVICE = -2;
	public static int NOT_TO_USER = -1;
	public static int ERROR = 0;
	public static int SUCESS = 1;
	public static int EXISTENCE_USER = 2;
	
	public static int requestFCM ( Message message ) {
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

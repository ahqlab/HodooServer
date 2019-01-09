package net.octacomm.sample.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.RealTimeWeightMapper;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.utils.DateUtil;

@Service
public class BackGroundServiceImpl implements BackGroundService {

	@Override
	public void additionalWeightWork() {
		// TODO Auto-generated method stub
		
	}

	/*@Autowired
	RealTimeWeightMapper realTimeWorkMapper;

	private String[] items = new String[] { "b4e62d9f13c9", "zw4es9DzZqSQ", "dyDZYHAdLDj4", "I2eOjbeDZ6ZB",
			"IByCoXSsrxoM" };
	private Random rand = new Random();

	@Scheduled(fixedDelay = (1000 * 60))
	@Override
	public void additionalWeightWork() {
		realTimeWorkMapper
				.insert(new RealTimeWeight(getRandArrayElement(), Float.parseFloat(String.valueOf(rand.nextInt(10)))));
	}

	public String getRandArrayElement() {
		return items[rand.nextInt(items.length)];
	}

	public static String converteLongToDate(String date) {
		long ldata = Long.parseLong(date);
		String dateFormatStringTime;
		ldata = ldata * 1000;
		Date date1 = new Date(ldata);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatStringTime = dateFormat.format(date1);
		return dateFormatStringTime;
	}*/
	
	
}

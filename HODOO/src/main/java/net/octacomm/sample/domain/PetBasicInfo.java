package net.octacomm.sample.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PetBasicInfo implements Domain{
	
	
	private int id;
	
	private String groupCode;
	
	private MultipartFile profile;
	
	private String profileFilePath;
	
	private String profileFileName;
	
	private String sex;
	
	private String petName;
	
	private String petBreed;
	
	private String birthday;
	
	private String neutralization;
	
	private int currentYear;

	private int currentMonth;
	
	private int petType;
	
	private String selectedBfi;
	
	
	public int getCurrentYear() {
		return currentYear();
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentMonth() {
		return currentMonth();
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int currentYear(){
		return getPeriod().getYears();
	}

	//return 개월수
	public int currentMonth(){
		return getPeriod().getMonths();
	}
	
	private Period getPeriod(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.parse(getBirthday(), formatter);
		Period p = Period.between(birthday, today);
		return p;
	}
}

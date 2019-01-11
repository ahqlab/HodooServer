package net.octacomm.sample.domain;

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
}

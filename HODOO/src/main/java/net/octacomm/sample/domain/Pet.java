package net.octacomm.sample.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Pet implements Domain {

	private int petIdx;
	
	private String groupCode;
	
	private int basic;
	
	private int disease;
	
	private int physical;
	
	private int weight;
	
	private String createDate;

}

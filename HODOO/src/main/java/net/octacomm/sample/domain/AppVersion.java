package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class AppVersion implements Domain{
	
	private int id;
	
	private String version;
	
	private String createDate;
	
}

package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class Device implements Domain{

	private int deviceIdx;

	private String groupCode;

	private String serialNumber;
	
	private String connect;
	
	private String isDel;

	private String createDate;

}

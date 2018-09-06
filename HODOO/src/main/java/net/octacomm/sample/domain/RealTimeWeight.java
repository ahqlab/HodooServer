package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class RealTimeWeight implements Domain{
	
	private int id;
	
	private String mac;
	
	private float value;
	
	private String createDate;

}
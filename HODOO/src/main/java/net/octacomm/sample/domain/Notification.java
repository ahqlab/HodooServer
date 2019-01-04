package net.octacomm.sample.domain;

import java.util.Map;

import lombok.Data;

@Data
public class Notification {
	
	private String title;
	
	private String body;
	
	private String sound;
	
	private String priority;
	
	private String host;
	
	private Map<String, Object> data;
}

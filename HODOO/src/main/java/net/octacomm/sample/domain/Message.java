package net.octacomm.sample.domain;

import java.util.Map;

import lombok.Data;

@Data
public class Message {
	
	
	private Notification notification;
	
	private String to;
	
	private Map<String, Object> data;

}

package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class Notification {
	
	private String title;
	
	private String body;
	
	private String sound;
}

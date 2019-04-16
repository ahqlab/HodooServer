package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class AlarmItem implements Domain {

	private int id;

	private String name;
	
	private String koName;
	
	private String enName;


}

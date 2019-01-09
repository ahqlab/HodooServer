package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class Groups implements Domain{
	
	private int id;
	
	private int userId;
	
	private String groupId;
	
	private int petId;
}

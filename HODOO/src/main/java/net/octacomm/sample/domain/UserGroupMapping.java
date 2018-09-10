package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class UserGroupMapping implements Domain{
	
	private int id;
	
	private int groupId;
	
	private int userId;

}

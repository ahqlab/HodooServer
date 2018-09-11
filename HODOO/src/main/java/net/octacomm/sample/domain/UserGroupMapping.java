package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class UserGroupMapping implements Domain{
	
	private int id;

    private int userIdx;

    private String groupCode;

    private String createDate;
}

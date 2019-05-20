package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Domain {


	private int userIdx;

	private String email;

	private String password;

	private String passwordCheck;

	private String nickname;

	private String sex;

	private int country;

	private String pushToken;
	
	private String snsId;
	
	private String snsToken;
	
	private String createDate;
	
	// joinColumn
	private String groupCode;
	
	private int accessType;
	
	private int loginType;
	
	private int userCode;

	@Override
	public String toString() {
		return "User [userIdx=" + userIdx + ", email=" + email + ", password=" + password + ", passwordCheck=" + passwordCheck + ", nickname=" + nickname + ", sex=" + sex + ", country=" + country + ", pushToken=" + pushToken + ", createDate=" + createDate + ", groupCode=" + groupCode + "]";
	}
}

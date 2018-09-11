package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Domain{

	private int userIdx;

    private String email;

    private String password;

    private String passwordCheck;

    private String nickname;

    private String sex;

    private String from;

    private String createDate;
    
    //joinColumn
    private String groupCode;

}

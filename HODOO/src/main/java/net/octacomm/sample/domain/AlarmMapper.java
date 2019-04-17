package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class AlarmMapper implements Serializable {
	private int id;
	private int userIdx;
	private int number;
}

package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class AlarmObject implements Domain {
	private int idx;
	private int userIdx;
	private int number;
}

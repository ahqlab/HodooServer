package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetRegistResult implements Domain {
	private int code;
    private int petIdx;
}

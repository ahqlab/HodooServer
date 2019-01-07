package net.octacomm.sample.domain;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class PetCommonResponse<D extends Domain> {
	
	public Pet pet;
	
	public D domain;
	
	public ResultMessage resultMessage;

}

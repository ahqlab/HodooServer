package net.octacomm.sample.domain;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class PetCommonResponse {
	
	public Pet pet;
	
	public PetChronicDisease chronicDisease;
	
	public ResultMessage resultMessage;

}

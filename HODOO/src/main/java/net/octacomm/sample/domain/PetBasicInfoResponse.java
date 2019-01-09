package net.octacomm.sample.domain;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class PetBasicInfoResponse {
	
	public Pet pet;
	
	public PetBasicInfo basicInfo;
	
	public ResultMessage resultMessage;
	
	

}

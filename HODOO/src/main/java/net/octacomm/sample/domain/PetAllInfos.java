package net.octacomm.sample.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PetAllInfos implements Domain {
	
	
	@Getter
	@Setter
	public Pet pet;
	
	@Getter
	@Setter
	public PetBasicInfo petBasicInfo;
	
	@Getter
	@Setter
	public PetChronicDisease petChronicDisease;
	
	@Getter
	@Setter
	public PetPhysicalInfo petPhysicalInfo;
	
	@Getter
	@Setter
	public PetWeightInfo petWeightInfo;
	
	@Getter
	@Setter
	public PetUserSelectionQuestion petUserSelectionQuestion;


	@Override
	public String toString() {
		return "PetAllInfos [pet=" + pet + ", petBasicInfo=" + petBasicInfo + ", petChronicDisease=" + petChronicDisease
				+ ", petPhysicalInfo=" + petPhysicalInfo + ", petWeightInfo=" + petWeightInfo
				+ ", petUserSelectionQuestion=" + petUserSelectionQuestion + "]";
	}
	
	

}

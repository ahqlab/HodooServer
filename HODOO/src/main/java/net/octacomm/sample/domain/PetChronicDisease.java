package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetChronicDisease implements Domain{

	private int id;

    private int diseaseName;
}

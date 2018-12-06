package net.octacomm.sample.domain;


import lombok.Data;

@Data
public class MealHistoryContent implements Domain {

	private User user;

	private PetAllInfos petAllInfos;

	private Feed feed;
	
	private MealHistory mealHistory;

}

package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class WeightGoalChart implements Domain {

	private int id;

	private float currentWeight;

	private float weightGoal;

	private int petType;
}

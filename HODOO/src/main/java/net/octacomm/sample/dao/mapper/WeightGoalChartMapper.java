package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.WeightGoalChart;
import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.AppVersion;
import net.octacomm.sample.domain.DefaultParam;

public interface WeightGoalChartMapper extends CRUDMapper<WeightGoalChart, DefaultParam, Integer>{

	public String TABLE_NAME = " weight_goal_chart ";

	@Select("select "
			+ "currentWeight, ${bodyFat}_per as weightGoal , ABS(currentWeight - #{currentWeight}) Distance FROM weight_goal_chart WHERE petType = #{petType} order by Distance limit 1")
	WeightGoalChart getWeightGoal(@Param("currentWeight") float currentWeight, @Param("bodyFat") int bodyFat, @Param("petType") int petType);

	

}


package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.MealHistory;
import net.octacomm.sample.domain.MealHistoryContent;

public interface MealHistoryMapper extends CRUDMapper<MealHistory, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( historyIdx , groupId , userIdx , feedIdx , petIdx , calorie , unitIndex , unitString, createDate )";

	public String INSERT_VALUES = " ( null , #{groupId} , #{userIdx} , #{feedIdx} , #{petIdx} , #{calorie},  #{unitIndex} , #{unitString} , now() )";

	public String TABLE_NAME = " mear_history ";

	public String UPDATE_VALUES = " calorie = #{calorie} , unitIndex = #{unitIndex}, unitString = #{unitString}";

	public String SELECT_FIELDS = " historyIdx , groupId , userIdx , feedIdx , petdIdx, calorie , unitIndex , unitString, createDate ";
	
	
	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	int insert(MealHistory domain);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE historyIdx =  #{historyIdx} and isDel = 1")
	@Override
	MealHistory get(Integer historyIdx);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE historyIdx =  #{historyIdx}")
	@Override
	int update(MealHistory domain);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET  isDel = 0 WHERE historyIdx =  #{historyIdx}")
	int delete(@Param("historyIdx") int historyIdx);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petIdx =  #{petIdx} and substring(mear_history.createDate, 1,10) = #{date} and isDel = 1")
	List<MealHistory> getContentList(@Param("date") String date, @Param("petIdx") int petIdx);

	@Select("select "
			+ "	sum(tb.calorie) as calorie "
			+ " from "
			+ "(select " 
			+"   CASE mear_history.unitString" 
			+"   WHEN 'g' THEN (feed.calculationCalories * 0.01) * mear_history.calorie" 
			+"   WHEN '개' THEN (feed.calculationCalories) * mear_history.calorie" 
			+"   WHEN '컵' THEN (feed.calculationCalories) * mear_history.calorie" 
			+"   END AS calorie " 
			+" 	 from mear_history join feed on mear_history.feedIdx = feed.id " 
			+"   where date(mear_history.createDate) = date(now()) "
			+"   and mear_history.petIdx = #{petIdx} and isDel = 1" 
			+") as tb")
	MealHistory getTodatSumCalorie(@Param("petIdx") int petIdx);
	


}

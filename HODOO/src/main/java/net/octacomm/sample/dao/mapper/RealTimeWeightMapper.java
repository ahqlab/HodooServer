package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.StandardHsv;

public interface RealTimeWeightMapper extends CRUDMapper<RealTimeWeight, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( mac, value, createDate )";
	
	public String INSERT_VALUES = " ( #{mac}, #{value}, now())";
	
	public String TABLE_NAME = " real_time_weight ";
	
	public String UPDATE_VALUES = " mac = #{mac} ,  value = #{value} ";
	
	public String SELECT_FIELDS = " id, mac, value, createDate ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(RealTimeWeight realTimeWeight);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(RealTimeWeight realTimeWeight);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public RealTimeWeight get(Integer id);
	
	
	@Select("select value from " + TABLE_NAME  +" where mac = #{mac} order by createDate desc limit 1")
	public Float getLatelyData(@Param("mac") String mac);
	
	
	@Select("select value from " + TABLE_NAME  +" where mac = #{mac} order by createDate desc limit 4")
	public List<Float> getRealTimeList(@Param("mac") String mac);
		
	
}

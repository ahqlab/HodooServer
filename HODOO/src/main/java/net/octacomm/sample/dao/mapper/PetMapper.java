package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Pet;

public interface PetMapper extends CRUDMapper<Pet, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( petIdx , groupCode , basic , disease, physical, weight, createDate )";

	public String INSERT_VALUES = " ( null , #{groupCode} , #{basic} , #{disease} , #{physical} , #{weight} ,  #{createDate} )";

	public String TABLE_NAME = " pet ";

	public String UPDATE_VALUES = " groupCode = #{groupCode} , basic = #{basic} ,  disease = #{disease} ,  physical = #{physical} ,  weight = #{weight} , createDate = now() ";

	public String SELECT_FIELDS = " deviceIdx , groupCode , basic , disease, physical, weight, createDate ";

	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(Pet pet);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE petIdx =  #{petIdx}")
	@Override
	public int delete(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE petIdx =  #{petIdx} ")
	@Override
	public int update(Pet pet);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petIdx =  #{petIdx} ")
	@Override
	public Pet get(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE groupCode =  #{groupCode} ")
	public List<Pet> myPetList(String groupCode);
	
	
}

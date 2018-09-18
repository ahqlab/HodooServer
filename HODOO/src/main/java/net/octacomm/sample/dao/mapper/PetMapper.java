package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Pet;

public interface PetMapper extends CRUDMapper<Pet, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( petIdx , petGroupCode , basic , disease, physical, weight, createDate )";

	public String INSERT_VALUES = " ( null , #{petGroupCode} , #{basic} , #{disease} , #{physical} , #{weight} ,  now() )";

	public String TABLE_NAME = " pet ";

	public String UPDATE_VALUES = " petGroupCode = #{petGroupCode} , basic = #{basic} ,  disease = #{disease} ,  physical = #{physical} ,  weight = #{weight} , createDate = now() ";

	public String SELECT_FIELDS = " deviceIdx , petGroupCode , basic , disease, physical, weight, createDate ";

	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES ( null , (select petGroupCode FROM group_pet_mapping where groupCode = #{petGroupCode}) ,  #{basic} , #{disease} , #{physical} , #{weight} , now() ) ")
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
	
	@Select("SELECT pet.* FROM " + TABLE_NAME + " join group_pet_mapping on group_pet_mapping.petGroupCode = pet.petGroupCode  WHERE group_pet_mapping.groupCode = #{groupCode} ")
	public List<Pet> myPetList(String groupCode);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET disease = 0 WHERE petIdx =  #{petIdx} ")
	public void resetDisease(int petIdx);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET disease = #{disease} WHERE petIdx =  #{petIdx} ")
	public int registDisease(@Param("disease") int disease, @Param("petIdx") int petIdx);
	
	
}

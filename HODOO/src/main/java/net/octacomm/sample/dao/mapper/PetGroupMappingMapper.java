package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.PetGroupMapping;

public interface PetGroupMappingMapper extends CRUDMapper<PetGroupMapping, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( groupId, petId )";
	
	public String INSERT_VALUES = " ( #{groupId}, #{petId} )";
	
	public String TABLE_NAME = " pet_group_mapping ";
	
	public String UPDATE_VALUES = " groupId = #{groupId} , petId = #{petId} ";
	
	public String SELECT_FIELDS = " id, groupId, petId ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(PetGroupMapping petGroupMapping);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetGroupMapping petGroupMapping);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public PetGroupMapping get(Integer id);
	
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE groupId =  #{groupId}")
	public List<PetGroupMapping> getMyPetList(int groupId);

}

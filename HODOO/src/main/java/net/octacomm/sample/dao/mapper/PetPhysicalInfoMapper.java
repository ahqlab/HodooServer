package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.StandardHsv;

public interface PetPhysicalInfoMapper extends CRUDMapper<PetPhysicalInfo, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( petId, width, height, weight, createDate )";
	
	public String INSERT_VALUES = " ( #{petId}, #{width}, #{height}, #{weight}, now() )";
	
	public String TABLE_NAME = " pet_physical_info ";
	
	public String UPDATE_VALUES = " width = #{width} , height = #{height} , weight = #{weight} ";
	
	public String SELECT_FIELDS = " id, petId, width, height, weight, createDate ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(PetPhysicalInfo petPhysicalInfo);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetPhysicalInfo petPhysicalInfo);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	@Override
	public PetPhysicalInfo get(Integer id);
	
	
	@Select("select pet_chronic_disease.* from pet_chronic_disease join groups on pet_chronic_disease.petId = groups.petId where groups.groupId =  #{groupId} and pet_chronic_disease.petId = #{petId};")
	public PetPhysicalInfo InfoCheck(String groupId, int petId);

}

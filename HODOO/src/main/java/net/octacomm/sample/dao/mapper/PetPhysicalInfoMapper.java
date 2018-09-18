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
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.StandardHsv;

public interface PetPhysicalInfoMapper extends CRUDMapper<PetPhysicalInfo, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( id, width, height, weight, createDate )";
	
	public String INSERT_VALUES = " ( null, #{width}, #{height}, #{weight}, now() )";
	
	public String TABLE_NAME = " pet_physical_info ";
	
	public String UPDATE_VALUES = " width = #{width} , height = #{height} , weight = #{weight} ";
	
	public String SELECT_FIELDS = " id, width, height, weight, createDate ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(PetPhysicalInfo petPhysicalInfo);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
						
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetPhysicalInfo petPhysicalInfo);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public PetPhysicalInfo get(Integer id);
	
	
	@Select("SELECT pet_physical_info.* FROM pet " + 
			"join group_pet_mapping on group_pet_mapping.petGroupCode = pet.petGroupCode " + 
			"join pet_physical_info on pet.physical = pet_physical_info.id " + 
			"WHERE group_pet_mapping.groupCode = #{groupCode} " + 
			"and pet.petIdx = #{petIdx}")
	public PetPhysicalInfo getPetPhysicalInformation(@Param("groupCode") String groupCode, @Param("petIdx") int petIdx);

}

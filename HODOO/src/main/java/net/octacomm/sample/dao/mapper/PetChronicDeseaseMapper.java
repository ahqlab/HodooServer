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
import net.octacomm.sample.domain.StandardHsv;

public interface PetChronicDeseaseMapper extends CRUDMapper<PetChronicDisease, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( petId, diseaseName )";
	
	public String INSERT_VALUES = " ( #{petId}, #{diseaseName} )";
	
	public String TABLE_NAME = " pet_chronic_disease ";
	
	public String UPDATE_VALUES = " diseaseName = #{diseaseName}  ";
	
	public String SELECT_FIELDS = " id, petId, diseaseName ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(PetChronicDisease petChronicDisease);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetChronicDisease petChronicDisease);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public PetChronicDisease get(Integer id);
	
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	public List<PetChronicDisease> list(int petId);

	
	@Select("select pet_chronic_disease.* from pet_chronic_disease join groups on pet_chronic_disease.petId = groups.petId where groups.groupId  = #{groupId} and pet_chronic_disease.petId = #{petId}")
	public PetChronicDisease InfoCheck(String groupId, int petId);

}

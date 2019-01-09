package net.octacomm.sample.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;

public interface PetWeightInfoMapper extends CRUDMapper<PetWeightInfo, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( id, bcs, createDate )";

	public String INSERT_VALUES = " ( null, #{bcs}, now() )";

	public String TABLE_NAME = " pet_weight_info ";

	public String UPDATE_VALUES = " bcs = #{bcs} ";

	public String SELECT_FIELDS = " id, bcs, createDate ";
	

	public int insert(PetWeightInfo petWeightInfo);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer petId);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetWeightInfo petWeightInfo);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public PetWeightInfo get(Integer id);
	
	@Select("select pet_weight_info.* from pet_basic_info " + 
			"join pet on pet.basic = pet_basic_info.id " + 
			"join pet_weight_info on pet_weight_info.id = pet.weight " + 
			"where pet_basic_info.id = #{basicIdx} ")
	public PetWeightInfo getBcs(@Param("basicIdx") int basicIdx);
	
	
	@Select("select pet_weight_info.* from pet_weight_info join groups on pet_weight_info.petId = groups.petId where groups.groupId = #{groupId} and pet_weight_info.petId = #{petId}")
	public PetPhysicalInfo InfoCheck(String groupId, int petId);
	
	@Select("SELECT pet_weight_info.* FROM pet " + 
			"join group_pet_mapping on group_pet_mapping.petGroupCode = pet.petGroupCode " + 
			"join pet_weight_info on pet.weight = pet_weight_info.id " + 
			"WHERE group_pet_mapping.groupCode = #{groupCode} " + 
			"and pet.petIdx = #{petIdx}")
	public PetWeightInfo getPetWeightInformation(@Param("groupCode") String groupCode, @Param("petIdx") int petIdx);
	
	
}

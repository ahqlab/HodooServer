package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.PetBasicInfo;

@CacheNamespace
public interface PetBasicInfoMapper extends CRUDMapper<PetBasicInfo, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( id, userId, groupId, profileFilePath, profileFileName, petName, sex, petBreed , birthday, neutralization, createDate )";

	public String INSERT_VALUES = " ( null, #{userId}, #{groupId},  #{profileFilePath} , #{profileFileName} , #{petName} , #{sex} , #{petBreed} , #{birthday},  #{neutralization}, now() )";

	public String TABLE_NAME = " pet_basic_info ";

	public String UPDATE_VALUES = " profileFilePath = #{profileFilePath} , profileFileName = #{profileFileName} , petName = #{petName} , sex = #{sex} ,petBreed = #{petBreed} , birthday = #{birthday} , neutralization = #{neutralization} , createDate = now() ";

	public String SELECT_FIELDS = "  id, userId, profileFilePath, profileFileName, petName, sex, petBreed , birthday, neutralization, createDate  ";

	/*@Insert("INSERT INTO " + TABLE_NAME + " " + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	int insert(PetBasicInfo petBasicInfo);*/

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userId =  #{userId}")
	@Override
	PetBasicInfo get(Integer id);
	
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	PetBasicInfo getBasicInfo(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	int update(PetBasicInfo petBasicInfo);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	int delete(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<PetBasicInfo> getList();
	
	/*@Select("select petname from groups join pet_basic_info on groups.groupId = pet_basic_info.groupId where groups.groupId = #{groupId}")*/
	/*@Select("select * from pet_basic_info where pet_basic_info.groupId = #{groupId}")*/
	
	@Select("select pet_basic_info.* , pet_group_mapping.depth1 , pet_group_mapping.depth2, pet_group_mapping.depth3, pet_group_mapping.depth4 from pet_group_mapping join pet_basic_info on pet_group_mapping.petId = pet_basic_info.id WHERE pet_group_mapping.groupId = #{groupId}")
	List<PetBasicInfo> getMyPetList(@Param("groupId") int groupId);
	
	/*@Select("select distinct pet_basic_info.* from pet_basic_info "
			+ "join pet_chronic_disease on pet_basic_info.id = pet_chronic_disease.petId "
			+ "join pet_physical_info on pet_basic_info.id = pet_physical_info.petId "
			+ "join pet_weight_info on pet_basic_info.id = pet_weight_info.petId "
			+ "where pet_basic_info.groupId = #{groupId};")*/
	@Select("select distinct pet_basic_info.* from pet_basic_info "
			+ "where pet_basic_info.groupId = #{groupId};")
	List<PetBasicInfo> getMyRegisteredPetList(@Param("groupId") String groupId);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	PetBasicInfo getBasicInfoForPetId(Integer id);
	
	/*@Select("select pet_basic_info.* from pet_basic_info where groupId = #{groupId} and id = #{id}")
	public PetBasicInfo basicInfoCheck(@Param("groupId") String groupId, @Param("id") int id);*/
	
	@Select("SELECT pet_basic_info.id, pet_basic_info.profileFilePath, pet_basic_info.profileFileName, pet_basic_info.petName, b.${location }_name AS petBreed, pet_basic_info.sex, pet_basic_info.birthday, pet_basic_info.neutralization, pet_basic_info.createDate FROM pet  JOIN group_pet_mapping  ON group_pet_mapping.petGroupCode = pet.petGroupCode  JOIN pet_basic_info  ON pet.basic = pet_basic_info.id LEFT JOIN pet_breed_mapper m on pet_basic_info.id = m.basic_info_id LEFT JOIN pet_breed b ON m.breed_id = b.id WHERE group_pet_mapping.groupCode = #{groupCode} AND pet.petIdx = #{petIdx}")
	public PetBasicInfo getBasicInformation(@Param("location") String location, @Param("groupCode") String groupCode, @Param("petIdx") int petIdx);
	
	
	
	
}

package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.Mapping;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;

public interface PetMapper extends CRUDMapper<Pet, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( petIdx , petGroupCode , basic , disease, physical, weight, createDate )";

	public String INSERT_VALUES = " ( null , #{petGroupCode} , #{basic} , #{disease} , #{physical} , #{weight} ,  now() )";

	public String TABLE_NAME = " pet ";

	public String UPDATE_VALUES = " petGroupCode = #{petGroupCode} , basic = #{basic} ,  disease = #{disease} ,  physical = #{physical} ,  weight = #{weight} , createDate = now() ";

	public String SELECT_FIELDS = " deviceIdx , petGroupCode , basic , disease, physical, weight, createDate ";

	/*@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES ( null , (select petGroupCode FROM group_pet_mapping where groupCode = #{petGroupCode}) ,  #{basic} , #{disease} , #{physical} , #{weight} , now() ) ")
	@Override
	public int insert(Pet pet);*/

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
	
	@Select("select * from group_pet_mapping join pet on group_pet_mapping.petGroupCode = pet.petGroupCode join pet_basic_info on pet_basic_info.id = pet.basic join pet_weight_info on pet_weight_info.id = pet.weight where group_pet_mapping.groupCode = #{groupCode} AND pet.petIdx = #{petIdx}")
	public Pet aboutMyPet(String groupCode, int petIdx);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET disease = 0 WHERE petIdx =  #{petIdx} ")
	public void resetDisease(int petIdx);
	
	@Update("UPDATE " + TABLE_NAME + " SET physical = 0 WHERE petIdx =  #{petIdx} ")
	public void resetPhysical(int petIdx);
	
	@Update("UPDATE " + TABLE_NAME + " SET weight = 0 WHERE petIdx =  #{petIdx} ")
	public void resetWeight(int petIdx);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET disease = #{disease} WHERE petIdx =  #{petIdx} ")
	public int registDisease(@Param("disease") int disease, @Param("petIdx") int petIdx);
	
	@Update("UPDATE " + TABLE_NAME + " SET physical = #{physical} WHERE petIdx =  #{petIdx} ")
	public int registPhysical(@Param("physical") int physical, @Param("petIdx") int petIdx);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET weight = #{weight} WHERE petIdx =  #{petIdx} ")
	public int registWeight(@Param("weight") int weight, @Param("petIdx") int petIdx);
	
	
	/*			"join pet_chronic_disease on pet_chronic_disease.id = pet.disease " + 
*/
	
	@Select("select * " + 
			"from group_pet_mapping " + 
			"join pet on group_pet_mapping.petGroupCode = pet.petGroupCode " + 
			"join pet_basic_info on pet_basic_info.id = pet.basic " + 
			/*"join pet_physical_info on pet_physical_info.id = pet.physical " + */
			"join pet_weight_info on pet_weight_info.id = pet.weight " + 
			"where group_pet_mapping.groupCode = #{groupCode} ")
	@Results({
		@Result(column="petIdx", property="pet.petIdx"),
		@Result(column="petGroupCode", property="pet.petGroupCode"),
		@Result(column="basic", property="pet.basic"),
		@Result(column="disease", property="pet.disease"),
		@Result(column="physical", property="pet.physical"),
		@Result(column="weight", property="pet.weight"),
		
		@Result(column="pet_basic_info.id", property="petBasicInfo.id"),
		@Result(column="profileFilePath", property="petBasicInfo.profileFilePath"),
		@Result(column="profileFileName", property="petBasicInfo.profileFileName"),
		@Result(column="petName", property="petBasicInfo.petName"),
		@Result(column="petBreed", property="petBasicInfo.petBreed"),
		@Result(column="sex", property="petBasicInfo.sex"),
		@Result(column="birthday", property="petBasicInfo.birthday"),
		@Result(column="neutralization", property="petBasicInfo.neutralization"),
		
	/*	@Result(column="diseaseName", property="petChronicDisease.diseaseName"),
		
		@Result(column="width", property="petPhysicalInfo.width"),
		@Result(column="height", property="petPhysicalInfo.height"),
		@Result(column="weight", property="petPhysicalInfo.weight"),*/
		
		@Result(column="bcs", property="petWeightInfo.bcs"),
	})
	public List<PetAllInfos> aboutMyPetList(String groupCode);
	
	
	@Select("select * ,"
			+ "pet_basic_info.id as basicId , "
			+ "pet_chronic_disease.id as diseaseId ,"
			+ "pet_physical_info.id as physicalId ,"
			+ "pet_weight_info.id as weightId " 
			+ "from group_pet_mapping " + 
			"left join pet on group_pet_mapping.petGroupCode = pet.petGroupCode " + 
			"left join pet_chronic_disease on pet_chronic_disease.id = pet.disease " + 
			"left join pet_basic_info on pet_basic_info.id = pet.basic " + 
			"left join pet_physical_info on pet_physical_info.id = pet.physical " + 
			"left join pet_weight_info on pet_weight_info.id = pet.weight " + 
			"where group_pet_mapping.groupCode = #{groupCode} ")
	@Results({
		@Result(column="petIdx", property="pet.petIdx"),
		@Result(column="petGroupCode", property="pet.petGroupCode"),
		@Result(column="basic", property="pet.basic"),
		@Result(column="disease", property="pet.disease"),
		@Result(column="physical", property="pet.physical"),
		@Result(column="weight", property="pet.weight"),
		
		@Result(column="basicId", property="petBasicInfo.id"),
		@Result(column="profileFilePath", property="petBasicInfo.profileFilePath"),
		@Result(column="profileFileName", property="petBasicInfo.profileFileName"),
		@Result(column="petName", property="petBasicInfo.petName"),
		@Result(column="petBreed", property="petBasicInfo.petBreed"),
		@Result(column="sex", property="petBasicInfo.sex"),
		@Result(column="birthday", property="petBasicInfo.birthday"),
		@Result(column="neutralization", property="petBasicInfo.neutralization"),
		
		@Result(column="diseaseId", property="petChronicDisease.id"),
		@Result(column="diseaseName", property="petChronicDisease.diseaseName"),
		
		@Result(column="physicalId", property="petPhysicalInfo.id"),
		@Result(column="width", property="petPhysicalInfo.width"),
		@Result(column="height", property="petPhysicalInfo.height"),
		@Result(column="weight", property="petPhysicalInfo.weight"),
		
		@Result(column="weightId", property="petWeightInfo.id"),
		@Result(column="bcs", property="petWeightInfo.bcs")
	})
	public List<PetAllInfos> aboutMyPetListForIos(String groupCode);
	
	
	
	@Select("select * from pet " + 
			"	join pet_basic_info on pet_basic_info.id = pet.basic " + 
			"	join pet_chronic_disease on pet_chronic_disease.id = pet.disease " + 
			"	join pet_physical_info on pet_physical_info.id = pet.physical " + 
			"	join pet_weight_info on pet_weight_info.id = pet.weight " + 
			"	where pet.petIdx = ${petIdx}")
	@Results({
		@Result(column="petIdx", property="pet.petIdx"),
		@Result(column="petGroupCode", property="pet.petGroupCode"),
		@Result(column="basic", property="pet.basic"),
		@Result(column="disease", property="pet.disease"),
		@Result(column="physical", property="pet.physical"),
		@Result(column="weight", property="pet.weight"),
		
		@Result(column="profileFilePath", property="petBasicInfo.profileFilePath"),
		@Result(column="profileFileName", property="petBasicInfo.profileFileName"),
		@Result(column="petName", property="petBasicInfo.petName"),
		@Result(column="petBreed", property="petBasicInfo.petBreed"),
		@Result(column="sex", property="petBasicInfo.sex"),
		@Result(column="birthday", property="petBasicInfo.birthday"),
		@Result(column="neutralization", property="petBasicInfo.neutralization"),
		
		@Result(column="diseaseName", property="petChronicDisease.diseaseName"),
		
		@Result(column="width", property="petPhysicalInfo.width"),
		@Result(column="height", property="petPhysicalInfo.height"),
		@Result(column="weight", property="petPhysicalInfo.weight"),
		
		@Result(column="bcs", property="petWeightInfo.bcs"),
	})
	public PetAllInfos allInfoOnThePet(@Param("petIdx") int petIdx);
	
}

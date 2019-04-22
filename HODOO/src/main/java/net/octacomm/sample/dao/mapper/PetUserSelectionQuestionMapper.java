package net.octacomm.sample.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.BfiQuestion;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.PetUserSelectionQuestion;

public interface PetUserSelectionQuestionMapper  extends CRUDMapper<PetUserSelectionQuestion, DefaultParam, Integer> {
	
	public String INSERT_FIELDS = " ( questionIdx, bodyFat, playTime, active, createDate )";

	public String INSERT_VALUES = " ( null, #{bodyFat}, #{playTime}, #{active} , now() )";

	public String TABLE_NAME = " pet_user_selection_question ";

	public String UPDATE_VALUES = " bodyFat, = #{bodyFat} , playTime = #{playTime} , active = #{active} ";

	public String SELECT_FIELDS = " questionIdx, bodyFat, playTime, active, createDate ";
	

	public int insert(PetUserSelectionQuestion petWeightInfo);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE questionIdx =  #{questionIdx}")
	@Override
	public int delete(@Param("questionIdx") Integer questionIdx);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE questionIdx =  #{questionIdx}")
	@Override
	public int update(PetUserSelectionQuestion petWeightInfo);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE questionIdx =  #{questionIdx}")
	@Override
	public PetUserSelectionQuestion get(@Param("questionIdx") Integer questionIdx);
	
	@Select("select pet_user_selection_question.* from pet_basic_info " + 
			"join pet on pet.basic = pet_basic_info.id " + 
			"join pet_user_selection_question on pet_user_selection_question.questionIdx = pet.sltQst " + 
			"where pet_basic_info.id = #{basicIdx} ")
	public PetUserSelectionQuestion getBcs(@Param("basicIdx") int basicIdx);

	
	@Select("SELECT pet_user_selection_question.* FROM pet " + 
			"join group_pet_mapping on group_pet_mapping.petGroupCode = pet.petGroupCode " + 
			"join pet_user_selection_question on pet.sltQst = pet_user_selection_question.questionIdx " + 
			"WHERE group_pet_mapping.groupCode = #{groupCode} " + 
			"and pet.petIdx = #{petIdx}")
	public PetUserSelectionQuestion getPetWeightInformation(@Param("groupCode") String groupCode, @Param("petIdx") int petIdx);
	
	@Select("SELECT id, question FROM bfi_question where id = 1")
	public BfiQuestion test();

}

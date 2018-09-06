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

	public String INSERT_FIELDS = " ( petId, bcs, createDate )";

	public String INSERT_VALUES = " ( #{petId}, #{bcs}, now() )";

	public String TABLE_NAME = " pet_weight_info ";

	public String UPDATE_VALUES = " bcs = #{bcs} ";

	public String SELECT_FIELDS = " id, petId, bcs, createDate ";

	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(PetWeightInfo petWeightInfo);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	@Override
	public int delete(Integer petId);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(PetWeightInfo petWeightInfo);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petId =  #{petId}")
	@Override
	public PetWeightInfo get(Integer petId);
	
	@Select("select bcs from groups join pet_weight_info on groups.petId = pet_weight_info.petId where groups.groupId = #{groupId} and pet_weight_info.petId = #{petId}")
	public String getMyBcs(@Param("groupId") String groupId, @Param("petId") int petId);
	
	
	@Select("select pet_weight_info.* from pet_weight_info join groups on pet_weight_info.petId = groups.petId where groups.groupId = #{groupId} and pet_weight_info.petId = #{petId}")
	public PetPhysicalInfo InfoCheck(String groupId, int petId);
	
	
}

package net.octacomm.sample.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.BfiQuestion;
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
	
	@Select("SELECT id, question FROM bfi_question where id = 1")
	public BfiQuestion test();
	
	//SELECT ( ((SELECT AVG(VALUE) AS b FROM real_time_weight  WHERE createDate  BETWEEN  (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) - 1 ) DAY)) AND (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) - 7 ) DAY)) AND mac = '#{mac }') - (SELECT AVG(VALUE) AS a FROM real_time_weight  WHERE createDate  BETWEEN  (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) + 6 ) DAY)) AND (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) ) DAY)) AND mac = '#{mac }')) / (SELECT AVG(VALUE) AS a FROM real_time_weight  WHERE createDate  BETWEEN  (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) + 6 ) DAY)) AND (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) ) DAY)) AND mac = '#{mac }') * 100 ) AS result 
	@Select("SELECT IFNULL((b - a)/a*100, 0) as result FROM (SELECT IFNULL(AVG(VALUE), 0) AS a FROM real_time_weight  WHERE createDate  BETWEEN  (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) + 6 ) DAY)) AND (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) ) DAY)) AND mac = #{mac}) AS a, (SELECT IFNULL(AVG(VALUE), 0) AS b FROM real_time_weight  WHERE createDate  BETWEEN  (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) - 1 ) DAY)) AND (SELECT DATE_SUB( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )), INTERVAL ( DAYOFWEEK( (SELECT DATE_FORMAT( NOW(), '%Y-%m-%d' )) ) - 7 ) DAY)) AND mac = #{mac}) AS b")
	public float getWeekRate( @Param("mac") String mac );
	
}

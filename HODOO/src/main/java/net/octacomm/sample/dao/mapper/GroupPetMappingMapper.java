package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.GroupPetMapping;

public interface GroupPetMappingMapper extends CRUDMapper<GroupPetMapping, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( id , groupCode , petGroupCode , createDate )";

	public String INSERT_VALUES = " ( null , #{groupCode} , #{petGroupCode}  , now() )";

	public String TABLE_NAME = " group_pet_mapping ";

	public String UPDATE_VALUES = " groupCode = #{groupCode} , petGroupCode = #{petGroupCode} , createDate = now() ";

	public String SELECT_FIELDS = " id , groupCode , petGroupCode , createDate  ";

	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(GroupPetMapping groupPetMapping);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE petIdx =  #{petIdx}")
	@Override
	public int delete(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE petIdx =  #{petIdx} ")
	@Override
	public int update(GroupPetMapping groupPetMapping);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE petIdx =  #{petIdx} ")
	@Override
	public GroupPetMapping get(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE groupCode =  #{groupCode} ")
	public GroupPetMapping isEmpty(String groupCode);

}

package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.UserGroupMapping;

public interface UserGroupMappingMapper extends CRUDMapper<UserGroupMapping, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( id, userIdx, groupCode, createDate )";
	
	public String INSERT_VALUES = " ( #{id}, #{userIdx}, #{groupCode}, now() )";
	
	public String TABLE_NAME = " user_group_mapping ";
	
	public String UPDATE_VALUES = " userIdx = #{userIdx} , groupCode = #{groupCode}  , createDate = now() ";
	
	public String SELECT_FIELDS = " id, userIdx, groupCode, createDate ";
	
	@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(UserGroupMapping userGroupMapping);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(UserGroupMapping userGroupMapping);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public UserGroupMapping get(Integer id);
	
	@Select("SELECT g.id FROM " + TABLE_NAME + " g JOIN USER u ON g.userIdx = u.userIdx WHERE u.userIdx = #{idx}")
	public int getUserGroupId( @Param("idx") int idx );
	
	@Update("UPDATE " + TABLE_NAME + " SET groupCode = ( SELECT groupCode FROM (SELECT * FROM " + TABLE_NAME +  ") AS map WHERE userIdx = #{toUserIdx} )  WHERE userIdx = #{fromUserIdx}")
	int invitationApproval(@Param("toUserIdx") int toUserIdx, @Param("fromUserIdx") int fromUserIdx);
	
	@Update("update " + TABLE_NAME + " set groupCode = #{code} where userIdx = #{idx} ")
	int withdrawGroup(@Param("code") String code, @Param("idx") int idx);
	
	

}

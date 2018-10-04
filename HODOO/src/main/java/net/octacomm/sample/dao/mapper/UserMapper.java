package net.octacomm.sample.dao.mapper;


import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.User;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * The MyBatis Mapper of "user" table  
 * 
 * @author tykim
 * 
 */
@CacheNamespace
public interface UserMapper extends CRUDMapper<User, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( userIdx, email, password, nickname, sex, user.from , groupId, createDate )";
	
	public String INSERT_VALUES = " ( null, #{email}, #{password} , #{nickname} , #{sex} , #{from} , #{groupId},  now() )";
	
	public String TABLE_NAME = " USER ";
	
	public String UPDATE_VALUES = " email = #{email} , password = #{password} , nickname = #{nickname} , sex = #{sex} , from = #{from} , groupId = #{groupId} , createDate = now() ";
	
	public String BASIC_INFO_UPDATE_VALUES = "  nickname = #{nickname} , USER.from = #{from} ";
	
	public String SELECT_FIELDS = "  userIdx , email, password, nickname, sex, from , groupId, createDate  ";
	
	
	int insert(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userIdx =  #{userIdx}")
	@Override
	User get(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE userIdx =  #{userIdx}")
	@Override
	int update(User user);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE userIdx =  #{userIdx}")
	@Override
	int delete(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<User> getList();
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userIdx = #{userIdx}")
	User getUser(User user);

	@Select("SELECT * FROM " + TABLE_NAME + "  WHERE userIdx = #{userIdx} AND password = #{password} ")
	User getUserForAuth(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " join user_group_mapping on user_group_mapping.userIdx = user.userIdx where user.email =  #{email} AND user.password = #{password} ")
	User login(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + "  WHERE groupId = #{groupId}")
	List<User> getGroupMemner(@Param("groupId") String groupId);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET " + BASIC_INFO_UPDATE_VALUES + " WHERE userIdx =  #{userIdx}")
	Integer updateBasic(User user);

}

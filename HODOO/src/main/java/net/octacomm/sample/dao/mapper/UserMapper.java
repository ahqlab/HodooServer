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
	
	public String INSERT_FIELDS = " ( id, email, password, nickname, sex, user.from , groupId, create_date )";
	
	public String INSERT_VALUES = " ( null, #{email}, #{password} , #{nickname} , #{sex} , #{from} , #{groupId},  now() )";
	
	public String TABLE_NAME = " user ";
	
	public String UPDATE_VALUES = " email = #{email} , password = #{password} , nickname = #{nickname} , sex = #{sex} , from = #{from} , groupId = #{groupId} , create_date = now() ";
	
	public String SELECT_FIELDS = "  id, email, password, nickname, sex, from , groupId, create_date  ";
	
	
	/*@Insert("INSERT INTO " + TABLE_NAME + " " + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	int insert(User user);*/
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	User get(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	int update(User user);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	int delete(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<User> getList();
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	User getUser(User user);

	@Select("SELECT * FROM user WHERE id = #{id} AND password = #{password} ")
	User getUserForAuth(User user);
	
	@Select("SELECT * FROM user_group_mapping join user on user_group_mapping.userId = user.id where user.email =  #{email} AND user.password = #{password} ")
	User login(User user);
	
	@Select("SELECT * FROM user WHERE groupId = #{groupId}")
	List<User> getGroupMemner(@Param("groupId") String groupId);

}

package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.StandardHsv;

public interface GroupsMapper extends CRUDMapper<Groups, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( groupId )";
	
	public String INSERT_VALUES = " ( #{groupId} )";
	
	public String TABLE_NAME = " groups ";
	
	public String UPDATE_VALUES = " groupId = #{groupId} ";
	
	public String SELECT_FIELDS = " id, groupId ";
	
	/*@Insert("INSERT INTO " + TABLE_NAME  + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(Groups group);*/
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(Groups dogsAndUser);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public Groups get(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userId =  #{userId} and groupId =  #{groupId} and seq = 1 ")
	public Groups getGroups(Groups group);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userId =  #{userId} and groupId =  #{groupId} and seq = 1 ")
	public List<Groups> getListGroups(Groups group);
	

}

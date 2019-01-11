package net.octacomm.sample.dao.mapper;


import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.StandardHsv;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * The MyBatis Mapper of "user" table  
 * 
 * @author tykim
 * 
 */
@CacheNamespace
public interface StandardHsvMapper extends CRUDMapper<StandardHsv, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( id, type, level1, level2, level3 , level4, level5, level6, level7)";
	
	public String INSERT_VALUES = " ( #{type}, #{type}, #{level1}, #{level2}, #{level3}, #{level4}, #{level5},  #{level6}, #{level7} )";
	
	public String TABLE_NAME = " hsv ";
	
	public String UPDATE_VALUES = " level1 = #{level1} ,  level2 = #{level2}  ,  level3 = #{level3},   level4 = #{level4},  level5 = #{level5} ,  level6 = #{level6},  level7 = #{level7} ";
	
	public String SELECT_FIELDS = "   type, level1, level2, level3 , level4, level5, level6, level7  ";
	
	@Insert("INSERT INTO " + TABLE_NAME + " " + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(StandardHsv hsv);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(StandardHsv hsv);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public StandardHsv get(Integer id);

}

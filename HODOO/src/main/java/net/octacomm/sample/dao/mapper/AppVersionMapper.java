package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.domain.User;
import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.AppVersion;
import net.octacomm.sample.domain.DefaultParam;

public interface AppVersionMapper extends CRUDMapper<AppVersion, DefaultParam, Integer>{

	public String INSERT_FIELDS = " ( id, version, createDate )";

	public String INSERT_VALUES = " ( null, #{version}, now() )";

	public String TABLE_NAME = " app_version ";

	public String UPDATE_VALUES = " version = #{version}  ";

	public String SELECT_FIELDS = "  id, version, createDate ";

	int insert(User user);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	@Override
	AppVersion get(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	int update(AppVersion AppVersion);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	int delete(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<AppVersion> getList();

}

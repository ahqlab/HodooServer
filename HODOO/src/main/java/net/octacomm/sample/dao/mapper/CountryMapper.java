package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.Country;
import net.octacomm.sample.domain.DefaultParam;

public interface CountryMapper extends CRUDMapper<Country, DefaultParam, Integer>{
	
	public String TABLE_NAME = " country ";
	public String SELECT_OPTION = "";

	@Override
	int insert(Country domain);
	
	@Override
	Country get(Integer id);

	@Override
	int update(Country domain);

	@Override
	int delete(Integer id);

	@Override
	int getCountByParam(DefaultParam param);

	@Override
	List<Country> getListByParam(int startRow, int pageSize, DefaultParam param);

	@Override
	@Select("SELECT * FROM" + TABLE_NAME)
	List<Country> getList( );
	
	@Select("SELECT ${name} AS NAME, id FROM" + TABLE_NAME)
	List<Country> getNameList( @Param("name") String name );

	
}

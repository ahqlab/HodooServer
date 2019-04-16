package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.domain.User;
import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.AlarmItem;
import net.octacomm.sample.domain.AppVersion;
import net.octacomm.sample.domain.DefaultParam;

public interface AlarmMapper extends CRUDMapper<AlarmItem, DefaultParam, Integer>{

	public String TABLE_NAME = " alarm_item ";

	@Select("select *, ${language}_name as name FROM alarm_item")
	List<AlarmItem> getAlarmList(@Param("language") String language);
	
	@Select("SELECT number FROM alarm_mapper WHERE userIdx = #{userIdx }")
	int getAlarm(@Param("userIdx") int userIdx);


}

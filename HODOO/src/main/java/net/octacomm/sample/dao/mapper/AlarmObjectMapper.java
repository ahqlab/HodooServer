package net.octacomm.sample.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.AlarmObject;
import net.octacomm.sample.domain.DefaultParam;

public interface AlarmObjectMapper extends CRUDMapper<AlarmObject, DefaultParam, Integer> {
	
	@Select("SELECT number FROM alarm_mapper WHERE userIdx = #{userIdx }")
	int getAlarm(@Param("userIdx") int userIdx);
	
	@Select("SELECT COUNT(*) FROM alarm_mapper WHERE userIdx = #{userIdx }")
	int checkAlarmMapper(@Param("userIdx") int userIdx);
	
	@Insert("INSERT INTO alarm_mapper (idx, userIdx, number) VALUES (null, #{userIdx}, #{number})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="idx", before=true, resultType=int.class)
	int saveAlarmMapper (AlarmObject object);
	
	@Update("UPDATE alarm_mapper SET number = #{number } where userIdx = #{userIdx }")
	int updateAlarmMapper( @Param("userIdx") int userIdx, @Param("number") int number );
	
}

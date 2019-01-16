package net.octacomm.sample.dao.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.StandardHsv;
import net.octacomm.sample.domain.Statistics;
import net.octacomm.sample.domain.User;

public interface RealTimeWeightMapper extends CRUDMapper<RealTimeWeight, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( mac, value, type , tag, createDate )";

	public String INSERT_VALUES = " ( #{mac}, #{value}, #{type}, #{tag}, now())";

	public String TABLE_NAME = " real_time_weight ";

	public String UPDATE_VALUES = " mac = #{mac} ,  value = #{value} ";

	public String SELECT_FIELDS = " id, mac, value, type,  createDate ";

	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(RealTimeWeight realTimeWeight);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(RealTimeWeight realTimeWeight);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public RealTimeWeight get(Integer id);

	@Select("select value from " + TABLE_NAME + " where mac = #{mac} order by createDate desc limit 1")
	public Float getLatelyData(@Param("mac") String mac);

	@Select("select value from " + TABLE_NAME + " where mac = #{mac} order by createDate desc limit 4")
	public List<Float> getRealTimeList(@Param("mac") String mac);

	
	@Select("select u.* from device d left join user_group_mapping m on d.groupCode = m.groupCode left join user u on u.userIdx = m.userIdx where d.serialNumber = #{mac} ")
	public List<User> getUserList( @Param("mac") String mac );
	
	public RealTimeWeight getListofDeviceList(@Param("date") String date, @Param("devices") List<Device> devices, @Param("type") int type);
	

	public List<Statistics> getStatisticsOfTime(HashMap<String, Object> map);

	public List<Statistics> getStatisticsOfDay(HashMap<String, Object> map);

	public List<Statistics> getStatisticsOfWeek(HashMap<String, Object> map);

	public List<Statistics> getStatisticsOfMonth(HashMap<String, Object> map);

	public List<Statistics> getStatisticsOfMonth(@Param("devices") List<Device> devices, @Param("year") String year, @Param("type") int type);

	public List<Statistics> getStatisticsOfYear(HashMap<String, Object> map);

}

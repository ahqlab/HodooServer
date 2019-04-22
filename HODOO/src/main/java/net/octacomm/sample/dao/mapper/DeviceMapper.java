package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Device;

public interface DeviceMapper extends CRUDMapper<Device, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( deviceIdx , groupCode , serialNumber , createDate )";

	public String INSERT_VALUES = " ( null , #{groupCode} , #{serialNumber} , now() )";

	public String TABLE_NAME = " device ";

	public String UPDATE_VALUES = " groupCode = #{groupCode} , serialNumber = #{serialNumber} , createDate = now() ";

	public String SELECT_FIELDS = " deviceIdx , groupCode , serialNumber , connect, createDate ";

	//@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	//@Override
	public int insert(Device device);
	
	@Insert("INSERT INTO " + TABLE_NAME + " ( deviceIdx , mode, groupCode , serialNumber , createDate )" + " VALUES " + " ( null , #{mode} , #{groupCode} , #{serialNumber} , now() )")
	public int artificialDeviceInsert(Device device);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE deviceIdx =  #{deviceIdx}")
	@Override
	public int delete(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE deviceIdx =  #{deviceIdx}")
	@Override
	public int update(Device device);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE deviceIdx =  #{deviceIdx}")
	@Override
	public Device get(Integer id);
	
	
	/*@Select("select device.* from user join user_group_mapping on user.userIdx = user_group_mapping.userIdx join device on user_group_mapping.groupCode = device.groupCode where user_group_mapping.groupCode = #{groupCode} and device.connect = 'ON' and device.isDel = 'CONNECTED'")*/
	@Select("select * FROM " + TABLE_NAME + " where device.groupCode = #{groupCode} and device.isDel = 'CONNECTED'")
	public List<Device> myDeviceList(@Param("groupCode") String groupCode);
	
	@Select("select count(*) FROM " + TABLE_NAME + " where device.groupCode = #{groupCode} and device.isDel = 'CONNECTED'")
	public int myDeviceListCount(@Param("groupCode") String groupCode);
	
	
	/*@Select("select device.* from user join user_group_mapping on user.userIdx = user_group_mapping.userIdx join device on user_group_mapping.groupCode = device.groupCode where user_group_mapping.groupCode = #{groupCode}")*/
	@Select("select * FROM " + TABLE_NAME + " where device.groupCode = #{groupCode}  and device.isDel = 'CONNECTED'")
	public List<Device> myAllDeviceList(@Param("groupCode") String groupCode);
	
	
	@Update("UPDATE " + TABLE_NAME + " SET  connect = '${connect == true ? 'ON' : 'OFF'}' WHERE deviceIdx =  #{deviceIdx}")
	public int changeDeviceConnectStatus(@Param("deviceIdx") int deviceIdx,  @Param("connect") boolean connect);
	
	@Update("UPDATE " + TABLE_NAME + " SET  groupCode = #{groupCode} , isDel = '${isDel == true ? 'CONNECTED' : 'DISCONNECTED'}' WHERE deviceIdx =  #{deviceIdx}")
	public int changeDeviceConnection(@Param("groupCode") String groupCode, @Param("deviceIdx") int deviceIdx,  @Param("isDel") boolean isDel);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE serialNumber =  #{serialNumber}")
	public List<Device> getRegisted(Device device);

	public int changeDeviceConnectionForIos(Device dv, @Param("isDel") boolean isDel);
	
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE serialNumber =  #{serialNumber}")
	public int testDeleteDevice(@Param("serialNumber") String serialNumber);

	
	@Select("select * FROM " + TABLE_NAME + " where device.groupCode = #{groupCode} and device.isDel = 'CONNECTED'  and mode = 1 ")
	public Device getDeviceInfoByGroupCode(@Param("groupCode") String groupCode);


}

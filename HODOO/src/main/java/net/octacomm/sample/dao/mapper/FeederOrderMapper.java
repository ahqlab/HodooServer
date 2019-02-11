package net.octacomm.sample.dao.mapper;


import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.FeedOrders;
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
 * @author tykim.
 * 
 */
@CacheNamespace
public interface FeederOrderMapper extends CRUDMapper<FeedOrders, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( orderIdx, groupCode, userIdx, feedIdx, petIdx, rer )";
	
	public String INSERT_VALUES = " ( null, #{groupCode}, #{userIdx} , #{feedIdx} , #{petIdx} , #{rer} )";
	
	public String TABLE_NAME = " feed_orders ";
	
	public String UPDATE_VALUES = " groupCode = #{groupCode} , userIdx = #{userIdx} , feedIdx = #{feedIdx} , petIdx = #{petIdx} , rer = #{rer}  ";
	
	public String SELECT_FIELDS = "  orderIdx, groupCode, userIdx, feedIdx, petIdx, rer ";
	
	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	int insert(FeedOrders feedOrders);
	
	@Select("SELECT FEED_ORDERS.* , FEED.calculationCalories as calories FROM FEED_ORDERS join FEED ON feed_orders.feedIdx = feed.id WHERE orderIdx =  #{orderIdx}")
	@Override
	FeedOrders get(Integer orderIdx);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE orderIdx =  #{orderIdx}")
	@Override
	int update(FeedOrders feedOrders);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE orderIdx =  #{orderIdx}")
	@Override
	int delete(Integer orderIdx);
	
	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<FeedOrders> getList();
	

}

package net.octacomm.sample.dao.mapper;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.MealTip;
import net.octacomm.sample.domain.Notice;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.WeightTip;
import net.octacomm.sample.domain.param.NoticeParam;

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
public interface NoticeMapper extends CRUDMapper<Notice, NoticeParam, Integer> {

	public String INSERT_FIELDS = " ( noticeIdx,  title, content, createDate )";

	public String INSERT_VALUES = " ( null,  #{title} , #{content}, now() )";

	public String TABLE_NAME = " notice ";

	public String UPDATE_VALUES = "  title = #{title} , content = #{content} , lastModified = now() ";

	public String SELECT_FIELDS = "  noticeIdx , title , content, lastModified, createDate ";
	
	
	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	int insert(Notice notice);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE noticeIdx =  #{noticeIdx}")
	@Override
	Notice get(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE tipIdx =  #{tipIdx}")
	@Override
	int update(Notice notice);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE noticeIdx =  #{noticeIdx}")
	@Override
	int delete(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<Notice> getList();
	
	@Select("SELECT noticeIdx , ${language}_title as title , ${language}_content as content , createDate  FROM " + TABLE_NAME + " WHERE ${language}_title is not NULL and ${language}_content is not NULL LIMIT #{pageSize}  OFFSET #{startRow}")
	List<Notice> geNoticetList(@Param("language") String language, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

}

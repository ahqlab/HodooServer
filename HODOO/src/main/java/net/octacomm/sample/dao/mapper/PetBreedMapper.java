package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.PetBreed;

public interface PetBreedMapper extends CRUDMapper<PetBreed, DefaultParam, Integer> {
	
	String TABLE_NAME = "pet_breed";
	String MAPPER_TABLE_NAME = "pet_breed_mapper";

	@Override
	int insert(PetBreed domain);

	@Override
	PetBreed get(Integer id);

	@Override
	int update(PetBreed domain);

	@Override
	int delete(Integer id);

	@Override
	int getCountByParam(DefaultParam param);

	@Override
	List<PetBreed> getListByParam(int startRow, int pageSize, DefaultParam param);

	@Override
	List<PetBreed> getList();
	
	
	@Select("SELECT id, ${location}_name as name FROM pet_breed")
	List<PetBreed> getAllList( @Param("location") String location );
	
	@Insert("insert into " + MAPPER_TABLE_NAME + " (basic_info_id, breed_id) values (#{basicInfoId }, #{breedId })")
	int insertPetBreedMapper( @Param("basicInfoId") int basicInfoId,@Param("breedId") int breedId );
	
	@Update("UPDATE " + MAPPER_TABLE_NAME + " SET breed_id = #{breedId } WHERE basic_info_id = #{basicInfoId }")
	int updatePetBreedMapper( @Param("basicInfoId") int basicInfoId,@Param("breedId") int breedId );
	
	@Select("select count(*) from " + MAPPER_TABLE_NAME + " where basic_info_id = #{basicInfoId }")
	int getBreedMapperCount( @Param("basicInfoId") int basicInfoId);

}

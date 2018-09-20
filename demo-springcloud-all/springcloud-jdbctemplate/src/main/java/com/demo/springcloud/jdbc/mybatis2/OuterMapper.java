package com.demo.springcloud.jdbc.mybatis2;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.springcloud.jdbc.mybatis1.TaskModel;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月20日 下午4:22:03
 *
 */
@Mapper
public interface OuterMapper {
	
	/**
	 * 
	 * @return
	 */
    @Select("SELECT * FROM sentinelClan;")
    public List<TaskModel> getAllClan();

	/**
	 * 
	 * @return
	 */
    @Select("SELECT * FROM club_analysis.task_main WHERE id = #{id}")
    public TaskModel getOneClan(@Param("id") Integer id);

	/**
	 * 
	 * @return
	 */
    @Insert("INSERT INTO sentinelClan (id,clanName,topicNames,bufferTime,countWidth,countPercent,alarmGroup,status,createTime,updateTime) " +
            "VALUES( #{id}, #{clanName}, #{topicNames}, #{bufferTime}, #{countWidth}, #{countPercent}, #{alarmGroup}, #{status}, #{createTime}, #{updateTime})")
    public int insertOne(TaskModel sentinelClan);

	/**
	 * 
	 * @return
	 */
    @Update("UPDATE sentinelClan SET clanName = #{clanName},topicNames = #{topicNames},bufferTime = #{bufferTime}," +
            "countWidth = #{countWidth},countPercent = #{countPercent},alarmGroup = #{alarmGroup},status = #{status}," +
            "createTime=#{createTime}, updateTime=#{updateTime}" +
            "WHERE id = #{id}")
    public int updateOne(TaskModel sentinelClan);
}
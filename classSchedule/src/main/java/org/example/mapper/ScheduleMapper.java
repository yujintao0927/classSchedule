package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Schedule;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Insert("insert into map_user_class(username,classId) values(#{username}, #{classId})")
    void addClass(String username, String classId) ;

    @Select("select classId from map_user_class WHERE username=#{username}")
    List<String> findClassId(String username);

    @Select("select * from class WHERE classId=#{classId}")
    List<Schedule> findClassById(String classId);
}

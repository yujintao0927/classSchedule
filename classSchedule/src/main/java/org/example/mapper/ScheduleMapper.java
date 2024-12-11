package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Schedule;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Insert("insert into map_user_class(username,classId, classTimeAndLocation, teacherName) values(#{username}, #{classId}, #{classTimeAndLocation}, #{teacherName})")
    void addClass(String username, String classId, String classTimeAndLocation, String teacherName) ;

    @Select("select classId from map_user_class WHERE username=#{username}")
    List<String> findClassId(String username);

    @Select("select * from class WHERE classId=#{classId} AND classTimeAndLocation=#{classTimeAndLocation} AND classTeacher=#{teacherName}")
    Schedule findClassById(String classId, String classTimeAndLocation, String teacherName);

    @Delete("DELETE FROM map_user_class WHERE username = #{username}")
    void deleteUserCourses(String username);

    @Select("select classTimeAndLocation from map_user_class WHERE username=#{username}")
    List<String> findClassTimeAndLocation(String username);

    @Select("select * from class WHERE classId=#{s} AND classTimeAndLocation=#{s1}")
    List<Schedule> findClassByIdd(String s, String s1);

    @Select("select teacherName from map_user_class WHERE username=#{username}")
    List<String> findClassTeacherName(String username);
}

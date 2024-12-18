package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Schedule;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Insert("insert into map_user_class(username,classId, classTimeAndLocation, classTeacher,classPoint,className) values(#{username}, #{classId}, #{classTimeAndLocation}, #{classTeacher}, #{classPoint}, #{className})")
    void addClass(String username, String classId, String classTimeAndLocation, String classTeacher, String classPoint, String className) ;

    @Select("select * from class WHERE classId=#{classId} AND classTimeAndLocation=#{classTimeAndLocation} AND classTeacher=#{teacherName}")
    Schedule findClassFromClass(String classId, String classTimeAndLocation, String teacherName);

    @Delete("DELETE FROM map_user_class WHERE username = #{username}")
    void deleteUserCourses(String username);

    @Select("select username, classId, classTimeAndLocation, classTeacher, classPoint, className from map_user_class WHERE username=#{userName}")
    List<Schedule> findClassFromMap(String userName);
}

package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Schedule;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from class where classTeacher LIKE CONCAT('%', #{teacher}, '%')")
    List<Schedule> search(String teacher);
}
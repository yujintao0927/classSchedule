package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Schedule;

import java.util.List;

@Mapper
public interface ClassroomMapper {
    @Select("select * from class WHERE classTimeAndLocation LIKE CONCAT('%', #{classroom}, '%')")
    List<Schedule> searchClassroom(String classroom);
}

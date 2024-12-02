package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

    @Insert("insert into map_user_class(username,classId) values(#{username}, #{classId})")
    void addClass(String username, String classId) ;

}

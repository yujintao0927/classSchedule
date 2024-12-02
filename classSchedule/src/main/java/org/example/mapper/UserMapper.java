package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from users where username=#{username}")
    User findByUserName(String username);

    @Insert("insert into users(username,password) values(#{username}, #{password})")
    void register(String username, String password);
}

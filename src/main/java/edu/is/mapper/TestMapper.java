package edu.is.mapper;

import edu.is.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Results({
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password")

    })
    @Select("select * from users where name=#{username} and password=#{password}")
    User selectUser(User user);
}

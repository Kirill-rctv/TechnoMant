package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.models.Role;

@Component
@Mapper
public interface RoleRepository {

    @Select("SELECT role_id, role FROM roles WHERE role = #{role}")
    @Results(value = {
            @Result(column = "role_id", property = "id"),
            @Result(column = "role", property = "name")
    })
    Role get(String role);
}


package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.models.Role;
import ru.kochnev.technomant.SpringBoot.models.UserRole;

import java.util.List;
import java.util.UUID;

@Component
@Mapper
public interface UserRoleRepository {

    @Insert("INSERT INTO user_role(user_id, role_id) " +
            "VALUES(#{userId}::UUID, #{roleId}::UUID)")
    void insert(UserRole userRole);

    @Select("SELECT roles.role_id as id, roles.role as name FROM user_role " +
            "LEFT JOIN roles ON user_role.role_id = roles.role_id " +
            "WHERE user_id = #{userId}::UUID")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name")
    })
    List<Role> findRolesByUserId(UUID userId);
}

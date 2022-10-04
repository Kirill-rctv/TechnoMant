package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.*;
import ru.kochnev.technomant.SpringBoot.models.User;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserRepository {

    @Insert("INSERT INTO users (id, active, name, password) " +
            "VALUES (#{id}::uuid, #{active}, #{name}, #{password})")
    int insert(User user);

    @Select("SELECT id, active, name, password FROM users WHERE id = #{id}::uuid")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "active", property = "active"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "password", property = "passwordConfirm"),
            @Result(column = "id", property = "roles", javaType = List.class,
                    many = @Many(select = "ru.kochnev.technomant.SpringBoot.repositories.UserRoleRepository.findRolesByUserId"))
    })
    User findById(UUID id);

    @Select("SELECT id, active, name, password FROM users WHERE name = #{name}")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "active", property = "active"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "password", property = "passwordConfirm"),
            @Result(column = "id", property = "roles", javaType = List.class,
                    many = @Many(select = "ru.kochnev.technomant.SpringBoot.repositories.UserRoleRepository.findRolesByUserId"))
    })
    User findByName(String name);

    @Select("SELECT id, active, name, password FROM users")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "active", property = "active"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "password", property = "passwordConfirm"),
            @Result(column = "id", property = "roles", javaType = List.class,
                    many = @Many(select = "ru.kochnev.technomant.SpringBoot.repositories.UserRoleRepository.findRolesByUserId"))
    })
    List<User> findAll();

    @Update("UPDATE users SET name = #{name}, password = #{password} WHERE id = #{id}::uuid")
    int update(User user);

    @Delete("DELETE FROM user_role WHERE user_id = #{id}::uuid; " +
            "DELETE FROM users WHERE id = #{id}::uuid;")
    int deleteById(UUID id);
}
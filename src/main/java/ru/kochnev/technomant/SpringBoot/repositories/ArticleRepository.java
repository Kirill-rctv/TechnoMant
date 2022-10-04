package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.*;
import ru.kochnev.technomant.SpringBoot.models.Article;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ArticleRepository {

    @Insert("INSERT INTO articles (id, author_id, name, content, date_publishing) " +
            "VALUES (#{id}::uuid, #{authorId}::uuid, #{name}, #{content}, #{datePublishing})")
    int insert(Article article);

    @Select("SELECT id, author_id, name, content, date_publishing FROM articles WHERE id = #{id}::uuid")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "content", column = "content"),
            @Result(property = "datePublishing", column = "date_publishing")
    })
    Article findById(UUID id);

    @Select("SELECT id, author_id, name, date_publishing FROM articles ORDER BY date_publishing LIMIT #{limit} OFFSET #{offset};")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "datePublishing", column = "date_publishing")
    })
    List<Article> findPaginated(int offset, int limit);

    @Update("UPDATE articles SET name = #{name}, content = #{content}, date_publishing = #{datePublishing} WHERE id = #{id}::uuid")
    int update(Article article);

    @Delete("DELETE FROM articles WHERE id = #{id}::uuid")
    int deleteById(UUID id);
}


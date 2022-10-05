package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.models.Statistic;

import java.util.List;

@Component
@Mapper
public interface StatisticRepository {

    @Select("SELECT COUNT(id) AS count, date_publishing " +
            "FROM articles " +
            "WHERE date_publishing BETWEEN #{date}::timestamp - INTERVAL '8 days' AND #{date}::timestamp + INTERVAL '1 day' " +
            "GROUP BY date_publishing " +
            "ORDER BY date_publishing;")
    @Results(value = {
            @Result(property = "numOfArticles", column = "count"),
            @Result(property = "date", column = "date_publishing")
    })
    List<Statistic> get(String date);
}


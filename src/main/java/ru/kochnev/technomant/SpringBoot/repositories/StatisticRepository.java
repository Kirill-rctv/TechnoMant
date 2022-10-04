package ru.kochnev.technomant.SpringBoot.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.models.Statistic;

@Component
@Mapper
public interface StatisticRepository {

    @Select("SELECT COUNT(*) AS count FROM articles WHERE date_publishing BETWEEN #{date}::timestamp - INTERVAL '8 days' AND #{date}::timestamp + INTERVAL '1 day';")
    @Results(value = {
            @Result(column = "count", property = "numOfArticles")
    })
    Statistic get(String date);
}


package ru.kochnev.technomant.SpringBoot.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.Statistic;
import ru.kochnev.technomant.SpringBoot.services.StatisticService;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public Statistic getStatistic(@JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX") @RequestBody String date) throws MyException {
        return statisticService.get(date);
    }
}
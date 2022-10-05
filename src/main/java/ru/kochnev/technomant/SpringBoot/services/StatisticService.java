package ru.kochnev.technomant.SpringBoot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kochnev.technomant.SpringBoot.models.Statistic;
import ru.kochnev.technomant.SpringBoot.repositories.StatisticRepository;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public Map<OffsetDateTime, Integer> get(String date) {
        List<Statistic> listOfStatisticsPerDay = statisticRepository.get(date);
        HashMap<OffsetDateTime, Integer> mapOfStatisticPerWeek = new HashMap<>();
        for (Statistic statistic : listOfStatisticsPerDay) {
            mapOfStatisticPerWeek.put(statistic.getDate().truncatedTo(ChronoUnit.DAYS), statistic.getNumOfArticles());
        }
        for (OffsetDateTime day = OffsetDateTime.parse(date).truncatedTo(ChronoUnit.DAYS).minusDays(6); day.isBefore(OffsetDateTime.parse(date)) || day.equals(OffsetDateTime.parse(date)); day = day.plusDays(1) ) {
            if (!mapOfStatisticPerWeek.containsKey(day)) {
                mapOfStatisticPerWeek.put(day, 0);
            }
        }
        return mapOfStatisticPerWeek;
    }
}
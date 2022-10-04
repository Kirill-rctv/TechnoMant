package ru.kochnev.technomant.SpringBoot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kochnev.technomant.SpringBoot.models.Statistic;
import ru.kochnev.technomant.SpringBoot.repositories.StatisticRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public Statistic get(String date) {
        Optional<Statistic> optionalStatistic = Optional.ofNullable(statisticRepository.get(date));
        return optionalStatistic.orElse(new Statistic(0));
    }
}
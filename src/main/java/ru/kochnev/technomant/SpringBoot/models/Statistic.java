package ru.kochnev.technomant.SpringBoot.models;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Statistic {

    final private Integer numOfArticles;
    final private OffsetDateTime date;

}

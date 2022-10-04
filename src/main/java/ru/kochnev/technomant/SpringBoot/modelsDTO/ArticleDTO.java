package ru.kochnev.technomant.SpringBoot.modelsDTO;

import lombok.Data;

import java.util.UUID;

@Data
public class ArticleDTO {
    private UUID id;
    private UUID authorId;
    private String name;
    private String content;
    private String datePublishing;
}

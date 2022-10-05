package ru.kochnev.technomant.SpringBoot.models;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.kochnev.technomant.SpringBoot.modelsDTO.ArticleDTO;

import java.util.List;

@Data
@Accessors(chain = true)
public class Page {

    List<ArticleDTO> currentPage;
    Integer numOfNextPage;
}

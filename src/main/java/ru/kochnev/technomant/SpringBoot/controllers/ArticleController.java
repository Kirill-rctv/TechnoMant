package ru.kochnev.technomant.SpringBoot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kochnev.technomant.SpringBoot.models.Article;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.ArticleDTO;
import ru.kochnev.technomant.SpringBoot.services.ArticleService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public void save(@RequestBody @Valid Article article) {
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        article.setAuthorId(authorizedUser.getId());
        articleService.save(article);
    }

    @GetMapping("/{id}")
    public ArticleDTO getById(@PathVariable("id") String id) throws MyException {
        return articleService.getById(id);
    }

    @GetMapping(params = { "page", "size" })
    public List<ArticleDTO> getPaginated(@RequestParam("page") int page, @RequestParam("size") int size) throws MyException {
        return articleService.getPaginated(page, size);
    }

    @PutMapping
    public void update(@RequestBody @Valid Article article) {
        articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        articleService.deleteById(id);
    }
}

package ru.kochnev.technomant.SpringBoot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kochnev.technomant.SpringBoot.models.*;
import ru.kochnev.technomant.SpringBoot.modelsDTO.ArticleDTO;
import ru.kochnev.technomant.SpringBoot.services.ArticleService;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Pojo> save(@RequestBody @Valid Article article) {
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        article.setAuthorId(authorizedUser.getId());
        Pojo response;
        try {
            UUID uuid = articleService.save(article).getId();
            return new ResponseEntity<>(new Pojo(true, uuid, "OK"), HttpStatus.OK);
        } catch (Error e) {
            return new ResponseEntity<>(new Pojo(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ArticleDTO getById(@PathVariable("id") String id) throws MyException {
        return articleService.getById(id);
    }

    @GetMapping(params = { "page", "size" })
    public Page getPaginated(@RequestParam("page") int page, @RequestParam("size") int size) throws MyException {
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

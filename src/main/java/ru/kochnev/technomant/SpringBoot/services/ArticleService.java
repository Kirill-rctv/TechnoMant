package ru.kochnev.technomant.SpringBoot.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kochnev.technomant.SpringBoot.models.Article;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.Page;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.ArticleDTO;
import ru.kochnev.technomant.SpringBoot.repositories.ArticleRepository;
import ru.kochnev.technomant.SpringBoot.validators.AuthorValidator;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final AuthorValidator authorValidator;

    public Article findById(UUID id) {
        Optional<Article> optionalArticle = Optional.ofNullable(articleRepository.findById(id));
        return optionalArticle.orElseThrow(() -> new MyException("Article not found"));
    }

    @Transactional
    public Article save(Article article) {
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        article
                .setId(UUID.randomUUID())
                .setAuthorId(authorizedUser.getId());
        articleRepository.insert(article);
        return article;
    }

    public ArticleDTO getById(String id) throws MyException {
        return modelMapper.map(findById(UUID.fromString(id)), ArticleDTO.class);
    }

    public Page getPaginated(int numOfpage, int size) throws MyException {
        Page page = new Page();
        page.setCurrentPage(articleRepository.findPaginated(numOfpage * size - 1, size).stream().map(i -> modelMapper.map(i, ArticleDTO.class)).toList());
        page.setNumOfNextPage(page.getCurrentPage().size() < size ? null : numOfpage++);
        return page;
    }

    @Transactional
    public void update(Article updatedArticle) {
        if(authorValidator.authorValid(articleRepository.findById(updatedArticle.getId())
                .getAuthorId())) {
            if (articleRepository.update(updatedArticle) == 0) {
                throw new MyException("Error updating article. Article not found");
            }
        } else {
            throw new MyException("Access denied!");
        }

    }

    @Transactional
    public void deleteById(String id) {
        if(authorValidator.authorValid(articleRepository.findById(UUID.fromString(id)).getAuthorId())) {
            if (articleRepository.deleteById(UUID.fromString(id)) == 0) {
                throw new MyException("Error deleting article. Article not found");
            }
        } else {
            throw new MyException("Access denied!");
        }
    }
}
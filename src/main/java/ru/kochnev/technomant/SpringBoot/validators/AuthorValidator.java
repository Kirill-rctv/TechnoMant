package ru.kochnev.technomant.SpringBoot.validators;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.models.Role;
import ru.kochnev.technomant.SpringBoot.models.User;

import java.util.UUID;

import static ru.kochnev.technomant.SpringBoot.models.Role.RoleName.ROLE_ADMIN;

@Component
public class AuthorValidator {

    public boolean authorValid(UUID authorId) {
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean roleAdminExist = authorizedUser.getRoles().stream()
                .map(Role::getName)
                .anyMatch(ROLE_ADMIN::equals);
        return roleAdminExist || authorizedUser.getId().equals(authorId);
    }
}

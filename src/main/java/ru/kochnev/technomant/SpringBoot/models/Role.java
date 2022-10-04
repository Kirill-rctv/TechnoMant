package ru.kochnev.technomant.SpringBoot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    private UUID id;
    private RoleName name;

    public enum RoleName {
        ROLE_ADMIN, ROLE_WRITER
    }

    @Override
    public String getAuthority() {
        return name.toString();
    }
}

package ru.kochnev.technomant.SpringBoot.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kochnev.technomant.SpringBoot.annotations.PasswordConfirm;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@PasswordConfirm
@Accessors(chain = true)
public class User implements UserDetails {
    private UUID id;
    @NonNull
    @NotBlank(message = "name of user cannot be empty ")
    private String name;
    @NonNull
    @NotBlank(message = "password of user cannot be empty ")
    private String password;
    @NonNull
    @NotBlank(message = "password confirmation of user cannot be empty ")
    private String passwordConfirm;
    private int active;
    List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active == 1;
    }
}
package ru.kochnev.technomant.SpringBoot.modelsDTO;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserDTO {
    private UUID id;
    @NotBlank(message = "name of user cannot be empty ")
    private String name;
}
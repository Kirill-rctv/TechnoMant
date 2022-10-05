package ru.kochnev.technomant.SpringBoot.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.kochnev.technomant.SpringBoot.annotations.NameLengthValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@NameLengthValid
@Accessors(chain = true)
public class Article {
    private UUID id;
    private UUID authorId;
    @NonNull
    @NotBlank(message = "name of article cannot be empty ")
    private String name;
    @NonNull
    @NotBlank(message = "content of article cannot be empty ")
    private String content;
    @NonNull
    @NotNull(message = "publishing date of article cannot be empty ")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    private OffsetDateTime datePublishing;
}

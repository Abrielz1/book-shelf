package ru.skillbox.bookshelf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookNewDto {

    @NotNull
    @NotBlank
    private String nameAuthor;

    @NotNull
    @NotBlank
    private String bookName;

    @NotNull
    @NotBlank
    private String category;
}

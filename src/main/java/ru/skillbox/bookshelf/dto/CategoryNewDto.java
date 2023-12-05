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
public class CategoryNewDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private BookNewDto bookNewDto;
}

package ru.skillbox.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long Id;

    private String name;

    private BookResponseDto book;

}

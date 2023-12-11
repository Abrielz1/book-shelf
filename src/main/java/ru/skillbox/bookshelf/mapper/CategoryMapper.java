package ru.skillbox.bookshelf.mapper;

import lombok.Data;
import ru.skillbox.bookshelf.dto.CategoryNewDto;
import ru.skillbox.bookshelf.dto.CategoryResponseDto;
import ru.skillbox.bookshelf.entity.Book;
import ru.skillbox.bookshelf.entity.Category;

@Data
public class CategoryMapper {

    public static Category toCategory(CategoryNewDto categoryNewDto) {
        return Category.builder()
                .Id(null)
                .name(categoryNewDto.getName())
                .build();
    }

    public static CategoryResponseDto categoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .Id(category.getId())
                .name(category.getName())
                .build();
    }
}

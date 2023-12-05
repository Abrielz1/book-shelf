package ru.skillbox.bookshelf.mapper;

import lombok.Data;
import ru.skillbox.bookshelf.dto.BookNewDto;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.dto.CategoryNewDto;
import ru.skillbox.bookshelf.entity.Book;
import ru.skillbox.bookshelf.entity.Category;

import static ru.skillbox.bookshelf.mapper.CategoryMapper.toCategory;

@Data
public class BookMapper {

    public static Book toBook(BookNewDto bookNewDto, CategoryNewDto categoryNewDto) {
        return Book.builder()
                .Id(null)
                .nameAuthor(bookNewDto.getNameAuthor())
                .bookName(bookNewDto.getBookName())
                .category(toCategory(categoryNewDto))
                .build();
    }

    public static BookResponseDto bookResponseDto(Book book, Category category) {
        return BookResponseDto.builder()
                .Id(book.getId())
                .nameAuthor(book.getNameAuthor())
                .bookName(book.getBookName())
                .category(CategoryMapper.categoryResponseDto(category, book))
                .build();
    }
}

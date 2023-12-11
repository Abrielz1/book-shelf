package ru.skillbox.bookshelf.mapper;

import lombok.Data;
import ru.skillbox.bookshelf.dto.BookNewDto;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.entity.Book;
import ru.skillbox.bookshelf.entity.Category;

@Data
public class BookMapper {

    public static Book toBook(BookNewDto bookNewDto, Category category) {
        return Book.builder()
                .Id(null)
                .nameAuthor(bookNewDto.getNameAuthor())
                .bookName(bookNewDto.getBookName())
                .category(category)
                .build();
    }

    public static BookResponseDto bookResponseDto(Book book) {
        return BookResponseDto.builder()
                .Id(book.getId())
                .nameAuthor(book.getNameAuthor())
                .bookName(book.getBookName())
                .category(book.getCategory().getName())
                .build();
    }
}

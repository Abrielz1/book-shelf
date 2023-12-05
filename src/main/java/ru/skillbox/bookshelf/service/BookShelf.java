package ru.skillbox.bookshelf.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.bookshelf.dto.BookResponseDto;

import java.util.List;

public interface BookShelf {

    List<BookResponseDto> findAllBooksByName(String nameCategory, PageRequest page);

    BookResponseDto findBookByNameAndAuthor(String bookName, String nameAuthor);
}

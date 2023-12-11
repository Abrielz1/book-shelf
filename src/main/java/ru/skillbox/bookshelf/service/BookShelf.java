package ru.skillbox.bookshelf.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.bookshelf.dto.BookNewDto;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.dto.CategoryNewDto;
import ru.skillbox.bookshelf.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookShelf {

    List<BookResponseDto> findAllBooksByName(String nameCategory, PageRequest page);

    BookResponseDto findBookByNameAndAuthor(String bookName, String nameAuthor);

    BookResponseDto createBook(BookNewDto bookNewDto);

    BookResponseDto updateBook(Long id, BookResponseDto bookResponseDto);

    void deleteBookById(Long id);

}

package ru.skillbox.bookshelf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.repository.BookShelfRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookShelfServiceImpl implements BookShelf {

    private final BookShelfRepository repository;

    @Override
    public List<BookResponseDto> findAllBooksByName(String nameCategory, PageRequest page) {
        return repository.getAllBooksByName(nameCategory, page);
    }

    @Override
    public BookResponseDto findBookByNameAndAuthor(String bookName, String nameAuthor) {
        return repository.getBookByAuthorAndName(bookName, nameAuthor);
    }


}

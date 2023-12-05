package ru.skillbox.bookshelf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.bookshelf.dto.BookNewDto;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.dto.CategoryNewDto;
import ru.skillbox.bookshelf.entity.Book;
import ru.skillbox.bookshelf.entity.Category;
import ru.skillbox.bookshelf.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.bookshelf.mapper.BookMapper;
import ru.skillbox.bookshelf.mapper.CategoryMapper;
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
        return repository.getBookByAuthorAndName(bookName, nameAuthor).orElseThrow(() -> {
            log.error("No such element!");
            throw new ObjectNotFoundException("No such element!");
        });
    }

    @Override
    public BookResponseDto createBook(BookNewDto bookNewDto, CategoryNewDto categoryNewDto) {

        Book book = BookMapper.toBook(bookNewDto, categoryNewDto);
        Category newCategory;

        if (!repository.checkIfExists(categoryNewDto.getName())) {

            book = BookMapper.toBook(bookNewDto, categoryNewDto);
            repository.save(book);

            return BookMapper.bookResponseDto(book, book.getCategory());

        } else {

            newCategory = getFromDB(categoryNewDto.getName());
            book.setCategory(newCategory);

            return BookMapper.bookResponseDto(book, book.getCategory());
        }
    }

    private Category getFromDB(String name) {
        return repository.getCategoryByName(name).orElseThrow(() -> {
            log.error("No such element!");
            throw new ObjectNotFoundException("No such element!");
        });
    }
}

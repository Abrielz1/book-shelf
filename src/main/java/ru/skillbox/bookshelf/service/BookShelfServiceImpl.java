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
import ru.skillbox.bookshelf.repository.BookShelfRepository;
import java.util.List;
import java.util.stream.Collectors;
import static ru.skillbox.bookshelf.mapper.BookMapper.bookResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookShelfServiceImpl implements BookShelf {

    private final BookShelfRepository repository;

    @Override
    public List<BookResponseDto> findAllBooksByName(String nameCategory, PageRequest page) {

        log.info("Sent all books!");
        return repository.getAllBooksByName(nameCategory, page) //todo: проверить
                .stream()
                .map((t) -> BookMapper.bookResponseDto(t, t.getCategory()))
                .collect(Collectors.toList());

//        List<Book> list = repository.getAllBooksByName(nameCategory, page);
//        List<BookResponseDto> responseDtos = new ArrayList<>();
//
//        for (Book i : list) {
//            responseDtos.add(BookMapper.bookResponseDto(i, i.getCategory()));
//        }
//
//        return responseDtos;
    }

    @Override
    public BookResponseDto findBookByNameAndAuthor(String bookName, String nameAuthor) {

       Book book = repository.getBookByAuthorAndName(bookName, nameAuthor).orElseThrow(() -> {
            log.error("No such element!");
            throw new ObjectNotFoundException("No such element!");
        });

        log.info("Sent book!");
        return BookMapper.bookResponseDto(book, book.getCategory());
    }

    @Override
    public BookResponseDto createBook(BookNewDto bookNewDto, CategoryNewDto categoryNewDto) {

        Book book = BookMapper.toBook(bookNewDto, categoryNewDto);
        Category newCategory;

        if (!repository.checkIfExists(categoryNewDto.getName())) {

            book = BookMapper.toBook(bookNewDto, categoryNewDto);
            repository.save(book);

            log.info("Created!");
            return bookResponseDto(book, book.getCategory());
        } else {

            newCategory = getFromDB(categoryNewDto.getName());
            book.setCategory(newCategory);
            repository.save(book);

            log.info("Created!");
            return bookResponseDto(book, book.getCategory());
        }
    }

    @Override
    public BookResponseDto updateBook(Long id, BookResponseDto bookResponseDto) {

        Category categoryFromDB = getFromDB(bookResponseDto.getCategory().getName());
        Book bookFromDB = checkBook(id);

        if (bookResponseDto.getNameAuthor() != null) {
            bookFromDB.setNameAuthor(bookResponseDto.getNameAuthor());
        }

        if (bookResponseDto.getBookName() != null) {
            bookFromDB.setBookName(bookResponseDto.getBookName());
        }

        repository.save(bookFromDB);

        log.info("updated!");
        return bookResponseDto(bookFromDB, categoryFromDB);
    }

    @Override
    public void deleteBookById(Long id) {

        Book book = checkBook(id);
        repository.delete(book);
        log.info("Deleted by id: {}", id);
    }

    private Category getFromDB(String name) {

        return repository.getCategoryByName(name).orElseThrow(() -> {
            log.error("No such element!");
            return new ObjectNotFoundException("No such element!");
        });
    }

    private Book checkBook(Long id) {

        return repository.findById(id).orElseThrow(() -> {

            log.error("No such element!");
            return new ObjectNotFoundException("No such element!");
        });
    }
}

package ru.skillbox.bookshelf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import ru.skillbox.bookshelf.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import static ru.skillbox.bookshelf.mapper.BookMapper.bookResponseDto;
import static ru.skillbox.bookshelf.mapper.BookMapper.toBook;
import static ru.skillbox.bookshelf.mapper.CategoryMapper.toCategory;

@Slf4j
@Service
@CacheConfig(cacheManager = "redisCacheManager")
@RequiredArgsConstructor
public class BookShelfServiceImpl implements BookShelf {

    private final BookShelfRepository repository;

    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable("databaseEntities")
    public List<BookResponseDto> findAllBooksByName(String name, PageRequest page) {

        log.info("Sent all books!");
        return repository.getAllBooksByName(name.toLowerCase().trim(), page)
                .stream()
                .map(BookMapper::bookResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("databaseEntitiesByName")
    public BookResponseDto findBookByNameAndAuthor(String bookName, String nameAuthor) {

        Book book = repository.getBookByAuthorAndName(bookName, nameAuthor).orElseThrow(() -> {
            log.error("No such element!");
            return new ObjectNotFoundException("No such element!");
        });

        log.info("Sent book!");
        return BookMapper.bookResponseDto(book);
    }

    @Override
    @CacheEvict(value = "databaseEntities", allEntries = true)
    public BookResponseDto createBook(BookNewDto bookNewDto) {

        String nameCategory = bookNewDto.getCategory();

        CategoryNewDto categoryNewDto = CategoryNewDto.builder()
                .name(nameCategory)
                .build();

        Category category;
        Book book;

        if (!checkCategory(nameCategory)) {
            category = toCategory(categoryNewDto);
            categoryRepository.save(category);
        } else {
            category = getFromDB(nameCategory.trim().toLowerCase());
        }

        book = toBook(bookNewDto, category);
        repository.save(book);
        categoryRepository.save(category);

        log.info("Created!");
        return bookResponseDto(book);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "databaseEntities", allEntries = true),
            @CacheEvict(value = "databaseEntityByName", allEntries = true)
    })
    public BookResponseDto updateBook(Long id, BookResponseDto bookResponseDto) {

        getFromDB(bookResponseDto.getCategory());
        Book bookFromDB = checkBook(id);

        if (bookResponseDto.getNameAuthor() != null) {
            bookFromDB.setNameAuthor(bookResponseDto.getNameAuthor());
        }

        if (bookResponseDto.getBookName() != null) {
            bookFromDB.setBookName(bookResponseDto.getBookName());
        }

        repository.save(bookFromDB);

        log.info("updated!");
        return bookResponseDto(bookFromDB);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "databaseEntities", allEntries = true),
            @CacheEvict(value = "databaseEntityByName", allEntries = true)
    })
    public void deleteBookById(Long id) {

        Book book = checkBook(id);
        repository.delete(book);
        log.info("Deleted by id: {}", id);
    }

    private Category getFromDB(String nameCategory) {

        return repository.getCategory(nameCategory).orElseThrow(() -> {
            log.error("No such element!");
            return new ObjectNotFoundException("No such element!");
        });
    }

    private Boolean checkCategory(String categoryName) {

        return repository.checkIfExists(categoryName);
    }

    private Book checkBook(Long id) {

        return repository.findById(id).orElseThrow(() -> {

            log.error("No such element!");
            return new ObjectNotFoundException("No such element!");
        });
    }
}

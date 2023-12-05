package ru.skillbox.bookshelf.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.bookshelf.dto.BookNewDto;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.service.BookShelf;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookshelf")
@RequiredArgsConstructor
public class BookShelfController {

    private final BookShelf bookShelfService;

    //TODO: найти список книг по имени категории

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponseDto> findAllBooksByNameCategory(@RequestParam() String nameCategory,
                                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                            @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);

        //return bookShelfService.findAllBooksByNameCategory(nameCategory, page);
        return null;
    }

    //TODO: найти одну книгу по её названию и автору

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto findBookByNameAndAuthor(@RequestParam() String bookName,
                                                   @RequestParam() String nameAuthor) {

        //return bookShelfService.findBookByNameAndAuthor(bookName, nameAuthor);
        return null;
    }

    //TODO: создать книгу

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@RequestBody BookNewDto bookNewDto) {

        //return bookShelfService.createBook(bookNewDto);
        return null;
    }

    //TODO: обновить информацию о книге

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto updateBookById(@PathVariable(name = "id") Long id,
                                      @RequestBody BookResponseDto bookResponseDto) {

        //return bookShelfService.createBookById(id, bookResponseDto);
        return null;
    }


    //TODO: удалить книгу по ID

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable(name = "id") Long id) {
        //bookShelfService.deleteBookById(id);
    }
}

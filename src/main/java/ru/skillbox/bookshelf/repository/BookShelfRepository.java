package ru.skillbox.bookshelf.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.bookshelf.dto.BookResponseDto;
import ru.skillbox.bookshelf.entity.Book;
import java.util.List;

@Repository
public interface BookShelfRepository extends JpaRepository<Book, Long> {

    @Query(value = """
           select * from public.books where books.category like concat('%', :nameCategory , '%')
           """, nativeQuery = true)
    List<BookResponseDto> getAllBooksByName(@Param("nameCategory ")String nameCategory, PageRequest page);

    @Query(value = """
           select * from public.books where books.title like concat('%', :bookName , '%') and
           books.author like concat('%', :nameAuthor , '%')
           """, nativeQuery = true)
    BookResponseDto getBookByAuthorAndName(@Param("bookName")String bookName, @Param("nameAuthor")String nameAuthor);
}

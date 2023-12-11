package ru.skillbox.bookshelf.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.bookshelf.entity.Book;
import ru.skillbox.bookshelf.entity.Category;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookShelfRepository extends JpaRepository<Book, Long> {

    @Query(value = """
           select * from public.books as b
            join categories as c on b.id = c.book_id where c.name like concat('%', :nameCategory, '%')
           """, nativeQuery = true)
    List<Book> getAllBooksByName(@Param("nameCategory")String nameCategory, PageRequest page);

    @Query(value = """
           select * from public.books where books.name like concat('%', :bookName, '%') and
           books.author like concat('%', :nameAuthor, '%')
           """, nativeQuery = true)
    Optional<Book> getBookByAuthorAndName(@Param("bookName")String bookName, @Param("nameAuthor")String nameAuthor);

    @Query(value = """
           select case when count(c)>0 then true else false end from categories as c where c.name like concat('%', :nameCategory, '%')
           """, nativeQuery = true)
    Boolean checkIfExists(@Param("nameCategory")String nameCategory);

    @Query(value = """
           select * from categories as c where c.name like concat('%', :nameCategory, '%')
                   """, nativeQuery = true)
    Optional<Category> getCategoryByName(@Param("nameCategory")String nameCategory);
}

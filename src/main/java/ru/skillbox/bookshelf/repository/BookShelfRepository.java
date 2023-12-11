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

    @Query("""
           from Book as b join Category as c on b.category.Id = c.Id where c.name like :name
           """)
    List<Book> getAllBooksByName(@Param("name")String name, PageRequest page);

    @Query(value = """
           select * from public.books where LOWER(books.name) like LOWER(concat('%', :bookName, '%')) and
           LOWER(books.author) like LOWER(concat('%', :nameAuthor, '%'))
           """, nativeQuery = true)
    Optional<Book> getBookByAuthorAndName(@Param("bookName")String bookName, @Param("nameAuthor")String nameAuthor);

    @Query(value = """
           select case when count(c)>0 then true else false end from categories as c where c.name like concat('%', :name, '%')
           """, nativeQuery = true)
    Boolean checkIfExists(@Param("name")String name);

    @Query("""
           from Category  as c
           where c.name like :name
           """)
    Optional<Category> getCategory(@Param("name")String name);
}

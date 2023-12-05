package ru.skillbox.bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.bookshelf.entity.Book;

@Repository
public interface BookShelfRepository extends JpaRepository<Book, Long> {
}

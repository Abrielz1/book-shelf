package ru.skillbox.bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.bookshelf.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

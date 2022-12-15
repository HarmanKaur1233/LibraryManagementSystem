package com.gem.librarymanagement.repository;

import com.gem.librarymanagement.entity.Author;
import com.gem.librarymanagement.entity.Book;
import com.gem.librarymanagement.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Book,Integer> {
    Book findByAuthorId(Author author);

    Book findByPublisherId(Publisher publisher);
}

package com.gem.librarymanagement.repository;

import com.gem.librarymanagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Integer> {
    Author findByAuthorName(String authorName);
}

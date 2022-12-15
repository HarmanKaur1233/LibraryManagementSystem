package com.gem.librarymanagement.repository;

import com.gem.librarymanagement.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher,Integer> {
    Publisher findByPublisherName(String publisherName);
}

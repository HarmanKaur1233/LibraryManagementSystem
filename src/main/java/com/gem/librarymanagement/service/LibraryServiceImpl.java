package com.gem.librarymanagement.service;

import com.gem.librarymanagement.entity.Author;
import com.gem.librarymanagement.entity.Book;
import com.gem.librarymanagement.entity.Publisher;
import com.gem.librarymanagement.exceptions.ConfigDataResourceNotFoundException;
import com.gem.librarymanagement.payloads.BookDto;
import com.gem.librarymanagement.repository.AuthorRepo;
import com.gem.librarymanagement.repository.LibraryRepository;
import com.gem.librarymanagement.repository.PublisherRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    PublisherRepo publisherRepo;


    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = new Book();
        Author author = null;
//      Check In Db duplicate author doesn't exist.
        author = authorRepo.findByAuthorName(bookDto.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setAuthorName(bookDto.getAuthorName());
        }
        Publisher publisher = null;
        //Check In Db duplicate publisher doesn't exist.
        publisher = publisherRepo.findByPublisherName(bookDto.getPublisherName());
        if (publisher == null) {
            publisher = new Publisher();
            publisher.setPublisherName(bookDto.getPublisherName());
        }
        book.setBookName(bookDto.getBookName());
        book.setPublishDate(bookDto.getPublishDate());
        book.setAuthorId(author);
        book.setPublisherId(publisher);
        book = libraryRepository.save(book);
        log.info("Books stored in the library");
        return bookDto;
    }

    @Override
    public BookDto getBookById(Integer bookId) {
        Book book = this.libraryRepository.findById(bookId).orElseThrow(() -> new ConfigDataResourceNotFoundException("Book", "Id", String.valueOf(bookId)));
        BookDto bookDto = new BookDto();
        bookDto.setBookName(book.getBookName());
        bookDto.setAuthorName(book.getAuthorId().getAuthorName());
        bookDto.setPublisherName(book.getPublisherId().getPublisherName());
        bookDto.setPublishDate(book.getPublishDate());
        log.info("Book returned from the library through bookId");
        return bookDto;
    }

    @Override
    public List<BookDto> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Book> book = this.libraryRepository.findAll(pageable);
        List<Book> allPagedBooks = book.getContent();

        List<BookDto> bookList = allPagedBooks.stream().map(book1 -> {
            BookDto bookDto = new BookDto();
            bookDto.setBookName(book1.getBookName());
            bookDto.setPublishDate(book1.getPublishDate());
            bookDto.setPublisherName(book1.getPublisherId().getPublisherName());
            bookDto.setAuthorName(book1.getAuthorId().getAuthorName());
            return bookDto;
        }).collect(Collectors.toList());
        log.info("All books returned");
        return bookList;
    }

    @Override
    public String deleteBook(Integer bookId) {

        this.libraryRepository.deleteById(bookId);
        log.info("Delete the book by given bookId");
        return "Successfully deleted the book";
    }

    @Override
    public BookDto getBookByAuthorName(String bookName) {
        Author author = authorRepo.findByAuthorName(bookName);
        BookDto bookDto = new BookDto();
        if (author != null) {
            Book book = this.libraryRepository.findByAuthorId(author);
            bookDto.setAuthorName(book.getAuthorId().getAuthorName());
            bookDto.setPublisherName(book.getPublisherId().getPublisherName());
            bookDto.setBookName(book.getBookName());
            bookDto.setPublishDate(book.getPublishDate());
        }
        return bookDto;
    }

    @Override
    public BookDto getBookByPublisherName(String publisherName) {
        Publisher publisher = publisherRepo.findByPublisherName(publisherName);
        BookDto bookDto = new BookDto();
        if (publisherName != null) {
            Book book = this.libraryRepository.findByPublisherId(publisher);
            bookDto.setPublishDate(book.getPublishDate());
            bookDto.setPublisherName(book.getPublisherId().getPublisherName());
            bookDto.setBookName(book.getBookName());
            bookDto.setAuthorName(book.getAuthorId().getAuthorName());
        }
        return bookDto;
    }

}

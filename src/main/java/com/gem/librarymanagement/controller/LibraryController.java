package com.gem.librarymanagement.controller;

import com.gem.librarymanagement.payloads.ApiResponse;
import com.gem.librarymanagement.payloads.BookDto;
import com.gem.librarymanagement.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@Slf4j
public class LibraryController {
    @Autowired
    LibraryService libraryService;

    @PostMapping("/addBook")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        log.info("Add book in library catalogue");
        BookDto addBook = this.libraryService.addBook(bookDto);
        return new ResponseEntity<>(addBook, HttpStatus.CREATED);
    }

    @GetMapping("/getBookById/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("bookId") Integer bookId) {
        log.info("Get book by id");
        BookDto bookDto = this.libraryService.getBookById(bookId);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping("/getBookByName/{bookName}")
    public ResponseEntity<BookDto> getBookByAuthorName(@PathVariable("bookName") String bookName) {
        log.info("Get book by name");
        BookDto bookDto = this.libraryService.getBookByAuthorName(bookName);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping("/getBookByPublisher/{publisherName}")
    public ResponseEntity<BookDto> getBookByPublisherName(@PathVariable("publisherName") String publisherName) {
        log.info("Get book by publisher name");
        BookDto bookDto = this.libraryService.getBookByPublisherName(publisherName);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "bookName", required = false) String sortBooks
    ) {
        log.info("Get all books from library");
        List<BookDto> bookDtos = this.libraryService.getAllBooks(pageNumber, pageSize, sortBooks);
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable("bookId") Integer bookId) {
        log.info("Delete book");
        String message = this.libraryService.deleteBook(bookId);
        return ResponseEntity.ok(new ApiResponse(message, true));
    }
}

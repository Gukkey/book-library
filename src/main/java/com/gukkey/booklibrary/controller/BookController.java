package com.gukkey.booklibrary.controller;

import com.gukkey.booklibrary.model.dto.BookDTO;
import com.gukkey.booklibrary.model.res.AuthorResponse;
import com.gukkey.booklibrary.model.res.BookResponse;
import com.gukkey.booklibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBook(@PathVariable UUID id) {
    return bookService.getBook(id);
  }

  @PostMapping("/create")
  public ResponseEntity<BookResponse> createBook(@RequestBody BookDTO bookDTO) {
    return bookService.createBook(bookDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<BookResponse> deleteBook(@PathVariable UUID id) {
    return bookService.deleteBook(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBook(
      @PathVariable UUID id, @RequestBody BookDTO bookDTO) {
    return bookService.updateBook(id, bookDTO);
  }
}

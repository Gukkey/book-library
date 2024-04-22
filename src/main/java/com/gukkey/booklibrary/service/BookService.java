package com.gukkey.booklibrary.service;

import com.gukkey.booklibrary.model.dto.BookDTO;
import com.gukkey.booklibrary.model.res.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BookService {
  ResponseEntity<BookResponse> createBook(BookDTO bookDTO);

  ResponseEntity<BookResponse> deleteBook(UUID id);

  ResponseEntity<BookResponse> updateBook(UUID id, BookDTO bookDTO);

  ResponseEntity<BookResponse> getBook(UUID id);
}

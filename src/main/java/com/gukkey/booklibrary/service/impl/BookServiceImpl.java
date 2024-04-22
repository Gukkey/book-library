package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.domain.Book;
import com.gukkey.booklibrary.mapper.BookMapper;
import com.gukkey.booklibrary.model.dto.BookDTO;
import com.gukkey.booklibrary.model.res.BookResponse;
import com.gukkey.booklibrary.repository.AuthorRepository;
import com.gukkey.booklibrary.repository.BookRepository;
import com.gukkey.booklibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

  private final BookMapper bookMapper;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  private static final String BADREQUEST = "Bad request";

  @Autowired
  BookServiceImpl(
      BookMapper bookMapper, BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookMapper = bookMapper;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public ResponseEntity<BookResponse> createBook(BookDTO bookDTO) {
    Book book = bookMapper.toBook(bookDTO);
    BookResponse response;
    try {
      bookRepository.save(book);
      response =
          BookResponse.builder()
              .status(HttpStatus.CREATED.value())
              .response("Book has been created")
              .id(book.getId())
              .authorId(bookDTO.getAuthorId())
              .title(book.getTitle())
              .isbn(book.getIsbn())
              .year(book.getYear())
              .build();
    } catch (Exception e) {
      response =
          BookResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BADREQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @Override
  public ResponseEntity<BookResponse> deleteBook(UUID id) {
    BookResponse response;
    try {
      bookRepository.deleteById(id);
      response =
          BookResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Book has been deleted")
              .build();
    } catch (Exception e) {
      response =
          BookResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BADREQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @Override
  public ResponseEntity<BookResponse> updateBook(UUID id, BookDTO bookDTO) {

    BookResponse response;
    try {
      Optional<Book> optional = bookRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException("Book not found");
      }
      if (!optional.get().getTitle().equals(bookDTO.getTitle())) {
        optional.get().setTitle(bookDTO.getTitle());
      }
      if (!optional.get().getYear().equals(bookDTO.getYear())) {
        optional.get().setYear(bookDTO.getYear());
      }
      if (!optional.get().getIsbn().equals(bookDTO.getIsbn())) {
        optional.get().setIsbn(bookDTO.getIsbn());
      }
      if (!optional.get().getYear().equals(bookDTO.getYear())) {
        optional.get().setYear(bookDTO.getYear());
      }
      Optional<Author> author = authorRepository.findById(bookDTO.getAuthorId());
      if (author.isEmpty()) {
        throw new IllegalArgumentException("Author not found");
      }
      if (optional.get().getAuthor().getId() != bookDTO.getAuthorId()) {
        optional.get().setAuthor(author.get());
      }
      bookRepository.save(optional.get());
      response =
          BookResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Book has been updated")
              .id(optional.get().getId())
              .title(optional.get().getTitle())
              .authorId(optional.get().getAuthor().getId())
              .isbn(optional.get().getIsbn())
              .year(optional.get().getYear())
              .build();
    } catch (Exception e) {
      response =
          BookResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BADREQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @Override
  public ResponseEntity<BookResponse> getBook(UUID id) {
    BookResponse response;
    try {
      Optional<Book> optional = bookRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException("Book not found");
      }
      response =
          BookResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Book found")
              .id(optional.get().getId())
              .authorId(optional.get().getAuthor().getId())
              .title(optional.get().getTitle())
              .isbn(optional.get().getIsbn())
              .year(optional.get().getYear())
              .build();
    } catch (Exception e) {
      response =
          BookResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BADREQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}

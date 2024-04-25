package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.domain.Book;
import com.gukkey.booklibrary.mapper.BookMapper;
import com.gukkey.booklibrary.model.dto.BookDTO;
import com.gukkey.booklibrary.model.res.BookResponse;
import com.gukkey.booklibrary.repository.AuthorRepository;
import com.gukkey.booklibrary.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock BookRepository bookRepository;
  @Mock AuthorRepository authorRepository;
  @Mock BookMapper bookMapper;

  @InjectMocks BookServiceImpl bookService;

  Author testAuthor1 =
      Author.builder()
          .id(UUID.randomUUID())
          .name("Chitra Banerjee Divakaruni")
          .biography(null)
          .build();

  Book testBook1 =
      Book.builder()
          .id(UUID.randomUUID())
          .title("The palace of illusions")
          .year(Year.of(2008))
          .isbn("OL16515951M")
          .author(testAuthor1)
          .build();

  UUID testBook1Id = testBook1.getId();

  @Test
  void bookShouldBeCreatedIfDTOIsValid() {
    // Arrange
    BookDTO bookDTO =
        BookDTO.builder()
            .title("The palace of illusions")
            .isbn("OL16515951M")
            .year(Year.of(2008))
            .authorId(testAuthor1.getId())
            .build();
    when(bookMapper.toBook(bookDTO)).thenReturn(testBook1);
    when(bookRepository.save(any(Book.class))).thenReturn(testBook1);

    // Act
    ResponseEntity<BookResponse> response = bookService.createBook(bookDTO);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void createBookShouldThrow404IfDTOIsInvalid() {
    // Arrange
    BookDTO bookDTO =
        BookDTO.builder()
            .title("The palace of illusions")
            .isbn("OL16515951M")
            .year(Year.of(2008))
            .authorId(null)
            .build();

    // Act
    ResponseEntity<BookResponse> response = bookService.createBook(bookDTO);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void deleteBook() {
    // Arrange
    doNothing().when(bookRepository).deleteById(testBook1Id);

    // Act
    ResponseEntity<BookResponse> response = bookService.deleteBook(testBook1Id);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void updateBook() {}

  @Test
  void getBook() {}
}

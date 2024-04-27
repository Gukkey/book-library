package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.mapper.AuthorMapper;
import com.gukkey.booklibrary.model.dto.AuthorDTO;
import com.gukkey.booklibrary.model.res.AuthorResponse;
import com.gukkey.booklibrary.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

  @Mock AuthorRepository authorRepository;
  @Mock AuthorMapper authorMapper;
  @InjectMocks AuthorServiceImpl authorService;

  @Test
  void createAuthorIfDTOIsValid() {
    // Arrange
    AuthorDTO authorDTO = AuthorDTO.builder().name("test").biography("test").build();
    Author author = Author.builder().name("test").biography("test").build();
    when(authorMapper.toAuthor(authorDTO)).thenReturn(author);
    when(authorRepository.save(author)).thenReturn(author);

    // Act
    ResponseEntity<AuthorResponse> authorResponse = authorService.createAuthor(authorDTO);

    // Assert
    assertNotNull(authorResponse);
    assertEquals(HttpStatus.CREATED, authorResponse.getStatusCode());
  }

  @Test
  void deleteAuthorIfAuthorExists() {
    // Arrange
    Author author = Author.builder().name("test").biography("test").build();
    when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
    doNothing().when(authorRepository).deleteById(author.getId());

    // Act
    ResponseEntity<AuthorResponse> authorResponse = authorService.deleteAuthor(author.getId());

    // Assert
    assertNotNull(authorResponse);
    assertEquals(HttpStatus.OK, authorResponse.getStatusCode());
  }

  @Test
  void deleteAuthorShouldthrowBadRequestIfAuthorNotExists() {
    // Arrange
    when(authorRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    // Act
    ResponseEntity<AuthorResponse> authorResponse = authorService.deleteAuthor(UUID.randomUUID());

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, authorResponse.getStatusCode());
  }
}

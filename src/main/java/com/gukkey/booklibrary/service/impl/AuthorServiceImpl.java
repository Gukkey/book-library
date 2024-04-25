package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.mapper.AuthorMapper;
import com.gukkey.booklibrary.model.dto.AuthorDTO;
import com.gukkey.booklibrary.model.res.AuthorResponse;
import com.gukkey.booklibrary.repository.AuthorRepository;
import com.gukkey.booklibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final AuthorMapper authorMapper;
  private final AuthorRepository authorRepository;

  private static final String BAD_REQUEST = "Bad request";
  private static final String AUTHOR_NOT_FOUND = "Author not found";

  @Autowired
  public AuthorServiceImpl(AuthorMapper authorMapper, AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  /**
   * Creates an author based on the provided author DTO.
   *
   * @param  authorDTO the author DTO containing the author details
   * @return          the response entity containing the author response
   */

  public ResponseEntity<AuthorResponse> createAuthor(AuthorDTO authorDTO) {
    AuthorResponse response;
    try {
      Author author = authorMapper.toAuthor(authorDTO);
      authorRepository.save(author);
      response =
          AuthorResponse.builder()
              .status(HttpStatus.CREATED.value())
              .response("Author has been created")
              .id(author.getId())
              .name(author.getName())
              .biography(author.getBiography())
              .build();
    } catch (Exception e) {
      response =
          AuthorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BAD_REQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Deletes an author by their ID.
   *
   * @param  id  the ID of the author to delete
   * @return     a ResponseEntity containing the result of the deletion operation
   */

  @Override
  public ResponseEntity<AuthorResponse> deleteAuthor(UUID id) {
    AuthorResponse response;
    try {
      Optional<Author> optional = authorRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException(AUTHOR_NOT_FOUND);
      }
      authorRepository.deleteById(id);
      response =
          AuthorResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Author has been deleted")
              .build();
    } catch (Exception e) {
      response =
          AuthorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BAD_REQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Updates an author with the given ID using the provided AuthorDTO.
   *
   * @param  id       the ID of the author to be updated
   * @param  authorDTO the AuthorDTO containing the updated author information
   * @return          a ResponseEntity containing the updated AuthorResponse
   */

  @Override
  public ResponseEntity<AuthorResponse> updateAuthor(UUID id, AuthorDTO authorDTO) {
    AuthorResponse response;
    try {
      Optional<Author> optional = authorRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException(AUTHOR_NOT_FOUND);
      }
      if (!optional.get().getName().equals(authorDTO.getName())) {
        optional.get().setName(authorDTO.getName());
      }
      if (!optional.get().getBiography().equals(authorDTO.getBiography())) {
        optional.get().setBiography(authorDTO.getBiography());
      }
      authorRepository.save(optional.get());
      response =
          AuthorResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Author has been updated")
              .id(optional.get().getId())
              .name(optional.get().getName())
              .biography(optional.get().getBiography())
              .build();
    } catch (Exception e) {
      response =
          AuthorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Input is invalid")
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Retrieves an author by their unique identifier.
   *
   * @param  id  the unique identifier of the author
   * @return     a ResponseEntity containing the AuthorResponse with the author details, or a
   *             ResponseEntity with a BAD_REQUEST status and a "Bad Request" message if the author is not
   *             found or an error occurs
   */

  @Override
  public ResponseEntity<AuthorResponse> getAuthor(UUID id) {
    AuthorResponse response;
    try {
      Optional<Author> optional = authorRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException(AUTHOR_NOT_FOUND);
      }
      response =
          AuthorResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Author has been found")
              .id(optional.get().getId())
              .name(optional.get().getName())
              .biography(optional.get().getBiography())
              .build();
    } catch (Exception e) {
      response =
          AuthorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response(BAD_REQUEST)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}

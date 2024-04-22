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

  @Autowired
  public AuthorServiceImpl(AuthorMapper authorMapper, AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  @Override
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

  @Override
  public ResponseEntity<AuthorResponse> deleteAuthor(UUID id) {
    AuthorResponse response;
    try {
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

  @Override
  public ResponseEntity<AuthorResponse> updateAuthor(UUID id, AuthorDTO authorDTO) {
    AuthorResponse response;
    try {
      Optional<Author> optional = authorRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException("Author not found");
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

  @Override
  public ResponseEntity<AuthorResponse> getAuthor(UUID id) {
    AuthorResponse response;
    try {
      Optional<Author> optional = authorRepository.findById(id);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException("Author not found");
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

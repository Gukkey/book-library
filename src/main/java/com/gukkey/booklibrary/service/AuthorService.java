package com.gukkey.booklibrary.service;

import com.gukkey.booklibrary.model.dto.AuthorDTO;
import com.gukkey.booklibrary.model.res.AuthorResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthorService {

  ResponseEntity<AuthorResponse> createAuthor(AuthorDTO authorDTO);

  ResponseEntity<AuthorResponse> deleteAuthor(UUID id);

  ResponseEntity<AuthorResponse> updateAuthor(UUID id, AuthorDTO authorDTO);

  ResponseEntity<AuthorResponse> getAuthor(UUID id);
}

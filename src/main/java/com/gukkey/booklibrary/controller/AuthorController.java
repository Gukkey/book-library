package com.gukkey.booklibrary.controller;

import com.gukkey.booklibrary.model.dto.AuthorDTO;
import com.gukkey.booklibrary.model.res.AuthorResponse;
import com.gukkey.booklibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/author", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {
  private final AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorResponse> getAuthor(@PathVariable UUID id) {
    return authorService.getAuthor(id);
  }

  @PostMapping("/create")
  public ResponseEntity<AuthorResponse> postAuthor(@RequestBody AuthorDTO authorDTO) {
    return authorService.createAuthor(authorDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AuthorResponse> deleteAuthor(@PathVariable UUID id) {
    return authorService.deleteAuthor(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AuthorResponse> putAuthor(
      @PathVariable UUID id, @RequestBody AuthorDTO authorDTO) {
    return authorService.updateAuthor(id, authorDTO);
  }
}

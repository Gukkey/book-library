package com.gukkey.booklibrary.mapper;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.model.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

  public Author toAuthor(AuthorDTO authorDTO) {
    return Author.builder().name(authorDTO.getName()).biography(authorDTO.getBiography()).build();
  }
}

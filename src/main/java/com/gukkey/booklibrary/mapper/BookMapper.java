package com.gukkey.booklibrary.mapper;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.domain.Book;
import com.gukkey.booklibrary.model.dto.BookDTO;
import com.gukkey.booklibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookMapper {

  private AuthorRepository authorRepository;

  @Autowired
  BookMapper bookMapper(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
    return this;
  }

  public Book toBook(BookDTO bookDTO) {
    Optional<Author> author = authorRepository.findById(bookDTO.getAuthorId());
    if (author.isEmpty()) {
      throw new IllegalArgumentException("Input is invalid");
    }
    return Book.builder()
        .title(bookDTO.getTitle())
        .author(author.get())
        .isbn(bookDTO.getIsbn())
        .year(bookDTO.getYear())
        .build();
  }
}

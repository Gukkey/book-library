package com.gukkey.booklibrary.mapper;

import com.gukkey.booklibrary.domain.Author;
import com.gukkey.booklibrary.domain.Book;
import com.gukkey.booklibrary.domain.Rental;
import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.repository.AuthorRepository;
import com.gukkey.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class RentalMapper {
  private final BookRepository bookRepository;

  @Autowired
  public RentalMapper(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Rental toRental(RentalDTO rentalDTO) {
    Optional<Book> book = bookRepository.findById(rentalDTO.getBookId());
    if (book.isEmpty()) {
      throw new IllegalArgumentException("Book not found in this id");
    }
    LocalDate returnDate = rentalDTO.getRentalDate().plusDays(14);
    return Rental.builder()
        .book(book.get())
        .renterName(rentalDTO.getRenterName())
        .rentalDate(rentalDTO.getRentalDate())
        .returnDate(returnDate)
        .build();
  }
}

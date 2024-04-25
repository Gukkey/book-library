package com.gukkey.booklibrary.mapper;

import com.gukkey.booklibrary.domain.Book;
import com.gukkey.booklibrary.domain.Rental;
import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class RentalMapper {
  private final BookRepository bookRepository;

  @Autowired
  public RentalMapper(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * Converts a RentalDTO object to a Rental object.
   *
   * @param rentalDTO the RentalDTO object to be converted
   * @return the converted Rental object
   * @throws IllegalArgumentException if the book with the given bookId is not found
   */
  public Rental toRental(RentalDTO rentalDTO) {
    Optional<Book> book = bookRepository.findById(rentalDTO.getBookId());
    if (book.isEmpty()) {
      throw new IllegalArgumentException("Book not found in this id");
    }
    return Rental.builder()
        .bookId(rentalDTO.getBookId())
        .renterName(rentalDTO.getRenterName())
        .rentalDate(LocalDate.now())
        .returnDate(LocalDate.now().plusDays(14))
        .build();
  }
}

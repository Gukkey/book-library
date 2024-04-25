package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Rental;
import com.gukkey.booklibrary.mapper.RentalMapper;
import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.model.res.RentalListResponse;
import com.gukkey.booklibrary.model.res.RentalResponse;
import com.gukkey.booklibrary.repository.BookRepository;
import com.gukkey.booklibrary.repository.RentalRepository;
import com.gukkey.booklibrary.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

  private final RentalRepository rentalRepository;
  private final RentalMapper rentalMapper;
  private final BookRepository bookRepository;

  @Autowired
  public RentalServiceImpl(
      RentalRepository rentalRepository, RentalMapper rentalMapper, BookRepository bookRepository) {
    this.rentalMapper = rentalMapper;
    this.rentalRepository = rentalRepository;
    this.bookRepository = bookRepository;
  }

  /**
   * Creates a rental based on the provided rental details.
   *
   * @param rentalDTO the rental details
   * @return the response entity containing the rental response
   */
  @Override
  public ResponseEntity<RentalResponse> createRental(RentalDTO rentalDTO) {
    RentalResponse response;
    try {
      if (!checkForRental(rentalDTO.getBookId())) {
        throw new IllegalArgumentException("Book is not available");
      }
      Rental rental = rentalMapper.toRental(rentalDTO);
      rentalRepository.save(rental);
      response =
          RentalResponse.builder()
              .status(HttpStatus.CREATED.value())
              .response("Rental created successfully")
              .bookId(rental.getBookId())
              .renterName(rental.getRenterName())
              .rentalDate(rental.getRentalDate())
              .returnDate(rental.getReturnDate())
              .build();
    } catch (Exception e) {
      response =
          RentalResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Bad Request" + " " + e)
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Retrieves a rental by its book ID.
   *
   * @param bookId the ID of the book to retrieve the rental for
   * @return a ResponseEntity containing the RentalResponse with the rental details, or a
   *     ResponseEntity with a BAD_REQUEST status and a "Bad Request" message if the rental is not
   *     found or an error occurs
   */
  @Override
  public ResponseEntity<RentalResponse> retrieveRental(UUID bookId) {
    RentalResponse rentalResponse;
    try {
      Optional<Rental> optional = rentalRepository.findById(bookId);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException("Rental not found");
      }
      optional.get().setRenterName(null);
      optional.get().setRentalDate(null);
      optional.get().setReturnDate(null);
      rentalRepository.save(optional.get());
      rentalResponse =
          RentalResponse.builder()
              .status(HttpStatus.OK.value())
              .response("Rental found")
              .bookId(optional.get().getBook().getId())
              .renterName(optional.get().getRenterName())
              .rentalDate(optional.get().getRentalDate())
              .returnDate(optional.get().getReturnDate())
              .build();
    } catch (Exception e) {
      rentalResponse =
          RentalResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Bad Request: " + " " + e.getMessage())
              .build();
    }
    return ResponseEntity.status(rentalResponse.getStatus()).body(rentalResponse);
  }

  /**
   * Retrieves a list of overdue rentals from the rental repository and returns it as a response
   * entity.
   *
   * @return a response entity containing the list of overdue rentals and the corresponding status
   *     code
   */
  @Override
  public ResponseEntity<RentalListResponse> checkOverdueRentals() {
    RentalListResponse response;
    try {
      LocalDate currentDate = LocalDate.now();
      List<Rental> overdueRentals =
          rentalRepository.findAll().stream()
              .filter(rental -> rental.getRentalDate().isAfter(currentDate))
              .toList();
      response =
          RentalListResponse.builder()
              .status(HttpStatus.FOUND.value())
              .rentals(overdueRentals)
              .build();
    } catch (Exception e) {
      response =
          RentalListResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Bad request")
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Returns a rental by its book ID.
   *
   * @param  bookId  the ID of the book to be returned
   * @return         a ResponseEntity containing the RentalResponse with the rental details and the corresponding status code
   */

  @Override
  public ResponseEntity<RentalResponse> returnRental(UUID bookId) {
    RentalResponse response;
    try {
      if (!checkForRental(bookId)) {
        rentalRepository.deleteById(bookId);
        response =
            RentalResponse.builder()
                .status(HttpStatus.OK.value())
                .response("Book returned successfully")
                .build();
      } else {
        response =
            RentalResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .response("Book is already returned or available for rental")
                .build();
      }
      ResponseEntity.status(response.getStatus()).body(response);
    } catch (Exception e) {
      response =
          RentalResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Bad request")
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Checks if a book with the given ID is available for rental.
   *
   * @param bookId the ID of the book to check for rental
   * @return true if the book is available for rental, false otherwise
   */
  boolean checkForRental(UUID bookId) {
    if (bookRepository.findById(bookId).isEmpty()) {
      throw new IllegalArgumentException("Book does not exist");
    }
    return rentalRepository.findById(bookId).isEmpty();
  }
}

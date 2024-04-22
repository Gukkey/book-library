package com.gukkey.booklibrary.service.impl;

import com.gukkey.booklibrary.domain.Rental;
import com.gukkey.booklibrary.mapper.RentalMapper;
import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.model.res.RentalResponse;
import com.gukkey.booklibrary.repository.RentalRepository;
import com.gukkey.booklibrary.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

  private final RentalRepository rentalRepository;
  private final RentalMapper rentalMapper;

  @Autowired
  public RentalServiceImpl(RentalRepository rentalRepository, RentalMapper rentalMapper) {
    this.rentalMapper = rentalMapper;
    this.rentalRepository = rentalRepository;
  }

  @Override
  public ResponseEntity<RentalResponse> createRental(RentalDTO rentalDTO) {
    RentalResponse response;
    try {
      Rental rental = rentalMapper.toRental(rentalDTO);
      if (checkForRental(rentalDTO.getBookId())) {
        throw new IllegalArgumentException("Book is not available");
      }
      rentalRepository.save(rental);
      response =
          RentalResponse.builder()
              .status(HttpStatus.CREATED.value())
              .response("Rental created successfully")
              .bookId(rental.getBook().getId())
              .renterName(rental.getRenterName())
              .rentalDate(rental.getRentalDate())
              .returnDate(rental.getReturnDate())
              .build();
    } catch (Exception e) {
      response =
          RentalResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .response("Bad Request")
              .build();
    }
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @Override
  public ResponseEntity<RentalResponse> retriveRental(UUID bookId) {
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
              .response("Bad Request")
              .build();
    }
    return ResponseEntity.status(rentalResponse.getStatus()).body(rentalResponse);
  }

  boolean checkForRental(UUID bookId) {
    if (bookRepository.findById(bookId).isEmpty()) {
      throw new IllegalArgumentException("Book does not exist");
    }
    return rentalRepository.findById(bookId).isEmpty();
  }
}

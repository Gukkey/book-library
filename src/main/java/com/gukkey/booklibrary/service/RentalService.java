package com.gukkey.booklibrary.service;

import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.model.res.RentalListResponse;
import com.gukkey.booklibrary.model.res.RentalResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface RentalService {
  ResponseEntity<RentalResponse> createRental(RentalDTO rentalDTO);

  ResponseEntity<RentalResponse> retrieveRental(UUID bookId);

  ResponseEntity<RentalListResponse> checkOverdueRentals();

  ResponseEntity<RentalResponse> returnRental(UUID bookId);
}

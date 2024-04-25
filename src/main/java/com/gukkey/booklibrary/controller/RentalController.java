package com.gukkey.booklibrary.controller;

import com.gukkey.booklibrary.model.dto.RentalDTO;
import com.gukkey.booklibrary.model.res.RentalResponse;
import com.gukkey.booklibrary.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/rental", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentalController {
  private final RentalService rentalService;

  @Autowired
  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @PostMapping("create")
  public ResponseEntity<RentalResponse> createRental(@RequestBody RentalDTO rentalDTO) {
    return rentalService.createRental(rentalDTO);
  }

  @GetMapping("/retrieve/{id}")
  public ResponseEntity<RentalResponse> retrieveRental(@PathVariable UUID id) {
    return rentalService.retrieveRental(id);
  }

  @DeleteMapping("/return/{id}")
  public ResponseEntity<RentalResponse> returnRental(@PathVariable UUID id) {
    return rentalService.returnRental(id);
  }
}

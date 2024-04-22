package com.gukkey.booklibrary.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class RentalResponse {
  int status;
  String response;
  UUID bookId;
  String renterName;
  LocalDate rentalDate;
  LocalDate returnDate;
}

package com.gukkey.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class RentalDTO {
  private UUID bookId;
  private String renterName;
}

package com.gukkey.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BookDTO {
  String title;
  UUID authorId;
  String isbn;
  Year year;
}

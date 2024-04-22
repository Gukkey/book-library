package com.gukkey.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorDTO {
  String name;
  String biography;
}

package com.gukkey.booklibrary.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class AuthorResponse {
  int status;
  String response;
  UUID id;
  String name;
  String biography;
}

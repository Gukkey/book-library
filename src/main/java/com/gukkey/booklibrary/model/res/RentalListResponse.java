package com.gukkey.booklibrary.model.res;

import com.gukkey.booklibrary.domain.Rental;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RentalListResponse {
    int status;
    String response;
    List<Rental> rentals;
}

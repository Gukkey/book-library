package com.gukkey.booklibrary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rental")
public class Rental {

  @Id
  @Column(name = "book_id")
  private UUID bookId;

  @OneToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;

  @Column(name = "renter_name")
  private String renterName;

  @Column(name = "rental_date")
  private LocalDate rentalDate;

  @Column(name = "return_date")
  private LocalDate returnDate;
}

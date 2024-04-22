package com.gukkey.booklibrary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;

  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @Column(name = "publication_year", nullable = false)
  private Year year;

  @OneToOne(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Rental rental;
}

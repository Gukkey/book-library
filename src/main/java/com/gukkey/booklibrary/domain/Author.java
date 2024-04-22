package com.gukkey.booklibrary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "biography")
  private String biography;

  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Book> books;
}

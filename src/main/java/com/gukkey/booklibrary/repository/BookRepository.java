package com.gukkey.booklibrary.repository;

import com.gukkey.booklibrary.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {}

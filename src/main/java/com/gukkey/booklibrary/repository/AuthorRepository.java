package com.gukkey.booklibrary.repository;

import com.gukkey.booklibrary.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {}

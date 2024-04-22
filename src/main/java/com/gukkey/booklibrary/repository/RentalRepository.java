package com.gukkey.booklibrary.repository;

import com.gukkey.booklibrary.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {}

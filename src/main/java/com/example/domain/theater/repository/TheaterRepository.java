package com.example.domain.theater.repository;

import com.example.domain.theater.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Optional<Theater> findByNameAndLocation(@Param("theater_name") String name, @Param("theater_location") String location);
}

package com.example.domain.payment.repository;

import com.example.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p JOIN FETCH p.seatList WHERE p.user.id = :userId")
    List<Payment> findByUserId(@Param("userId") Long userId);

}

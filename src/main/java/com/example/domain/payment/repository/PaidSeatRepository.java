package com.example.domain.payment.repository;

import com.example.domain.payment.domain.PaidSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaidSeatRepository extends JpaRepository<PaidSeat, Long> {

    @Query("SELECT distinct p.seatName FROM PaidSeat p WHERE p.payment.id = :payId")
    List<String> findAllSeatNames(@Param("payId") Long payId);

}

package com.example.domain.user.repository;

import com.example.domain.payment.domain.Payment;
import com.example.domain.user.domain.User;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByMail(String mail);

}

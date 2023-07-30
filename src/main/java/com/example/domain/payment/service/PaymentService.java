package com.example.domain.payment.service;

import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.repository.PaymentRepository;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.user.domain.User;
import com.example.global.exception.CustomException;
import com.example.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.example.global.exception.ErrorCode.PAYMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public double getAmount(){
        Payment payment = Payment.builder().build();
         return payment.getAmount();
    }

    public Payment save(FixPaymentRequestDto dto, User user, Schedule schedule) {
        return paymentRepository.save(dto.toEntity(user, schedule));
    }

    public Payment findById(long id) {
        return paymentRepository.findById(id).orElseThrow(
                () -> new CustomException(PAYMENT_NOT_FOUND.getMessage(), PAYMENT_NOT_FOUND)
        );
    }

    public List<Payment> findAll(Long userId) {
        List<Payment> allByUserId = paymentRepository.findByUserId(userId);
        return allByUserId;
    }

}

package com.example.domain.payment.service;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaidSeatResponseDto;
import com.example.domain.payment.repository.PaidSeatRepository;
import com.example.global.exception.CustomException;
import com.example.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaidSeatService {

    private final PaidSeatRepository paidSeatRepository;

    public PaidSeat save(String seatName, Payment payment) {
       return paidSeatRepository.save(new PaidSeat(seatName, payment));
    }

    public List<PaidSeatResponseDto> findAll(Long paymentId) {
        return paidSeatRepository.findAllSeatNames(paymentId).stream().map(PaidSeatResponseDto::new).collect(Collectors.toList());

    }
}

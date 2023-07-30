package com.example.domain.payment.controller;

import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.payment.service.PaymentFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentFacadeService facadeService;

    @GetMapping("/{scheduleId}")
    public ResponseEntity<PaymentResponseDto> getPaymentAmount(@PathVariable Long scheduleId, @Validated PaymentRequestDto dto){
        return ResponseEntity.ok().body(facadeService.getPrice(scheduleId, dto));
    }

    @PostMapping
    public ResponseEntity<Boolean> payMyMovie(Long scheduleId, @Validated FixPaymentRequestDto dto){
        return ResponseEntity.ok().body(facadeService.payMyMovie(scheduleId, dto));
    }
}

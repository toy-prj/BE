package com.example.domain.user.domain.response;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPaymentResponseDto {

    private Long paymentId;

    private Long scheduleId;
    private int count;
    private PaymentType payment;
    private double amountToPay;
    private List<PaidSeat> seatList;

    private String title;
    private String genre;
    private String theater;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public MyPaymentResponseDto(Payment payment, List<PaidSeat> seatList) {
        this.paymentId = payment.getId();
        this.scheduleId = payment.getSchedule().getId();
        this.count = payment.getCount();
        this.payment = payment.getPayment();
        this.amountToPay = payment.getAmount();
        this.seatList = seatList;
        this.title = payment.getSchedule().getMovie().getName();
        this.genre = payment.getSchedule().getMovie().getGenre();
        this.theater = payment.getSchedule().getTheater().getName();
        this.location = payment.getSchedule().getTheater().getLocation();
        this.startTime =  payment.getSchedule().getTime();
        this.endTime = payment.getSchedule().getTime().plusHours(2);
    }
}

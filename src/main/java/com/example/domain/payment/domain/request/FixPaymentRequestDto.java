package com.example.domain.payment.domain.request;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.PaymentType;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixPaymentRequestDto {

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long scheduleId;

    @Min(value = 1)
    private int count;

    @NotEmpty
    private PaymentType payment;

    @NotEmpty
    private double amountToPay;

    private List<SeatSelectedDto> seatList;


    public Payment toEntity(User user, Schedule schedule) {
        return Payment.builder()
                .payment(getPayment())
                .time(LocalDateTime.now())
                .count(getCount())
                .amount(getAmountToPay())
                .user(user)
                .schedule(schedule)
                .seatList(List.of())
                .build();

    }
}

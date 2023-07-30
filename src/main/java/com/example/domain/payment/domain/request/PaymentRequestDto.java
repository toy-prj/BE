package com.example.domain.payment.domain.request;

import com.example.domain.payment.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Getter
public class PaymentRequestDto {

    @Min(value = 1, message = "최소 선택 매수는 1매 입니다.")
    private int count;

    @NotEmpty(message = "최소 1개 이상의 좌석을 선택해야합니다.")
    private List<SeatSelectedDto> seatList;

    @NotBlank(message = "결제 방식을 선택해야합니다.")
    private PaymentType payment;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long scheduleId;



}

package com.example.domain.payment.domain.response;

import com.example.domain.payment.domain.PaidSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaidSeatResponseDto {

    private String paidSeatName;

}
